/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Toan
 */
public class TestWellFormed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String projectPath = System.getProperty("user.dir");
        String file = projectPath + "\\web\\index.html";
        //String file = "D:\\CodeXML\\Test_Project\\web\\test.xml";
        TestWellFormer former = new TestWellFormer();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String document = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                document += line;
            }
            String result = former.check(document);
            System.out.println(result);
            Document doc = XMLUtils.parseFileToDom(projectPath + "\\web\\WEB-INF\\test.xml");
            XPath xPath = XMLUtils.createXPath();
            String exp = "//img";
            Node node = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
            String attr = node.getAttributes().getNamedItem("src").getNodeValue();
            System.out.println(attr);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
