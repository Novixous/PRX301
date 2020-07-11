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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "Flight")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flight.findAll", query = "SELECT f FROM Flight f")
    , @NamedQuery(name = "Flight.findByFlightId", query = "SELECT f FROM Flight f WHERE f.flightId = :flightId")
    , @NamedQuery(name = "Flight.findByDepartureDate", query = "SELECT f FROM Flight f WHERE f.departureDate = :departureDate")
    , @NamedQuery(name = "Flight.findByArrivalDate", query = "SELECT f FROM Flight f WHERE f.arrivalDate = :arrivalDate")
    , @NamedQuery(name = "Flight.findByDuration", query = "SELECT f FROM Flight f WHERE f.duration = :duration")
    , @NamedQuery(name = "Flight.findByPrice", query = "SELECT f FROM Flight f WHERE f.price = :price")})
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "flight_id")
    private Integer flightId;
    @Size(max = 50)
    @Column(name = "departure_date")
    private String departureDate;
    @Size(max = 50)
    @Column(name = "arrival_date")
    private String arrivalDate;
    @Column(name = "duration")
    private Integer duration;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @JoinTable(name = "Flight_Segment", joinColumns = {
        @JoinColumn(name = "flight_id", referencedColumnName = "flight_id")}, inverseJoinColumns = {
        @JoinColumn(name = "segment_id", referencedColumnName = "segment_id")})
    @ManyToMany
    private List<Segment> segmentList = new ArrayList<>();
    @JoinColumn(name = "departure", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport departure;
    @JoinColumn(name = "arrival", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport arrival;

    public Flight() {
    }

    public Flight(Integer flightId) {
        this.flightId = flightId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @XmlTransient
    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    public void addSegment(Segment segment) {
        this.segmentList.add(segment);
        segment.getFlightList().add(this);
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
        hash += (flightId != null ? flightId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.flightId == null && other.flightId != null) || (this.flightId != null && !this.flightId.equals(other.flightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webservice.dtos.Flight[ flightId=" + flightId + " ]";
    }

}
