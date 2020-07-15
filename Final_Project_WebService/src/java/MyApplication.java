
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import webservice.services.AirportService;
import webservice.services.AirlineService;
import webservice.services.CountryCostService;
import webservice.services.FlightService;
import webservice.services.SegmentService;
import webservice.services.UserService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Novixous
 */
@ApplicationPath("/")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<>();
        h.add(AirportService.class);
        h.add(AirlineService.class);
        h.add(FlightService.class);
        h.add(SegmentService.class);
        h.add(CountryCostService.class);
        h.add(UserService.class);
        return h;
    }

}
