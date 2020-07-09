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
    private Collection<Flights> flightsCollection;
    @OneToMany(mappedBy = "arrival")
    private Collection<Flights> flightsCollection1;
    @JoinColumn(name = "country", referencedColumnName = "Code")
    @ManyToOne
    private Country country;
    @OneToMany(mappedBy = "departure")
    private Collection<Segments> segmentsCollection;
    @OneToMany(mappedBy = "arrival")
    private Collection<Segments> segmentsCollection1;

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
    public Collection<Flights> getFlightsCollection() {
        return flightsCollection;
    }

    public void setFlightsCollection(Collection<Flights> flightsCollection) {
        this.flightsCollection = flightsCollection;
    }

    @XmlTransient
    public Collection<Flights> getFlightsCollection1() {
        return flightsCollection1;
    }

    public void setFlightsCollection1(Collection<Flights> flightsCollection1) {
        this.flightsCollection1 = flightsCollection1;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @XmlTransient
    public Collection<Segments> getSegmentsCollection() {
        return segmentsCollection;
    }

    public void setSegmentsCollection(Collection<Segments> segmentsCollection) {
        this.segmentsCollection = segmentsCollection;
    }

    @XmlTransient
    public Collection<Segments> getSegmentsCollection1() {
        return segmentsCollection1;
    }

    public void setSegmentsCollection1(Collection<Segments> segmentsCollection1) {
        this.segmentsCollection1 = segmentsCollection1;
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
        return "crawler.dtos.Airport[ iataCode=" + iataCode + " ]";
    }

}
