/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.List;
import javax.persistence.EntityManager;
import webservice.dtos.Airport;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class AirportDAO {

    public List<Airport> getAirportsForCrawler() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<Airport> airports = (List<Airport>) em.createNativeQuery("{call getAirportsForCrawler()}", Airport.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return airports;
    }

    public Airport getAirportByKey(String key) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Airport airport = em.find(Airport.class, key);
        em.getTransaction().commit();
        em.close();
        return airport;
    }
}
