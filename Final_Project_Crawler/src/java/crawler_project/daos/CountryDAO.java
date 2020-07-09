/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.daos;

import crawler_project.dtos.Country;
import crawler_project.utils.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 *
 * @author Novixous
 */
public class CountryDAO {

    public void insertEntity(Country country) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(country);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Country findEntityByName(String name) {
        Country country = null;
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {

            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            country = (Country) entityManager.createQuery("SELECT c FROM Country c WHERE c.name = :name").setParameter("name", name).getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (NoResultException e) {
            try {
                country = (Country) entityManager.createQuery("SELECT c FROM Country c WHERE dbo.LEVENSHTEIN(c.name, :name) IS NOT NULL and dbo.LEVENSHTEIN(c.name, :name) <=2").setParameter("name", name).getSingleResult();
                entityManager.getTransaction().commit();
                entityManager.close();
            } catch (NoResultException ex) {
                System.out.println("ERROR NoResultException with name: " + name);
            }
        } catch (NonUniqueResultException e) {
            System.out.println("ERROR NonUniqueResultException with name: " + name);
        }
        return country;

    }
}
