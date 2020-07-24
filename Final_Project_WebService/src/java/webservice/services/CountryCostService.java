/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import webservice.daos.CountryDAO;
import webservice.daos.CountryTravelCostDAO;
import webservice.dtos.Country;
import webservice.dtos.CountryTravelCost;
import webservice.utils.XMLUtils;

/**
 *
 * @author Novixous
 */
@Path("/countryCostCrawl")
public class CountryCostService {

    private ExecutorService executor;
    @Context
    ServletContext servletContext;

    @PostConstruct
    public void onCreate() {

        // Creates a thread pool that reuses a fixed number 
        // of threads operating off a shared unbounded queue
        this.executor = Executors.newFixedThreadPool​(3);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int crawlCountryCost() {
        BufferedReader br = null;

        try {
            CountryDAO countryDAO = new CountryDAO();
            String configPath = servletContext.getRealPath("/") + "\\WEB-INF\\config.xml";
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
            CompletableFuture[] futures = new CompletableFuture[nodeList.getLength()];
            CountryTravelCostDAO countryTravelCostDAO = new CountryTravelCostDAO();
            for (int i = 0; i < nodeList.getLength(); i++) {
                futures[i] = CompletableFuture.runAsync(new CountryCostTask(nodeList.item(i), countryDetailUrlFormat, converterURLFormat, countryDAO, countryTravelCostDAO), executor);
            }
            CompletableFuture.allOf(futures).join();

        } catch (Exception ex) {
            Logger.getLogger(CountryCostService.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException ex) {
                    Logger.getLogger(CountryCostService.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return 0;
    }

    public BufferedReader getBufferReaderFromURL(String urlString) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(2 * 1000);
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            InputStream is = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));

        } catch (MalformedURLException ex) {
            Logger.getLogger(CountryCostService.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
//            Logger.getLogger(CountryTravelCostCrawler.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR Connection timeout. Retrying");
            return getBufferReaderFromURL(urlString);
        }
        return bufferedReader;
    }

    private boolean validate(String inputXml, String schemaLocation, boolean dtdCheck) throws IOException, SAXException, TransformerConfigurationException, TransformerException, ParserConfigurationException {
        SchemaFactory schemafactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaFile = new File(schemaLocation);
        Schema schemaSchema = schemafactory.newSchema(schemaFile);
        Validator schemaValidator = schemaSchema.newValidator();

        Source source = new StreamSource(new StringReader(inputXml));

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                throw exception; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                throw exception; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception; //To change body of generated methods, choose Tools | Templates.
            }

        });

        boolean isValid = true;
        try {
            if (dtdCheck) {
                builder.parse(new InputSource(new StringReader(inputXml)));
            }
            schemaValidator.validate(source);
        } catch (SAXException e) {
            isValid = false;
        }

        return isValid;
    }

    public class CountryCostTask implements Runnable {

        Node countryName;
        String countryDetailUrlFormat;
        String converterURLFormat;
        CountryDAO countryDAO;
        CountryTravelCostDAO countryTravelCostDAO;

        public CountryCostTask(Node countryName, String countryDetailUrlFormat, String converterURLFormat, CountryDAO countryDAO, CountryTravelCostDAO countryTravelCostDAO) {
            this.countryName = countryName;
            this.countryDetailUrlFormat = countryDetailUrlFormat;
            this.converterURLFormat = converterURLFormat;
            this.countryDAO = countryDAO;
            this.countryTravelCostDAO = countryTravelCostDAO;
        }

       

        @Override
        public void run() {
            try {
                int count = 0;
                String countryNameStr = countryName.getTextContent().trim();
                XPath xPath = XMLUtils.createXPath();
                Country country = countryDAO.findEntityByName(countryNameStr);
                if (country != null && !countryNameStr.equals("Tahiti")) {
                    String dtdPath = servletContext.getRealPath("/") + "/WEB-INF/country-travel-cost.dtd";
                    String xml = "<!DOCTYPE countryTravelCost[" + readFile(dtdPath, StandardCharsets.UTF_8) + "]>\n"
                            + "<countryTravelCost>\n";
                    xml += "<countryCode>" + country.getCode() + "</countryCode>\n";
                    List<String> countryURLs = new ArrayList<>();
                    for (int j = 1; j <= 3; j++) {
                        countryURLs.add(String.format(countryDetailUrlFormat, country.getCode(), j));

                    }
                    for (int j = 0; j < countryURLs.size(); j++) {
                        try {
                            String countryURL = countryURLs.get(j);
                            BufferedReader br = getBufferReaderFromURL(countryURL);
                            boolean start = false;
                            String htmlContent = "";
                            String line = "";
                            String currency = "";
                            while ((line = br.readLine()) != null) {
                                if (start) {
                                    htmlContent += line;
                                    if (line.contains("</div>")) {
                                        break;
                                    }
                                }
                                if (line.contains("<div class=\"cost-tile-value\">")) {
                                    htmlContent += "<div xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
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
                            htmlContent = htmlContent.replace("class", "xsi:type");
                            String schemaPath = servletContext.getRealPath("/") + "/WEB-INF/countryCost.xsd";
                            if (validate(htmlContent, schemaPath, false)) {
                                Document doc = XMLUtils.parseStringToDom(htmlContent);

                                String cost = ((Node) xPath.evaluate("//span[@type='curvalue']", doc, XPathConstants.NODE)).getTextContent();
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
                                        xml += "<costCheap>" + travelCost + "</costCheap>\n";
                                        break;
                                    case 1:
                                        xml += "<costMedium>" + travelCost + "</costMedium>\n";
                                        break;
                                    case 2:
                                        xml += "<costHigh>" + travelCost + "</costHigh>\n";
                                        break;
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(CountryCostService.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SAXException ex) {
                            Logger.getLogger(CountryCostService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    xml += "</countryTravelCost>";
                    JAXBContext jaxbContext = JAXBContext.newInstance(CountryTravelCost.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                    StringReader reader = new StringReader(xml);
                    String schemaPath = servletContext.getRealPath("/") + "/WEB-INF/country-travel-cost.xsd";
                    if (validate(xml, schemaPath, true)) {
                        CountryTravelCost countryTravelCost = (CountryTravelCost) unmarshaller.unmarshal(reader);
                        countryTravelCostDAO.insertEntity(countryTravelCost);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(CountryCostService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
