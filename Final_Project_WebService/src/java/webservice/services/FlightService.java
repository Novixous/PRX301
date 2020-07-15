/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.services;

import java.io.StringReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import webservice.daos.AirportDAO;
import webservice.daos.FlightDAO;
import webservice.daos.GenericDAO;
import webservice.daos.SegmentDAO;
import webservice.dtos.Airport;
import webservice.dtos.AirportArrivalCount;
import webservice.dtos.Flight;
import webservice.dtos.Segment;
import webservice.utils.JPAUtil;
import webservice.utils.StatisticUtils;
import webservice.utils.XMLUtils;

/**
 *
 * @author Novixous
 */
@Path("/flight")
public class FlightService {

    private ExecutorService executor;

    @PostConstruct
    public void onCreate() {

        // Creates a thread pool that reuses a fixed number 
        // of threads operating off a shared unbounded queue
        this.executor = Executors.newFixedThreadPool​(20);
    }

    @PreDestroy
    public void onDestroy() {

        // Initiates an orderly shutdown in which previously submitted 
        // tasks are executed, but no new tasks will be accepted.
        this.executor.shutdownNow();
    }

    public class FlightInsertTask implements Runnable {

        Node flightNode;

        public FlightInsertTask(Node flightNode) {
            this.flightNode = flightNode;
        }

        @Override
        public void run() {
            try {
                XPath xPath = XMLUtils.createXPath();
                JAXBContext jc = JAXBContext.newInstance(Flight.class);
                Unmarshaller um = jc.createUnmarshaller();
                GenericDAO genericDAO = new GenericDAO();
                genericDAO.setType(Flight.class);
                Flight flight = (Flight) um.unmarshal(flightNode);
                flight.setFlightId(null);
                genericDAO.insert(flight);
                NodeList segmentNodes = (NodeList) xPath.evaluate("segment", flightNode, XPathConstants.NODESET);

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
                    System.out.println("done");
                }
            } catch (Exception ex) {
                Logger.getLogger(FlightService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Integer insertAirline(String xmlString) {
        try {
            XPath xPath = XMLUtils.createXPath();

            NodeList flightNodes = (NodeList) xPath.evaluate("//flight", new InputSource(new StringReader(xmlString)), XPathConstants.NODESET);
            CompletableFuture[] futures = new CompletableFuture[flightNodes.getLength()];
            for (int i = 0; i < flightNodes.getLength(); i++) {
                futures[i] = CompletableFuture.runAsync(new FlightInsertTask(flightNodes.item(i)), executor);
            }
            CompletableFuture.allOf(futures).join();
        } catch (JAXBException | XMLStreamException ex) {
            Logger.getLogger(AirlineService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AirlineService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @GET
    @Path("return")
    @Produces(MediaType.APPLICATION_XML)
    public Flight getFlightReturn(
            @QueryParam("dateDeparture") String dateDeparture,
            @QueryParam("from") String depature,
            @QueryParam("to") String arrival) {
        FlightDAO flightDAO = new FlightDAO();
        return flightDAO.find(dateDeparture, depature, arrival);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Flight> getFlights(
            @QueryParam("dateDeparture") String dateDeparture,
            @QueryParam("costWeight") int costWeight,
            @QueryParam("timeWeight") int timeWeight,
            @QueryParam("mostArrivalWeight") int mostArrivalWeight,
            @QueryParam("layoverWeight") int layoverWeight) {

        AirportDAO airportDAO = new AirportDAO();
        List<AirportArrivalCount> list = airportDAO.getCityArrivalCount(null);
        Collections.sort(list, Collections.reverseOrder());
        try {
            timeWeight = handleTimeWeight(timeWeight, costWeight);
            switch (layoverWeight) {
                case 1:
                    layoverWeight = 0;
                    break;
                case 2:
                    layoverWeight = costWeight + 2;
                    break;
            }
            AirportArrivalCount Q1ArrivalCount = StatisticUtils.findQuartile(1, list);
            list = airportDAO.getCityArrivalCount(Double.parseDouble(Q1ArrivalCount.getArrivalCount().toString()));
            List<String> cityNames = new ArrayList<>();
            HashMap<String, Integer> arrivalCountMap = new HashMap<>();
            for (AirportArrivalCount item : list) {
                cityNames.add(item.getCity());
                arrivalCountMap.put(item.getCity(), item.getArrivalCount());
            }
            FlightDAO flightDAO = new FlightDAO();
            HashMap<Integer, Double> scoreMap = new HashMap<>();
            List<Flight> flights = new ArrayList<>(flightDAO.getFlightOfCities(cityNames, dateDeparture));
            MaxParameters maxs = maxParameters(flights, arrivalCountMap);
            for (Flight flight : flights) {
                Double scoreCost = 10.0 - (10.0 / maxs.getMaxCost() * flight.getPrice());
                Double scoreArrival;
                Double scoreDuration = 10.0 - (10.0 / maxs.getMaxDuration() * flight.getDuration());
                Double scoreLayoverDuration = 10.0 / maxs.getMaxLayoverDuration() * getMaxLayoverSegment(flight.getSegmentList());
                Double averageScore;
                int tmpMostArrivalWeight = 0;
                if (mostArrivalWeight <= 2) {
                    scoreArrival = 10.0 - (10.0 / maxs.getMaxArrival() * arrivalCountMap.get(flight.getArrival().getCity()));
                    switch (mostArrivalWeight) {
                        case 1:
                            tmpMostArrivalWeight = costWeight + 2;
                            break;
                        case 2:
                            tmpMostArrivalWeight = costWeight + 1;
                            break;
                    }

                } else {
                    scoreArrival = 10.0 / maxs.getMaxArrival() * arrivalCountMap.get(flight.getArrival().getCity());
                    switch (mostArrivalWeight) {
                        case 3:
                            tmpMostArrivalWeight = costWeight + 1;
                            break;
                        case 4:
                            tmpMostArrivalWeight = costWeight + 2;
                            break;
                    }
                }
                int totalWeight = costWeight + layoverWeight + mostArrivalWeight + timeWeight;
                averageScore = (scoreCost * costWeight + scoreLayoverDuration * layoverWeight + scoreArrival * tmpMostArrivalWeight + scoreDuration * timeWeight) / totalWeight;
                scoreMap.put(flight.getFlightId(), averageScore);
            }
            scoreMap = sortByValue(scoreMap);
            List<Map.Entry<Integer, Double>> sortedScoreList = new LinkedList<>(scoreMap.entrySet());

            int min = 0;
            int max;
            if (sortedScoreList.size() < 4) {
                max = sortedScoreList.size() - 1;
            } else {
                max = sortedScoreList.size()/4;
            }
            HashSet<Flight> result = new HashSet<>();
            flights = new ArrayList<>();
            if (!sortedScoreList.isEmpty()) {
                result = flightDAO.find(sortedScoreList.get((int) (Math.random() * ((max - min) + 1)) + min).getKey());
                List<Flight> resultList = new ArrayList<>(result);
                flights.add(resultList.get(0));
            }
            return flights;

        } catch (Exception ex) {
            Logger.getLogger(AirportService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    private MaxParameters maxParameters(List<Flight> flights, HashMap<String, Integer> arrivalMaps) {
        Double maxCost = 0.0;
        Double maxDuration = 0.0;
        Double maxArrival = 0.0;
        Long maxLayoverDuration = 0l;
        for (Flight flight : flights) {
            Double tmpCost = flight.getPrice();
            if (tmpCost.compareTo(maxCost) > 0) {
                maxCost = tmpCost;
            }

            Double tmpDuration = Double.parseDouble(flight.getDuration().toString());
            if (tmpDuration.compareTo(maxDuration) > 0) {
                maxDuration = tmpDuration;
            }

            Double tmpArrival = Double.parseDouble(arrivalMaps.get(flight.getArrival().getCity()).toString());
            if (tmpArrival.compareTo(maxArrival) > 0) {
                maxArrival = tmpArrival;
            }

            Long tmpMaxLayoverDuration = getMaxLayoverSegment(flight.getSegmentList());
            if (tmpMaxLayoverDuration.compareTo(maxLayoverDuration) > 0) {
                maxLayoverDuration = tmpMaxLayoverDuration;
            }

        }
        return new MaxParameters(maxCost, maxDuration, maxArrival, maxLayoverDuration);
    }

    private Long getMaxLayoverSegment(List<Segment> segments) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        Long maxLayover = 0l;
        for (int i = 1; i < segments.size(); i++) {
            String arrivalTimeString = segments.get(i - 1).getArrivalTime().substring(0, 19).replace("T", " ");
            String arrivalZone = segments.get(i - 1).getArrivalTime().substring(19, 25);
            LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeString, dtf);
            ZonedDateTime zonedArrivalTime = arrivalTime.atZone(ZoneId.of(arrivalZone));

            String departureTimeString = segments.get(i).getDepartureTime().substring(0, 19).replace("T", " ");;
            String departureZone = segments.get(i).getDepartureTime().substring(19, 25);
            LocalDateTime departureTime = LocalDateTime.parse(departureTimeString, dtf);
            ZonedDateTime zonedDepartureTime = departureTime.atZone(ZoneId.of(departureZone));

            Long tmpLayoverDuration = Duration.between(zonedArrivalTime, zonedDepartureTime).getSeconds();
            if (tmpLayoverDuration.compareTo(maxLayover) > 0) {
                maxLayover = tmpLayoverDuration;
            }
        }
        return maxLayover;

    }

    class MaxParameters {

        Double maxCost;
        Double maxDuration;
        Double maxArrival;
        Long maxLayoverDuration;

        public MaxParameters(Double maxCost, Double maxDuration, Double maxArrival, Long maxLayoverDuration) {
            this.maxCost = maxCost;
            this.maxDuration = maxDuration;
            this.maxArrival = maxArrival;
            this.maxLayoverDuration = maxLayoverDuration;
        }

        public Double getMaxCost() {
            return maxCost;
        }

        public Double getMaxDuration() {
            return maxDuration;
        }

        public Double getMaxArrival() {
            return maxArrival;
        }

        public Long getMaxLayoverDuration() {
            return maxLayoverDuration;
        }

    }

    public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm) {
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, Double>> list
                = new LinkedList<>(hm.entrySet());

        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1,
                    Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap  
        HashMap<Integer, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public int handleTimeWeight(int timeWeight, int costWeight) {
        if (costWeight > 1) {
            switch (timeWeight) {
                case 1:
                    timeWeight = costWeight - 2;
                    break;
                case 2:
                    timeWeight = costWeight - 1;
                    break;
                case 3:
                    timeWeight = costWeight;
                    break;
                case 4:
                    timeWeight = costWeight + 1;
                    break;
                case 5:
                    timeWeight = costWeight + 2;
                    break;

            }
        } else {
            switch (timeWeight) {
                case 1:
                    timeWeight = 0;
                    break;
                case 2:
                    timeWeight = 0;
                    break;
                case 3:
                    timeWeight = costWeight;
                    break;
                case 4:
                    timeWeight = costWeight + 1;
                    break;
                case 5:
                    timeWeight = costWeight + 2;
                    break;
            }
        }
        return timeWeight;
    }

}
