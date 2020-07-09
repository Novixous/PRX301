/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.dtos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "Segments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segments.findAll", query = "SELECT s FROM Segments s")
    , @NamedQuery(name = "Segments.findBySegmentId", query = "SELECT s FROM Segments s WHERE s.segmentId = :segmentId")
    , @NamedQuery(name = "Segments.findByFlightNumber", query = "SELECT s FROM Segments s WHERE s.flightNumber = :flightNumber")
    , @NamedQuery(name = "Segments.findByDepartureTime", query = "SELECT s FROM Segments s WHERE s.departureTime = :departureTime")
    , @NamedQuery(name = "Segments.findByArrivalTime", query = "SELECT s FROM Segments s WHERE s.arrivalTime = :arrivalTime")
    , @NamedQuery(name = "Segments.findByDuration", query = "SELECT s FROM Segments s WHERE s.duration = :duration")
    , @NamedQuery(name = "Segments.findByPosition", query = "SELECT s FROM Segments s WHERE s.position = :position")})
public class Segments implements Serializable {

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
    @JoinColumn(name = "airline_code", referencedColumnName = "airline_code")
    @ManyToOne
    private Airlines airlineCode;
    @JoinColumn(name = "departure", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport departure;
    @JoinColumn(name = "arrival", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport arrival;
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id")
    @ManyToOne
    private Flights flightId;

    public Segments() {
    }

    public Segments(Integer segmentId) {
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

    public Airlines getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(Airlines airlineCode) {
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

    public Flights getFlightId() {
        return flightId;
    }

    public void setFlightId(Flights flightId) {
        this.flightId = flightId;
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
        if (!(object instanceof Segments)) {
            return false;
        }
        Segments other = (Segments) object;
        if ((this.segmentId == null && other.segmentId != null) || (this.segmentId != null && !this.segmentId.equals(other.segmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crawler.dtos.Segments[ segmentId=" + segmentId + " ]";
    }
    
}
