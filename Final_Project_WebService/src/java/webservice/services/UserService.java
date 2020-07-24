/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import webservice.daos.GenericDAO;
import webservice.dtos.Airport;
import webservice.dtos.User;
import webservice.utils.ExceptionUtil;
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

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public int register(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("email") String email,
            @FormParam("fullname") String fullname) {
        try {
            GenericDAO genericDAO = new GenericDAO();
            genericDAO.insert(new User(username, fullname, password, 0, email));
        } catch (Exception e) {
            if (ExceptionUtil.findRootCause(e).getMessage().contains("PRIMARY KEY")) {
                return 0;
            }
        }
        return 1;
    }

    @POST
    @Path("/visitedCity")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public int RegisterVisitedCity(@FormParam("username") String username,
            @FormParam("iataCode") String iataCode) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, username);
        Airport airport = em.find(Airport.class, iataCode);
        user.getAirportList().add(airport);
        airport.getUserList().add(user);
        em.getTransaction().commit();
        return 0;
    }

}
