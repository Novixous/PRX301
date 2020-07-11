/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import javax.persistence.EntityManager;
import webservice.dtos.Airline;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class AirlineDAO {

    public static void insertAirline(Airline airline) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(airline);
        em.getTransaction().commit();
        em.close();

    }

}
