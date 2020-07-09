/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.csv_importers;

import crawler_project.constants.AirportTypeConstants;
import crawler_project.constants.ContinentConstants;
import crawler_project.daos.AirportDAO;
import crawler_project.dtos.Airport;
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
public class AirportImporterFromCSV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filePath = "D://Summer 2020/PRX301/airport-codes_csv.csv";
        BufferedReader br = null;
        String line = "";
        String split = ";";
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine();
            while ((line = br.readLine()) != null && count < 56871) {
                count++;
                System.out.println(count);
                String[] airportString = line.split(split);
                AirportDAO airportDAO = new AirportDAO();
                if (airportString.length > 13 && !airportString[10].equals("")) {
                    if (airportDAO.findEntity(airportString[13]) != null) {

                    } else {
                        if (airportString[2].equals("small_airport") || airportString[2].equals("medium_airport") || airportString[2].equals("large_airport")) {
                            if (!airportString[13].trim().equals("0") && !airportString[13].trim().equals("-") && !airportString[13].trim().equals("") && airportString[13] != null) {
                                Airport airport = new Airport();
                                airport.setIataCode(airportString[13]);
                                Country country = new Country(airportString[8]);
                                airport.setCountry(country);
                                switch (airportString[7]) {
                                    case "AF":
                                        airport.setContinent(ContinentConstants.AF);
                                        break;
                                    case "AN":
                                        airport.setContinent(ContinentConstants.AN);
                                        break;
                                    case "AS":
                                        airport.setContinent(ContinentConstants.AS);
                                        break;
                                    case "EU":
                                        airport.setContinent(ContinentConstants.EU);
                                        break;
                                    case "NA":
                                        airport.setContinent(ContinentConstants.NA);
                                        break;
                                    case "OC":
                                        airport.setContinent(ContinentConstants.OC);
                                        break;
                                    case "SA":
                                        airport.setContinent(ContinentConstants.SA);
                                        break;
                                    default:
                                        airport.setContinent(-1);
                                        break;
                                }
                                airport.setName(airportString[3]);
                                switch (airportString[2]) {
                                    case "small_airport":
                                        airport.setType(AirportTypeConstants.SMALL_AIRPORT);
                                        break;
                                    case "medium_airport":
                                        airport.setType(AirportTypeConstants.MEDIUM_AIRPORT);
                                        break;
                                    case "large_airport":
                                        airport.setType(AirportTypeConstants.LARGE_AIRPORT);
                                        break;
                                    default:
                                        airport.setType(-1);
                                        break;
                                }
                                airport.setCity(airportString[10]);
                                System.out.println(airport.getIataCode());
                                airportDAO.insertEntity(airport);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(AirportImporterFromCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.exit(0);
        }

    }

}
