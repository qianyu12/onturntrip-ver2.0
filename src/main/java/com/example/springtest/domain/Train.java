package com.example.springtest.domain;

import javax.persistence.*;

public class Train {
    @Column(name = "train_no")
    private String trainNo;

    @Column(name = "train_code")
    private String trainCode;

    @Column(name = "from_station")
    private String fromStation;

    @Column(name = "to_station")
    private String toStation;

    /**
     * @return train_no
     */
    public String getTrainNo() {
        return trainNo;
    }

    /**
     * @param trainNo
     */
    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    /**
     * @return train_code
     */
    public String getTrainCode() {
        return trainCode;
    }

    /**
     * @param trainCode
     */
    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    /**
     * @return from_station
     */
    public String getFromStation() {
        return fromStation;
    }

    /**
     * @param fromStation
     */
    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    /**
     * @return to_station
     */
    public String getToStation() {
        return toStation;
    }

    /**
     * @param toStation
     */
    public void setToStation(String toStation) {
        this.toStation = toStation;
    }
}