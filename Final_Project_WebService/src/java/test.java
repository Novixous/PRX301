
import java.util.Collections;
import java.util.List;
import webservice.daos.AirportDAO;
import webservice.dtos.AirportArrivalCount;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Novixous
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AirportDAO airportDAO = new AirportDAO();
        List<AirportArrivalCount> list = airportDAO.getCityArrivalCount(null);
        Double sum = 0D;
        for (AirportArrivalCount airportArrivalCount : list) {
            sum += airportArrivalCount.getArrivalCount();
        }
        Double mean = sum / list.size();
        Collections.sort(list);
    }

}
