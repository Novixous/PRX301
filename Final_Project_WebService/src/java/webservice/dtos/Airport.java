/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.dtos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "Airport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a")
    , @NamedQuery(name = "Airport.findByIataCode", query = "SELECT a FROM Airport a WHERE a.iataCode = :iataCode")
    , @NamedQuery(name = "Airport.findByCity", query = "SELECT a FROM Airport a WHERE a.city = :city")
    , @NamedQuery(name = "Airport.findByContinent", query = "SELECT a FROM Airport a WHERE a.continent = :continent")
    , @NamedQuery(name = "Airport.findByName", query = "SELECT a FROM Airport a WHERE a.name = :name")
    , @NamedQuery(name = "Airport.findByType", query = "SELECT a FROM Airport a WHERE a.type = :type")})
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "iata_code")
    private String iataCode;
    @Size(max = 255)
    @Column(name = "city")
    private String city;
    @Column(name = "continent")
    private Integer continent;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private Integer type;
    @OneToMany(mappedBy = "departure")
    private List<Flight> flightList;
    @OneToMany(mappedBy = "arrival")
    private List<Flight> flightList1;
    @JoinColumn(name = "country", referencedColumnName = "Code")
    @ManyToOne
    private Country country;
    @OneToMany(mappedBy = "departure")
    private List<Segment> segmentList;
    @OneToMany(mappedBy = "arrival")
    private List<Segment> segmentList1;

    public Airport() {
    }

    public Airport(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getContinent() {
        return continent;
    }

    public void setContinent(Integer continent) {
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @XmlTransient
    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    @XmlTransient
    public List<Flight> getFlightList1() {
        return flightList1;
    }

    public void setFlightList1(List<Flight> flightList1) {
        this.flightList1 = flightList1;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @XmlTransient
    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    @XmlTransient
    public List<Segment> getSegmentList1() {
        return segmentList1;
    }

    public void setSegmentList1(List<Segment> segmentList1) {
        this.segmentList1 = segmentList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iataCode != null ? iataCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.iataCode == null && other.iataCode != null) || (this.iataCode != null && !this.iataCode.equals(other.iataCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webservice.dtos.Airport[ iataCode=" + iataCode + " ]";
    }
    
}
