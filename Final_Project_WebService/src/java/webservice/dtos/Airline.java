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
@Table(name = "Airline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airline.findAll", query = "SELECT a FROM Airline a")
    , @NamedQuery(name = "Airline.findByAirlineCode", query = "SELECT a FROM Airline a WHERE a.airlineCode = :airlineCode")
    , @NamedQuery(name = "Airline.findByName", query = "SELECT a FROM Airline a WHERE a.name = :name")})
public class Airline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "airline_code")
    private String airlineCode;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "airlineCode")
    private List<Segment> segmentList;

    public Airline() {
    }

    public Airline(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airlineCode != null ? airlineCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airline)) {
            return false;
        }
        Airline other = (Airline) object;
        if ((this.airlineCode == null && other.airlineCode != null) || (this.airlineCode != null && !this.airlineCode.equals(other.airlineCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webservice.dtos.Airline[ airlineCode=" + airlineCode + " ]";
    }
    
}
