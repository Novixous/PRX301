/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.List;
import javax.persistence.EntityManager;
import webservice.dtos.Segment;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class SegmentDAO {

    public void insert(Segment segment) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<Segment> segments = em.createQuery("SELECT s FROM Segment s WHERE s.flightNumber = :flightNumber and s.departureTime = :departureTime and s.arrivalTime = :arrivalTime")
                .setParameter("flightNumber", segment.getFlightNumber())
                .setParameter("departureTime", segment.getDepartureTime())
                .setParameter("arrivalTime", segment.getArrivalTime()).getResultList();
        boolean found = false;
        for (int i = 0; i < segments.size(); i++) {
            Segment tmp = segments.get(i);
            if (tmp.getAirlineCode().getAirlineCode().equals(segment.getAirlineCode().getAirlineCode())
                    && tmp.getArrival().getIataCode().equals(segment.getArrival().getIataCode())
                    && tmp.getDeparture().getIataCode().equals(segment.getDeparture().getIataCode())) {
                found = true;
            }
        }
        if (!found) {
            em.persist(segment);
        }
        em.getTransaction().commit();
        em.close();

    }

    public Segment findSegment(Segment segment) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Segment result = null;
        List<Segment> segments = em.createQuery("SELECT s FROM Segment s WHERE s.flightNumber = :flightNumber and s.departureTime = :departureTime and s.arrivalTime = :arrivalTime")
                .setParameter("flightNumber", segment.getFlightNumber())
                .setParameter("departureTime", segment.getDepartureTime())
                .setParameter("arrivalTime", segment.getArrivalTime()).getResultList();
        boolean found = false;
        for (int i = 0; i < segments.size(); i++) {
            Segment tmp = segments.get(i);
            if (tmp.getAirlineCode().getAirlineCode().equals(segment.getAirlineCode().getAirlineCode())
                    && tmp.getArrival().getIataCode().equals(segment.getArrival().getIataCode())
                    && tmp.getDeparture().getIataCode().equals(segment.getDeparture().getIataCode())) {
                result = tmp;
                break;
            }
        }

        em.getTransaction().commit();
        em.close();
        return result;
    }

}
