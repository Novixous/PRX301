/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import webservice.dtos.CountryTravelCost;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class CountryTravelCostDAO {

    public void insertEntity(CountryTravelCost countryTravelCost) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("Final_Project_WebServicePU").createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(countryTravelCost);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
