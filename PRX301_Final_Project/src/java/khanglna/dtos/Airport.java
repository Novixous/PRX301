/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.dtos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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

    //Africa
    public static final int AF = 0;
    //Antarctica
    public static final int AN = 1;
    //Asia
    public static final int AS = 2;
    //Eurpope
    public static final int EU = 2;
    //North America
    public static final int NA = 2;
    //Oceania
    public static final int OC = 2;
    //South America
    public static final int SA = 2;
    public static final int SMALL_AIRPORT = 0;
    public static final int MEDIUM_AIRPORT = 1;
    public static final int LARGE_AIRPORT = 2;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
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
    @JoinColumn(name = "country", referencedColumnName = "Code")
    @ManyToOne
    private Country country;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        return "khanglna.dtos.Airport[ iataCode=" + iataCode + " ]";
    }

}
