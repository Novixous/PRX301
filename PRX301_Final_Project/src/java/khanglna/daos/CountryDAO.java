/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import khanglna.dtos.Country;
import khanglna.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class CountryDAO {

    public void insertEntity(Country country) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(country);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
