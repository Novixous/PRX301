/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.List;
import javax.persistence.EntityManager;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class GenericDAO<T> {

    private Class<T> type;

    public <T> void insert(T obj) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.close();
    }

    public <V> T find(V key) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        T t = type.cast(em.find(type, key));
        em.getTransaction().commit();
        em.close();
        return t;
    }

    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<T> t = em.createQuery("Select t from " + type.getSimpleName() + " t").getResultList();
        em.getTransaction().commit();
        em.close();
        return t;
    }

    public void merge(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

}
