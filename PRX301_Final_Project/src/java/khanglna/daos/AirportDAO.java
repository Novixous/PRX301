/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import khanglna.dtos.Airport;
import khanglna.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class AirportDAO {

    public void insertEntity(Airport airport) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(airport);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Airport findEntity(String key) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Airport airport = entityManager.find(Airport.class, key);
        entityManager.getTransaction().commit();
        entityManager.close();
        return airport;
    }
}
