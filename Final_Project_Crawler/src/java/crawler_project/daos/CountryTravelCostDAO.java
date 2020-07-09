/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.daos;

import crawler_project.dtos.Country;
import crawler_project.dtos.CountryTravelCost;
import crawler_project.utils.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Novixous
 */
public class CountryTravelCostDAO {

    public void insertEntity(CountryTravelCost countryTravelCost) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(countryTravelCost);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
