package com.example.springtest.domain;

public class ResultWay {
    private DoubleWay doubleWay;
    private Oneway oneway;
    private double resultScore;

    public ResultWay(DoubleWay doubleWay) {
        this.doubleWay = doubleWay;
    }

    public ResultWay(Oneway oneway) {
        this.oneway = oneway;
    }

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
        }else{
            return null;
        }
    }
}
