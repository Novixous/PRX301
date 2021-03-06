/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.csv_importers;

import crawler_project.daos.CountryDAO;
import crawler_project.dtos.Country;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Novixous
 */
public class CountryImporterFromCSV {

    public static void main(String[] args) {

        String filePath = "D://Summer 2020/PRX301/countries.csv";
        BufferedReader br = null;
        String line = "";
        String split = ";";
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (count == 246) {
                    System.out.println(count);
                }
                count++;
                String[] countryInfo = line.split(split);
                Country country = new Country(countryInfo[1], countryInfo[2], countryInfo[3]);
                CountryDAO countryDAO = new CountryDAO();
                countryDAO.insertEntity(country);
                System.out.println(count);
            }
        } catch (Exception ex) {
            Logger.getLogger(CountryImporterFromCSV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(CountryImporterFromCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.exit(0);
        }
    }
}
