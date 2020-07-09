package webservice.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Novixous
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "Final_Project_WebServicePU";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            try {

                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
