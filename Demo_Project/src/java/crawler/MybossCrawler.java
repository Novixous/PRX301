///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package crawler;
//
//import entities.TblCategory;
//import java.io.BufferedReader;
//import java.io.UnsupportedEncodingException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javax.servlet.ServletContext;
//import javax.xml.namespace.QName;
//import javax.xml.stream.XMLEventReader;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.events.Attribute;
//import javax.xml.stream.events.StartElement;
//import javax.xml.stream.events.XMLEvent;
//
///**
// *
// * @author Novixous
// */
//public class MybossCrawler extends BaseCrawler implements Runnable {
//
//    private String url;
//    private String categoryName;
//    private TblCategory category;
//
//    public MybossCrawler(String url, String categoryName, ServletContext context) {
//        super(context);
//        this.url = url;
//        this.categoryName = categoryName;
//    }
//
//    @Override
//    public void run() {
//        category = createCategory(categoryName);
//        if (category == null) {
//            Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, new Exception("Error: category null"));
//            return;
//        }
//        BufferedReader reader = null;
//        try {
//            reader = getBufferedReaderForURL(url);
//            String line = "";
//            String document = "";
//            boolean isStart = false;
//            boolean isEnding = false;
//            int divCounter = 0;
//            int divOpen = 0;
//            int divClose = 0;
//            while ((line = reader.readLine()) != null) {
//                if (line.contains("<div id=\"phantrang\"")) {
//                    isStart = true;
//                }
//                if (isStart) {
//                    document += line;
//                    if (line.contains("</div>")) {
//                        break;
//                    }
//                }
//            }
//            try {
//                synchronized (BaseThread.getInstance()) {
//                    while (BaseThread.isSuspended()) {
//                        BaseThread.getInstance().wait();
//                    }
//                }
//            } catch (InterruptedException e) {
//                Logger.getLogger(MybossCrawler.class.getName()).log(Level.SEVERE, null, e);
//            }
//            int lastPage = getLastPage(document);
//            for (int i = 0; i < lastPage; i++) {
//                String pageUrl = url + "?page=" + (i + 1);
//                Thread pageCrawlingThread
//            }
//
//        } catch (Exception e) {
//        }
//    }
//
//    private int getLastPage(String document) throws UnsupportedEncodingException, XMLStreamException {
//        document = document.trim();
//        XMLEventReader eventReader = parseStringToXMLEventReader(document);
//        String tagName = "";
//        String link = "";
//        while (eventReader.hasNext()) {
//            XMLEvent event = (XMLEvent) eventReader.next();
//            if (event.isStartElement()) {
//                StartElement startElement = event.asStartElement();
//                tagName = startElement.getName().getLocalPart();
//                if ("a".equals(tagName)) {
//                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
//                    link = attrHref == null ? "" : attrHref.getValue();
//                }
//            }
//        }
//        if (link != null && !link.isEmpty()) {
//            String regex = "[0-9]+$";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(link);
//            if (matcher.find()) {
//                String result = matcher.group();
//                try {
//                    int number = Integer.parseInt(result);
//                    return number;
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return 1;
//    }
//}
