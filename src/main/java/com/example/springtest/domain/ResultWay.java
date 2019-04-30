package com.example.springtest.domain;

import com.example.springtest.service.FlightService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"way"})
public class ResultWay {
    private DoubleWay doubleWay;
    private Oneway oneway;
    private DomesticFlight flight;
    private DoubleFight doubleFight;
    private TrainToFlight trainToFlight;
    private FlightToTrain flightToTrain;

    private double totalPrice;
    private double totalTime;
    private double transferTime;
    private int comfortDegree;
    private Date departTime;

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    private double resultScore;
    public DomesticFlight getFlight() {
        return flight;
    }

    public void setFlight(DomesticFlight flight) {
        this.flight = flight;
    }

    public DoubleFight getDoubleFight() {
        return doubleFight;
    }

    public void setDoubleFight(DoubleFight doubleFight) {
        this.doubleFight = doubleFight;
    }

    public TrainToFlight getTrainToFlight() {
        return trainToFlight;
    }

    public void setTrainToFlight(TrainToFlight trainToFlight) {
        this.trainToFlight = trainToFlight;
    }

    public FlightToTrain getFlightToTrain() {
        return flightToTrain;
    }

    public void setFlightToTrain(FlightToTrain flightToTrain) {
        this.flightToTrain = flightToTrain;
    }

    public ResultWay(DoubleWay doubleWay) {
        this.doubleWay = doubleWay;
    }

    public ResultWay(Oneway oneway) {
        this.oneway = oneway;
    }

    public ResultWay(DomesticFlight flight) {
        this.flight = flight;
    }

    public ResultWay(FlightToTrain flightToTrain) {
        this.flightToTrain = flightToTrain;
    }

    public ResultWay(TrainToFlight trainToFlight) {
        this.trainToFlight = trainToFlight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(double transferTime) {
        this.transferTime = transferTime;
    }

    public int getComfortDegree() {
        return comfortDegree;
    }

    public void setComfortDegree(int  comfortDegree) {
        this.comfortDegree = comfortDegree;
    }

    public ResultWay(DoubleFight doubleFight) {
        this.doubleFight = doubleFight;
    }

    public ResultWay(){}

    public DoubleWay getDoubleWay() {
        return doubleWay;
    }

    public void setDoubleWay(DoubleWay doubleWay) {
        this.doubleWay = doubleWay;
    }

    public Oneway getOneway() {
        return oneway;
    }

    public void setOneway(Oneway oneway) {
        this.oneway = oneway;
    }

    public double getResultScore() {
        return resultScore;
    }

    public void setResultScore(double resultScore) {
        this.resultScore = resultScore;
    }

    public Object getWay(){
        if (doubleWay!=null){
            return doubleWay;
        }else if(oneway!=null){
            return oneway;
        }else if(flight!=null){
            return flight;
        }else if(doubleFight!=null){
            return doubleFight;
        }else if(flightToTrain!=null){
            return flightToTrain;
        }else if(trainToFlight!=null){
            return trainToFlight;
        }else{
            return null;
        }
    }
    @Override
    public String toString(){
        if (doubleWay!=null){
            return doubleWay.toString();
        }else if(oneway!=null){
            return oneway.getTrainNo();
        }else if(flight!=null){
            return flight.getFlightNumber();
        }else if(doubleFight!=null){
            return doubleFight.getFirstTrip().getFlightNumber()+"->"+doubleFight.getSecondTrip().getFlightNumber();
        }else if(flightToTrain!=null){
            return flightToTrain.getFirstTrip().getFlightNumber()+"->"+flightToTrain.getSecondTrip().getTrainNo();
        }else if(trainToFlight!=null){
            return trainToFlight.getFirstTrip().getTrainNo()+"->"+trainToFlight.getSecondTrip().getFlightNumber();
        }else{
            return "";
        }
    }

}
