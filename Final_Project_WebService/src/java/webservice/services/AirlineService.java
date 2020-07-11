/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import java.io.StringReader;
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
import webservice.dtos.Airline;
import webservice.utils.XMLUtils;

/**
 *
 * @author Novixous
 */
@Path("/airline")
public class AirlineService {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Integer insertAirline(String xmlString) {
        try {
            XPath xPath = XMLUtils.createXPath();
            JAXBContext jc = JAXBContext.newInstance(Airline.class);
            Unmarshaller um = jc.createUnmarshaller();
            NodeList airlineNodes = (NodeList) xPath.evaluate("//airline", new InputSource(new StringReader(xmlString)), XPathConstants.NODESET);
            for (int i = 0; i < airlineNodes.getLength(); i++) {
                Airline airline = (Airline) um.unmarshal(airlineNodes.item(i));
                AirlineDAO.insertAirline(airline);
            }
        } catch (JAXBException | XMLStreamException ex) {
//            Logger.getLogger(AirlineService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
//            Logger.getLogger(AirlineService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }
}
