package com.example.springtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoubleFight extends Way{
    private DomesticFlight firstTrip;
    private DomesticFlight secondTrip;

    public DomesticFlight getFirstTrip() {
        return firstTrip;
    }

    public void setFirstTrip(DomesticFlight firstTrip) {
        this.firstTrip = firstTrip;
    }

    public DomesticFlight getSecondTrip() {
        return secondTrip;
    }

    public void setSecondTrip(DomesticFlight secondTrip) {
        this.secondTrip = secondTrip;
    }

    @Override
    public double getPrice() {
        return firstTrip.getPrice()+secondTrip.getPrice();
    }

    @Override
    public double getDurations() {
        return (secondTrip.getArrDate().getTime()-firstTrip.getDepDate().getTime())/(1000*60);
    }

    @Override
    public double getComfort() {
        return 10;
    }

    @Override
    public double getTranferTime() {
        return (secondTrip.getDepDate().getTime()-firstTrip.getArrDate().getTime())/(1000*60);
    }
}
