/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.daos;

import java.util.List;
import javax.persistence.EntityManager;
import webservice.dtos.Airport;
import webservice.dtos.AirportArrivalCount;
import webservice.dtos.User;
import webservice.utils.JPAUtil;

/**
 *
 * @author Novixous
 */
public class AirportDAO {

    public List<Airport> getAirportsForCrawler() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<Airport> airports = (List<Airport>) em.createNativeQuery("{call getAirportsForCrawler()}", Airport.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return airports;
    }

    public Airport getAirportByKey(String key) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Airport airport = em.find(Airport.class, key);
        em.getTransaction().commit();
        em.close();
        return airport;
    }

    public List<AirportArrivalCount> getCityArrivalCount(Double arrivalCount, String username) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<AirportArrivalCount> list;
        if (arrivalCount != null) {
            list = em.createNativeQuery("SELECT SUM(c.arrivalCount) as arrivalCount, city, country "
                    + "FROM (SELECT arrival, COUNT(*) as arrivalCount FROM Segment "
                    + "GROUP BY arrival) c join Airport a "
                    + "on c.arrival = a.iata_code join Country co on co.Code = a.country "
                    + "WHERE a.continent = 3 and a.iata_code in(SELECT iata_code FROM dbo.funcGetAirportsForCrawler()) and arrivalCount > ? "
                    + "GROUP BY city, country "
                    + "ORDER BY arrivalCount DESC", "AirportArrivalCountMapping")
                    .setParameter(1, arrivalCount)
                    .getResultList();
        } else {
            list = em.createNativeQuery("SELECT SUM(c.arrivalCount) as arrivalCount, city, country "
                    + "FROM (SELECT arrival, COUNT(*) as arrivalCount FROM Segment "
                    + "GROUP BY arrival) c join Airport a "
                    + "on c.arrival = a.iata_code join Country co on co.Code = a.country "
                    + "WHERE a.continent = 3 and a.iata_code in(SELECT iata_code FROM dbo.funcGetAirportsForCrawler()) "
                    + "GROUP BY city, country "
                    + "ORDER BY arrivalCount DESC", "AirportArrivalCountMapping")
                    .getResultList();
        }
        if (username != null) {
            User user = em.find(User.class, username);
            List<Airport> airports = user.getAirportList();
            for (int i = list.size() - 1; i >= 0; i--) {
                for (Airport airport : airports) {
                    if (list.get(i).getCity().equals(airport.getCity())) {
                        list.remove(i);
                        break;
                    }
                }
            }
        }
        em.getTransaction().commit();
        em.close();

        return list;
    }
}
