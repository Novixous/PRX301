/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.utils;

/**
 *
 * @author Novixous
 */
public class MaxParameters {

    Double maxCost;
    Double maxDuration;
    Double maxArrival;
    Long maxLayoverDuration;

    public MaxParameters(Double maxCost, Double maxDuration, Double maxArrival, Long maxLayoverDuration) {
        this.maxCost = maxCost;
        this.maxDuration = maxDuration;
        this.maxArrival = maxArrival;
        this.maxLayoverDuration = maxLayoverDuration;
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public Double getMaxDuration() {
        return maxDuration;
    }

    public Double getMaxArrival() {
        return maxArrival;
    }

    public Long getMaxLayoverDuration() {
        return maxLayoverDuration;
    }

}
