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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Novixous
 */
@Entity
@Table(name = "CountryTravelCost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CountryTravelCost.findAll", query = "SELECT c FROM CountryTravelCost c")
    , @NamedQuery(name = "CountryTravelCost.findByCountryCode", query = "SELECT c FROM CountryTravelCost c WHERE c.countryCode = :countryCode")
    , @NamedQuery(name = "CountryTravelCost.findByCostCheap", query = "SELECT c FROM CountryTravelCost c WHERE c.costCheap = :costCheap")
    , @NamedQuery(name = "CountryTravelCost.findByCostMedium", query = "SELECT c FROM CountryTravelCost c WHERE c.costMedium = :costMedium")
    , @NamedQuery(name = "CountryTravelCost.findByCostHigh", query = "SELECT c FROM CountryTravelCost c WHERE c.costHigh = :costHigh")})
public class CountryTravelCost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "country_code")
    private String countryCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cost_cheap")
    private Double costCheap;
    @Column(name = "cost_medium")
    private Double costMedium;
    @Column(name = "cost_high")
    private Double costHigh;
    @JoinColumn(name = "country_code", referencedColumnName = "Code", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Country country;

    public CountryTravelCost() {
    }

    public CountryTravelCost(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getCostCheap() {
        return costCheap;
    }

    public void setCostCheap(Double costCheap) {
        this.costCheap = costCheap;
    }

    public Double getCostMedium() {
        return costMedium;
    }

    public void setCostMedium(Double costMedium) {
        this.costMedium = costMedium;
    }

    public Double getCostHigh() {
        return costHigh;
    }

    public void setCostHigh(Double costHigh) {
        this.costHigh = costHigh;
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
        hash += (countryCode != null ? countryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CountryTravelCost)) {
            return false;
        }
        CountryTravelCost other = (CountryTravelCost) object;
        if ((this.countryCode == null && other.countryCode != null) || (this.countryCode != null && !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "crawler_project.dtos.CountryTravelCost[ countryCode=" + countryCode + " ]";
    }
    
}
