/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import webservice.daos.AirportDAO;
import webservice.dtos.Airport;

/**
 *
 * @author Novixous
 */
@Path("/airport")
public class AirportService {

    @GET
    @Produces("application/xml")
    public List<Airport> getAirportListForCrawler() {
        AirportDAO airportDAO = new AirportDAO();
        return airportDAO.getAirportsForCrawler();

    }
    
    private String toJson(Object entity) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz")
                .setPrettyPrinting()
                .create();
        String result = gson.toJson(entity);
        return result.replace("\\\"", "");
    }
//    @GET
//    @Produces("application/json")
//    public Response getAirportListForCrawler() {
//        AirportDAO airportDAO = new AirportDAO();
//        return Response.ok(this.toJson(airportDAO.getAirportsForCrawler())).build();
//
//    }
}