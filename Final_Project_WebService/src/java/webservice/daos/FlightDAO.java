/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import webservice.dtos.Flight;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class FlightDAO {

    public HashSet<Flight> getFlightOfCities(List<String> cityNames, String date) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        HashSet<Flight> flights = new HashSet<>(em.createQuery("SELECT f FROM Flight f INNER JOIN fetch f.segmentList s JOIN f.arrival a JOIN a.country c JOIN c.countryTravelCost ct WHERE a.city IN :cityNames and f.departureDate = :date and f.departure.iataCode = 'SGN' and ct is not null")
                .setParameter("cityNames", cityNames)
                .setParameter("date", date)
                .getResultList());
        System.out.println(flights.size());
        em.getTransaction().commit();
        em.close();
        return flights;
    }

    public HashSet<Flight> find(Integer id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        HashSet<Flight> flights = new HashSet<>(em.createQuery("SELECT f FROM Flight f INNER JOIN fetch f.segmentList s WHERE f.flightId = :id")
                .setParameter("id", id)
                .getResultList());
        System.out.println(flights.size());
        em.getTransaction().commit();
        em.close();
        return flights;
    }

    public Flight find(String departureDate, String from, String to) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        HashSet<Flight> flights = new HashSet<>(em.createQuery("SELECT f FROM Flight f INNER JOIN fetch f.segmentList s WHERE f.departureDate = :departureDate AND f.departure.iataCode = :from AND f.arrival.iataCode = :to ORDER BY f.duration ASC, f.price DESC")
                .setParameter("departureDate", departureDate)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList());
        Flight flight = flights.iterator().next();
        flights = find(flight.getFlightId());
        flight = flights.iterator().next();
        em.getTransaction().commit();
        em.close();
        return flight;
    }

}
