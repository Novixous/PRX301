/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javafx.print.Collation;
import javax.persistence.EntityManager;
import webservice.dtos.Flight;
import webservice.dtos.Segment;
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
        for (Flight flight : flights) {
            if (flight.getArrival().getIataCode().equals("DTM")) {
                System.out.println("");
            }
        }
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

    public List<Flight> find(String departureDate, String from, String to) throws CloneNotSupportedException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        HashSet<Flight> flights = new HashSet<>(em.createQuery("SELECT f FROM Flight f INNER JOIN fetch f.segmentList s WHERE f.departureDate = :departureDate AND f.departure.iataCode = :from AND f.arrival.iataCode = :to")
                .setParameter("departureDate", departureDate)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList());
        List<Flight> result = new ArrayList<>();
        Iterator<Flight> it = flights.iterator();
        while (it.hasNext()) {
            Flight tmp = (Flight) it.next().clone();
            List<Segment> segments = tmp.getSegmentList();
            Collections.sort(segments, new Comparator<Segment>() {
                @Override
                public int compare(Segment o1, Segment o2) {
                    return o1.getArrivalTime().compareTo(o2.getArrivalTime());
                }
            });
            if (segments.get(segments.size() - 1).getArrival().getIataCode().equals("SGN")) {
                tmp.setSegmentList(segments);
                result.add(tmp);
            }
        }
        Collections.sort(result, new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                if (o1.getDuration().compareTo(o2.getDuration()) == 0) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
                return o1.getDuration().compareTo(o2.getDuration());
            }
        });
        em.getTransaction().commit();
        em.close();
        return result;
    }

}
