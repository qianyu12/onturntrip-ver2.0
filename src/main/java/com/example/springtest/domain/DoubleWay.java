package com.example.springtest.domain;

public class DoubleWay {
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

}
