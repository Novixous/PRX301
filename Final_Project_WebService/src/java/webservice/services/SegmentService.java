/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import webservice.daos.GenericDAO;
import webservice.daos.SegmentDAO;
import webservice.dtos.Airport;
import webservice.dtos.Segment;
import webservice.utils.XMLUtils;

/**
 *
 * @author Novixous
 */
@Path("/segment")
public class SegmentService {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Integer insertSegment(String xmlString) {
        try {
            XPath xPath = XMLUtils.createXPath();
            JAXBContext jc = JAXBContext.newInstance(Segment.class);
            Unmarshaller um = jc.createUnmarshaller();
            GenericDAO genericDAO = new GenericDAO();
            NodeList segmentNodes = (NodeList) xPath.evaluate("//segment", new InputSource(new StringReader(xmlString)), XPathConstants.NODESET);

            for (int j = 0; j < segmentNodes.getLength(); j++) {
                Segment segment = (Segment) um.unmarshal(segmentNodes.item(j));
                segment.setSegmentId(null);

                genericDAO.setType(Airport.class);
                Airport departure = (Airport) genericDAO.find(segment.getDeparture().getIataCode());
                segment.setDeparture(departure);

                Airport arrival = (Airport) genericDAO.find(segment.getArrival().getIataCode());
                segment.setArrival(arrival);

                SegmentDAO segmentDAO = new SegmentDAO();

                segmentDAO.insert(segment);

            }

        } catch (Exception ex) {
            Logger.getLogger(SegmentService.class.getName()).log(Level.SEVERE, "duplicated");
        }

        return 0;
    }
}
