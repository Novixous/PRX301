/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Novixous
 */
public class FlightsCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            String configPath = System.getProperty("user.dir") + "\\web\\WEB-INF\\config.xml";
            Document config = XMLUtils.parseFileToDom(configPath);
            XPath xPath = XMLUtils.createXPath();
            Node node = (Node) xPath.evaluate("//domain", config, XPathConstants.NODE);
            String domain = node.getTextContent();
            node = (Node) xPath.evaluate("//flight-sub-domain", config, XPathConstants.NODE);
            String subDomain = node.getTextContent();
            String urlString = domain + subDomain + "/SGN/LGA/2020-08-03";
            BufferedReader br = getBufferReaderForURL("https://skiplagged.com/api/search.php?from=SGN&to=LGA&depart=2020-08-23");
            String document = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                document += line;
            }
            Document htmlContent = XMLUtils.parseStringToDom(document);

        } catch (Exception ex) {
            Logger.getLogger(FlightsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static BufferedReader getBufferReaderForURL(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }
}
