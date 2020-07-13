/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import webservice.dtos.Flight;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class FlightDAO {

    public List<Flight> getFlightOfCities(List<String> cityNames, String date) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<Flight> flights = em.createQuery("SELECT f FROM Flight f INNER JOIN fetch f.segmentList s JOIN f.arrival a WHERE a.city IN :cityNames and f.departureDate = :date and f.departure.iataCode = 'SGN'")
                .setParameter("cityNames", cityNames)
                .setParameter("date", date)
                .getResultList();
        System.out.println(flights.size());
        em.getTransaction().commit();
        em.close();
        return flights;
    }

}
