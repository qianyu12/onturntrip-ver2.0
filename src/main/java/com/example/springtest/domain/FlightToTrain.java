package com.example.springtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightToTrain extends Way {
    private DomesticFlight firstTrip;
    private Oneway secondTrip;

    public DomesticFlight getFirstTrip() {
        return firstTrip;
    }

    public void setFirstTrip(DomesticFlight firstTrip) {
        this.firstTrip = firstTrip;
    }

    public Oneway getSecondTrip() {
        return secondTrip;
    }

    public FlightToTrain(DomesticFlight firstTrip, Oneway secondTrip) {
        this.firstTrip = firstTrip;
        this.secondTrip = secondTrip;
    }

    public void setSecondTrip(Oneway secondTrip) {
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
        Date date = secondTrip.getDate();
        Date flightArriveTime = firstTrip.getArrDate();
        String depTime = secondTrip.getDepTime();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR,Integer.valueOf(depTime.split(":")[0]));
        calendar.set(GregorianCalendar.MINUTE,Integer.valueOf(depTime.split(":")[1]));
        date = calendar.getTime();
//        System.out.println("flightArriveTime:"+flightArriveTime);
//        System.out.println("trainDepDate:"+date);
        return (date.getTime()-flightArriveTime.getTime())/(1000*60);
    }
}
