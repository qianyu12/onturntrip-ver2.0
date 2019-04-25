package com.example.springtest.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Oneway {
    @Id
    private Integer id;

    @Column(name = "start_station")
    private String startStation;

    @Column(name = "end_station")
    private String endStation;

    @Column(name = "train_no")
    private String trainNo;

    private BigDecimal price;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private Integer ticket;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return start_station
     */
    public String getStartStation() {
        return startStation;
    }

    /**
     * @param startStation
     */
    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    /**
     * @return end_station
     */
    public String getEndStation() {
        return endStation;
    }

    /**
     * @param endStation
     */
    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

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
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return ticket
     */
    public Integer getTicket() {
        return ticket;
    }

    /**
     * @param ticket
     */
    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }
    @Override
    public String toString(){
        return startStation+"->"+endStation+"no:"+trainNo;
    }
}