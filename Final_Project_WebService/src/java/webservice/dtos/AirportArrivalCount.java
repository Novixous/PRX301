/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Novixous
 */
@XmlRootElement(name = "AirportArrivalCount")
@XmlAccessorType(XmlAccessType.FIELD)
public class AirportArrivalCount implements Comparable<AirportArrivalCount> {

    private Integer arrivalCount;

    private String city;

    private String country;

    public AirportArrivalCount() {
    }

    public AirportArrivalCount(Integer arrivalCount, String city, String country) {
        this.arrivalCount = arrivalCount;
        this.city = city;
        this.country = country;
    }

    public Integer getArrivalCount() {
        return arrivalCount;
    }

    public void setArrivalCount(Integer arrivalCount) {
        this.arrivalCount = arrivalCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int compareTo(AirportArrivalCount o) {
        return o.getArrivalCount().compareTo(this.arrivalCount); //To change body of generated methods, choose Tools | Templates.
    }

}
