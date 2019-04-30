package com.example.springtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoubleWay extends Way{
    private Oneway firstTrip;

    public Oneway getFirstTrip() {
        return firstTrip;
    }

    public void setFirstTrip(Oneway firstTrip) {
        this.firstTrip = firstTrip;
    }

    public Oneway getSecondTrip() {
        return secondTrip;
    }

    public void setSecondTrip(Oneway secondTrip) {
        this.secondTrip = secondTrip;
    }

    private Oneway secondTrip;

    @Override
    public String toString(){
        return firstTrip.getTrainNo()+"->"+secondTrip.getTrainNo();
    }

    @Override
    public double getPrice() {
        return firstTrip.getPrice()+secondTrip.getPrice();
    }

    @Override
    public double getDurations() {
        return firstTrip.getDurations()+secondTrip.getDurations();
    }

    @Override
    public double getComfort() {
        return firstTrip.getComfort()+secondTrip.getComfort()+10;
    }

    @Override
    public double getTranferTime() {
        return getTransferMinute(firstTrip,secondTrip);
    }

    public Long getTransferMinute(Oneway firstTrip, Oneway secondTrip) {
        String firstArriveTime = firstTrip.getArrTime();
        String secondDepTime = secondTrip.getDepTime();
        Date firstTripDate = firstTrip.getDate();
        Date secondTripDate = secondTrip.getDate();
        Calendar firstTripCalender = new GregorianCalendar();
        firstTripCalender.setTime(firstTripDate);
        firstTripCalender.set(GregorianCalendar.HOUR,Integer.valueOf(firstArriveTime.split(":")[0]));
        firstTripCalender.set(GregorianCalendar.MINUTE,Integer.valueOf(firstArriveTime.split(":")[1]));
        firstTripDate = firstTripCalender.getTime();
        Calendar secondTripCalender = new GregorianCalendar();
        secondTripCalender.setTime(secondTripDate);
        secondTripCalender.set(GregorianCalendar.HOUR,Integer.valueOf(secondDepTime.split(":")[0]));
        secondTripCalender.set(GregorianCalendar.MINUTE,Integer.valueOf(secondDepTime.split(":")[1]));
        secondTripDate = secondTripCalender.getTime();
        return (secondTripDate.getTime()-firstTripDate.getTime())/(1000*60);
    }
}
