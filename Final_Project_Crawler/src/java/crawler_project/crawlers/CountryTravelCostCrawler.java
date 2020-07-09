/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.crawlers;

import crawler_project.daos.CountryDAO;
import crawler_project.daos.CountryTravelCostDAO;
import crawler_project.dtos.Country;
import crawler_project.dtos.CountryTravelCost;
import crawler_project.utils.XMLUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Novixous
 */
public class CountryTravelCostCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader br = null;
        int count = 0;
        try {
            CountryTravelCostDAO countryTravelCostDAO = new CountryTravelCostDAO();
            String configPath = System.getProperty("user.dir") + "\\web\\WEB-INF\\config.xml";
            Document doc = XMLUtils.parseFileToDom(configPath);
            XPath xPath = XMLUtils.createXPath();
            Node node = (Node) xPath.evaluate("//countries-list-url", doc, XPathConstants.NODE);
            String countryListURL = node.getTextContent().trim();
            node = (Node) xPath.evaluate("//country-detail-url", doc, XPathConstants.NODE);
            String countryDetailUrlFormat = node.getTextContent().trim();
            node = (Node) xPath.evaluate("//currency-converter-url", doc, XPathConstants.NODE);
            String converterURLFormat = node.getTextContent().trim();
            br = getBufferReaderFromURL(countryListURL);
            String line = "";
            String htmlContent = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("<ul class=\"placelist flow\">")) {
                    htmlContent += line;
                    break;
                }
            }
            htmlContent = htmlContent.replace("</a>", "</a></li>");
            doc = XMLUtils.parseStringToDom(htmlContent);
            NodeList nodeList = (NodeList) xPath.evaluate("//a", doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (count == 94) {
                    System.out.println("Here");
                }
                String countryName = nodeList.item(i).getTextContent().trim();
                CountryDAO countryDAO = new CountryDAO();
                Country country = countryDAO.findEntityByName(countryName);
                if (country != null && !countryName.equals("Tahiti")) {
                    List<String> countryURLs = new ArrayList<>();
                    for (int j = 1; j <= 3; j++) {
                        countryURLs.add(String.format(countryDetailUrlFormat, country.getCode(), j));

                    }
                    CountryTravelCost countryTravelCost = new CountryTravelCost();
                    for (int j = 0; j < countryURLs.size(); j++) {
                        String countryURL = countryURLs.get(j);
                        br = getBufferReaderFromURL(countryURL);
                        boolean start = false;
                        htmlContent = "";
                        line = "";
                        String currency = "";
                        while ((line = br.readLine()) != null) {
                            if (start) {
                                htmlContent += line;
                                if (line.contains("</li>")) {
                                    break;
                                }
                            }
                            if (line.contains("cost-tile cost-tile-main")) {
                                htmlContent += line;
                                start = true;
                            }

                        }
                        while ((line = br.readLine()) != null) {
                            if (line.contains("var originalcur =")) {
                                currency = line;
                                currency = currency.split("\"")[1];
                                break;
                            }
                        }
                        if (htmlContent.isEmpty() || htmlContent == null) {
                            break;
                        }
                        doc = XMLUtils.parseStringToDom(htmlContent);

                        String cost = ((Node) xPath.evaluate("//span[@class='curvalue']", doc, XPathConstants.NODE)).getTextContent();
                        cost = cost.replace(",", "");
                        Double travelCost = Double.parseDouble(cost);
                        String converterURL = String.format(converterURLFormat, travelCost, currency);
                        br = getBufferReaderFromURL(converterURL);

                        String temp = br.readLine();
                        if (temp.equals("error")) {
                            break;
                        }
                        travelCost = Double.parseDouble(temp);

                        switch (j) {
                            case 0:
                                countryTravelCost.setCostCheap(travelCost);
                                break;
                            case 1:
                                countryTravelCost.setCostMedium(travelCost);
                                break;
                            case 2:
                                countryTravelCost.setCostHigh(travelCost);
                                break;
                        }

                    }
                    if (countryTravelCost.getCostCheap() != null && countryTravelCost.getCostMedium() != null && countryTravelCost.getCostHigh() != null) {
                        countryTravelCost.setCountryCode(country.getCode());
                        countryTravelCostDAO.insertEntity(countryTravelCost);
                        count++;
                        System.out.println(count);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CountryTravelCostCrawler.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException ex) {
                    Logger.getLogger(CountryTravelCostCrawler.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.exit(0);
        }
    }

    public static BufferedReader getBufferReaderFromURL(String urlString) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(2 * 1000);
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            InputStream is = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));

        } catch (MalformedURLException ex) {
            Logger.getLogger(CountryTravelCostCrawler.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
//            Logger.getLogger(CountryTravelCostCrawler.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR Connection timeout. Retrying");
            return getBufferReaderFromURL(urlString);
        }
        return bufferedReader;
    }

}
