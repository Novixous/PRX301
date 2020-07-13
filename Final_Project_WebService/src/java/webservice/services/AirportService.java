/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import webservice.daos.AirportDAO;
import webservice.daos.GenericDAO;
import webservice.dtos.Airport;
import webservice.dtos.AirportArrivalCount;
import webservice.utils.StatisticUtils;

/**
 *
 * @author Novixous
 */
@Path("/airport")
public class AirportService {

    @Path("/crawl")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Airport> getAirportListForCrawler() {
        AirportDAO airportDAO = new AirportDAO();
        return airportDAO.getAirportsForCrawler();

    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Airport> getAllAirports() {
        GenericDAO<Airport> genericDAO = new GenericDAO<>();
        genericDAO.setType(Airport.class);
        return genericDAO.findAll();
    }

    @Path("/arrivalCounts")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<AirportArrivalCount> getAirportArrivalCounts() {
        AirportDAO airportDAO = new AirportDAO();
        List<AirportArrivalCount> list = airportDAO.getCityArrivalCount(null);
        return list;
    }

//    @GET
//    @Produces("application/json")
//    public Response getAirportListForCrawler() {
//        AirportDAO airportDAO = new AirportDAO();
//        return Response.ok(this.toJson(airportDAO.getAirportsForCrawler())).build();
//
//    }
}
