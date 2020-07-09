/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.crawlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Novixous
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
//            BufferedReader br = CountryTravelCostCrawler.getBufferReaderFromURL("https://www.budgetyourtrip.com/budgetreportadv.php?geonameid=&countrysearch=&country_code=AL&categoryid=0&budgettype=2&triptype=0&startdate=&enddate=&travelerno=0");
            BufferedReader br = null;
            String line = "";
            String doc = "<root>";
            boolean start = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("<div class=\"col-sm-20 mt10\">")) {
                    doc += line;
                    start = true;
                }
                if (start) {
                    if (line.contains("<div class=\"col-sm-18 mt20 col-sm-offset-3\"></div>")) {
                        start = false;
                    } else {
                        doc += line;
                    }
                }
            }
            doc += "</root>";
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
