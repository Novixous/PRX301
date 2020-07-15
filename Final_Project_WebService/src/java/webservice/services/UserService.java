/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import webservice.dtos.User;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
@Path("/user")
public class UserService {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User getUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        User user = null;
        try {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            user = (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password).getSingleResult();
            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {

        }
        return user;
    }
}
