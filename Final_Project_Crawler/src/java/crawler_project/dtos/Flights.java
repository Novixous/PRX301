/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler_project.dtos;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "Flights")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flights.findAll", query = "SELECT f FROM Flights f")
    , @NamedQuery(name = "Flights.findByFlightId", query = "SELECT f FROM Flights f WHERE f.flightId = :flightId")
    , @NamedQuery(name = "Flights.findByDepartureDate", query = "SELECT f FROM Flights f WHERE f.departureDate = :departureDate")
    , @NamedQuery(name = "Flights.findByArrivalDate", query = "SELECT f FROM Flights f WHERE f.arrivalDate = :arrivalDate")
    , @NamedQuery(name = "Flights.findByDuration", query = "SELECT f FROM Flights f WHERE f.duration = :duration")
    , @NamedQuery(name = "Flights.findByPrice", query = "SELECT f FROM Flights f WHERE f.price = :price")})
public class Flights implements Serializable {

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
    @JoinColumn(name = "departure", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport departure;
    @JoinColumn(name = "arrival", referencedColumnName = "iata_code")
    @ManyToOne
    private Airport arrival;
    @OneToMany(mappedBy = "flightId")
    private Collection<Segments> segmentsCollection;

    public Flights() {
    }

    public Flights(Integer flightId) {
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

    @XmlTransient
    public Collection<Segments> getSegmentsCollection() {
        return segmentsCollection;
    }

    public void setSegmentsCollection(Collection<Segments> segmentsCollection) {
        this.segmentsCollection = segmentsCollection;
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
        if (!(object instanceof Flights)) {
            return false;
        }
        Flights other = (Flights) object;
        if ((this.flightId == null && other.flightId != null) || (this.flightId != null && !this.flightId.equals(other.flightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crawler.dtos.Flights[ flightId=" + flightId + " ]";
    }
    
}
