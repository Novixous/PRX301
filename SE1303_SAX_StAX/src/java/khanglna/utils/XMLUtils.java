package khanglna.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Novixous
 */
public class XMLUtils implements Serializable {

    public static void parseFileWithSAX(String filePath, DefaultHandler handler) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sax = spf.newSAXParser();
        File f = new File(filePath);
        sax.parse(f, handler);
    }

    public static XMLStreamReader createStAXCursorReaderFromFile(String filePath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        File f = new File(filePath);
        InputStream is = new FileInputStream(f);
        XMLStreamReader reader = xif.createXMLStreamReader(is);
        return reader;
    }

    public static String getTextContent(XMLStreamReader currentCursor, String tagName) throws Exception {
        if (currentCursor != null) {
            while (currentCursor.hasNext()) {
                int cursor = currentCursor.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String name = currentCursor.getLocalName();
                    if (name.equals(tagName)) {
                        currentCursor.next();
                        String result = currentCursor.getText();
                        currentCursor.nextTag();
                        return result;
                    }
                }
            }
        }
        return null;
    }

}
