package com.example.springtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainToFlight extends  Way{
    private Oneway firstTrip;
    private DomesticFlight secondTrip;

    public Oneway getFirstTrip() {
        return firstTrip;
    }

    public void setFirstTrip(Oneway firstTrip) {
        this.firstTrip = firstTrip;
    }

    public TrainToFlight(Oneway firstTrip, DomesticFlight secondTrip) {
        this.firstTrip = firstTrip;
        this.secondTrip = secondTrip;
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
        return firstTrip.getDurations()+secondTrip.getDurations()+getTranferTime();
    }

    @Override
    public double getComfort() {
        return firstTrip.getComfort()+secondTrip.getComfort()+15;
    }

    @Override
    public double getTranferTime() {
        Date date = firstTrip.getDate();
        Date flightDepDate = secondTrip.getDepDate();
        String depTime = firstTrip.getDepTime();
        double duration = firstTrip.getDurations();
//        System.out.println("before"+depTime+firstTrip.getTrainNo());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR,Integer.valueOf(depTime.split(":")[0]));
        calendar.set(GregorianCalendar.MINUTE,Integer.valueOf(depTime.split(":")[1]));
        calendar.add(GregorianCalendar.MINUTE,(int)duration);
        date = calendar.getTime();
//        System.out.println("after:"+date);
//        System.out.println("flight"+flightDepDate+secondTrip.getFlightNumber());
//        System.out.println("result"+(flightDepDate.getTime()-date.getTime())/(1000*60));
        return (flightDepDate.getTime()-date.getTime())/(1000*60);
    }
}
