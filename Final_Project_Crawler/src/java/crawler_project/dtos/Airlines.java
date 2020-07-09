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
@Table(name = "Airlines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airlines.findAll", query = "SELECT a FROM Airlines a")
    , @NamedQuery(name = "Airlines.findByAirlineCode", query = "SELECT a FROM Airlines a WHERE a.airlineCode = :airlineCode")
    , @NamedQuery(name = "Airlines.findByName", query = "SELECT a FROM Airlines a WHERE a.name = :name")})
public class Airlines implements Serializable {

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
    private Collection<Segments> segmentsCollection;

    public Airlines() {
    }

    public Airlines(String airlineCode) {
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
    public Collection<Segments> getSegmentsCollection() {
        return segmentsCollection;
    }

    public void setSegmentsCollection(Collection<Segments> segmentsCollection) {
        this.segmentsCollection = segmentsCollection;
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
        if (!(object instanceof Airlines)) {
            return false;
        }
        Airlines other = (Airlines) object;
        if ((this.airlineCode == null && other.airlineCode != null) || (this.airlineCode != null && !this.airlineCode.equals(other.airlineCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crawler.dtos.Airlines[ airlineCode=" + airlineCode + " ]";
    }
    
}
