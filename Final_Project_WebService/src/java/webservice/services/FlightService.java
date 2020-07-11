/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import webservice.daos.AirlineDAO;
import webservice.daos.GenericDAO;
import webservice.daos.SegmentDAO;
import webservice.dtos.Airline;
import webservice.dtos.Airport;
import webservice.dtos.Flight;
import webservice.dtos.Segment;
import webservice.utils.JPAUtil;
import webservice.utils.XMLUtils;

/**
 *
 * @author Novixous
 */
@Path("/flight")
public class FlightService {
    
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Integer insertAirline(String xmlString) {
        try {
            XPath xPath = XMLUtils.createXPath();
            
            NodeList flightNodes = (NodeList) xPath.evaluate("//flight", new InputSource(new StringReader(xmlString)), XPathConstants.NODESET);
            for (int i = 0; i < flightNodes.getLength(); i++) {
                JAXBContext jc = JAXBContext.newInstance(Flight.class);
                Unmarshaller um = jc.createUnmarshaller();
                GenericDAO genericDAO = new GenericDAO();
                genericDAO.setType(Flight.class);
                Flight flight = (Flight) um.unmarshal(flightNodes.item(i));
                flight.setFlightId(null);
                genericDAO.insert(flight);
                NodeList segmentNodes = (NodeList) xPath.evaluate("segment", flightNodes.item(i), XPathConstants.NODESET);
                
                for (int j = 0; j < segmentNodes.getLength(); j++) {
                    jc = JAXBContext.newInstance(Segment.class);
                    um = jc.createUnmarshaller();
                    Segment segment = (Segment) um.unmarshal(segmentNodes.item(j));
                    segment.setSegmentId(null);
                    
                    genericDAO.setType(Airport.class);
                    Airport departure = (Airport) genericDAO.find(segment.getDeparture().getIataCode());
                    segment.setDeparture(departure);
                    
                    Airport arrival = (Airport) genericDAO.find(segment.getArrival().getIataCode());
                    segment.setArrival(arrival);
                    
                    SegmentDAO segmentDAO = new SegmentDAO();
                    segment = segmentDAO.findSegment(segment);
                    
                    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
                    em.getTransaction().begin();
                    segment = em.find(Segment.class, segment.getSegmentId());
                    flight = em.find(Flight.class, flight.getFlightId());
                    segment.addFlight(flight);
                    em.getTransaction().commit();
                    em.close();
                }
            }
        } catch (JAXBException | XMLStreamException ex) {
            Logger.getLogger(AirlineService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AirlineService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }
}
