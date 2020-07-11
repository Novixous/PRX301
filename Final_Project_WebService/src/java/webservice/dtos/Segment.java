/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "Segment", uniqueConstraints = {@UniqueConstraint(columnNames = {"flight_number","airline_code","departure_time","arrival_time","departure","arrival"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segment.findAll", query = "SELECT s FROM Segment s")
    , @NamedQuery(name = "Segment.findBySegmentId", query = "SELECT s FROM Segment s WHERE s.segmentId = :segmentId")
    , @NamedQuery(name = "Segment.findByFlightNumber", query = "SELECT s FROM Segment s WHERE s.flightNumber = :flightNumber")
    , @NamedQuery(name = "Segment.findByDepartureTime", query = "SELECT s FROM Segment s WHERE s.departureTime = :departureTime")
    , @NamedQuery(name = "Segment.findByArrivalTime", query = "SELECT s FROM Segment s WHERE s.arrivalTime = :arrivalTime")
    , @NamedQuery(name = "Segment.findByDuration", query = "SELECT s FROM Segment s WHERE s.duration = :duration")
    , @NamedQuery(name = "Segment.findByPosition", query = "SELECT s FROM Segment s WHERE s.position = :position")})
public class Segment implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "segment_id")
    private Integer segmentId;
    @Size(max = 10)
    @Column(name = "flight_number")
    private String flightNumber;
    @Size(max = 50)
    @Column(name = "departure_time")
    private String departureTime;
    @Size(max = 50)
    @Column(name = "arrival_time")
    private String arrivalTime;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "position")
    private Integer position;
    @ManyToMany(mappedBy = "segmentList")
    private List<Flight> flightList = new ArrayList<>();
    @JoinColumn(name = "airline_code", referencedColumnName = "airline_code")
    @ManyToOne
    private Airline airlineCode;
    @JoinColumn(name = "departure", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport departure;
    @JoinColumn(name = "arrival", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport arrival;

    public Segment() {
    }

    public Segment(Integer segmentId) {
        this.segmentId = segmentId;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @XmlTransient
    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public void addFlight(Flight flight) {
        this.flightList.add(flight);
        flight.getSegmentList().add(this);
    }

    public Airline getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(Airline airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport arrival) {
        this.arrival = arrival;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (segmentId != null ? segmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segment)) {
            return false;
        }
        Segment other = (Segment) object;
        if ((this.segmentId == null && other.segmentId != null) || (this.segmentId != null && !this.segmentId.equals(other.segmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webservice.dtos.Segment[ segmentId=" + segmentId + " ]";
    }

    @Override
    public Segment clone() throws CloneNotSupportedException {
        return (Segment) super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
