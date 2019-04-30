package com.example.springtest.domain;

import java.sql.Time;
import java.util.Date;
import javax.persistence.*;

public class Oneway extends Way{
    private Integer id;

    @Column(name = "train_telecode")
    private String trainTelecode;

    @Column(name = "train_no")
    private String trainNo;

    @Column(name = "start_ts_no")
    private String startTsNo;

    @Column(name = "end_ts_no")
    private String endTsNo;

    @Column(name = "from_ts_no")
    private String fromTsNo;

    @Column(name = "to_ts_no")
    private String toTsNo;

    @Column(name = "dep_time")
    private String depTime;

    @Column(name = "arr_time")
    private String arrTime;

    @Column(name = "duration")
    private String duration;
    @Column(name = "date")
    private Date date;


    @Column(name = "business_price")
    private String businessPrice;

    @Column(name = "first_price")
    private String firstPrice;

    @Column(name = "second_price")
    private String secondPrice;

    @Column(name = "high_soft_price")
    private String highSoftPrice;

    @Column(name = "soft_first_price")
    private String softFirstPrice;

    @Column(name = "moter_price")
    private String moterPrice;

    @Column(name = "hard_second_price")
    private String hardSecondPrice;

    @Column(name = "soft_price")
    private String softPrice;

    @Column(name = "hard_price")
    private String hardPrice;

    @Column(name = "no_seat_price")
    private String noSeatPrice;

    public double getPrice(boolean isBusiness){
        if(isBusiness){
            if(businessPrice!=null){
                return Double.valueOf(businessPrice.substring(1));
            }else if(firstPrice!=null){
                return Double.valueOf(firstPrice.substring(1));
            }else if(highSoftPrice!=null){
                return Double.valueOf(highSoftPrice.substring(1));
            }else if(softFirstPrice!=null){
                return Double.valueOf(softFirstPrice.substring(1));
            }else{
                return Double.valueOf(noSeatPrice.substring(1));
            }
        }else{
            return Double.valueOf(noSeatPrice.substring(1));
        }
    }

    public Station getFromStation() {
        return fromStation;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }

    public Station getToStation() {
        return toStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }
    private Station fromStation;
   private Station toStation;
   @Override
   public String toString(){
       return trainNo+" "+fromStation.getTsZh()+"->"+toStation.getTsZh()+"---"+duration+"---"+depTime;
   }
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
     * @return train_telecode
     */
    public String getTrainTelecode() {
        return trainTelecode;
    }

    /**
     * @param trainTelecode
     */
    public void setTrainTelecode(String trainTelecode) {
        this.trainTelecode = trainTelecode;
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
     * @return start_ts_no
     */
    public String getStartTsNo() {
        return startTsNo;
    }

    /**
     * @param startTsNo
     */
    public void setStartTsNo(String startTsNo) {
        this.startTsNo = startTsNo;
    }

    /**
     * @return end_ts_no
     */
    public String getEndTsNo() {
        return endTsNo;
    }

    /**
     * @param endTsNo
     */
    public void setEndTsNo(String endTsNo) {
        this.endTsNo = endTsNo;
    }

    /**
     * @return from_ts_no
     */
    public String getFromTsNo() {
        return fromTsNo;
    }

    /**
     * @param fromTsNo
     */
    public void setFromTsNo(String fromTsNo) {
        this.fromTsNo = fromTsNo;
    }

    /**
     * @return to_ts_no
     */
    public String getToTsNo() {
        return toTsNo;
    }

    /**
     * @param toTsNo
     */
    public void setToTsNo(String toTsNo) {
        this.toTsNo = toTsNo;
    }

    /**
     * @return dep_time
     */
    public String getDepTime() {
        return depTime;
    }

    /**
     * @param depTime
     */
    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    /**
     * @return arr_time
     */
    public String getArrTime() {
        return arrTime;
    }

    /**
     * @param arrTime
     */
    public void setArrTime( String arrTime) {
        this.arrTime = arrTime;
    }

    /**
     * @return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(String  duration) {
        this.duration = duration;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return business_price
     */
    public String getBusinessPrice() {
        return businessPrice;
    }

    /**
     * @param businessPrice
     */
    public void setBusinessPrice(String businessPrice) {
        this.businessPrice = businessPrice;
    }

    /**
     * @return first_price
     */
    public String getFirstPrice() {
        return firstPrice;
    }

    /**
     * @param firstPrice
     */
    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    /**
     * @return second_price
     */
    public String getSecondPrice() {
        return secondPrice;
    }

    /**
     * @param secondPrice
     */
    public void setSecondPrice(String secondPrice) {
        this.secondPrice = secondPrice;
    }

    /**
     * @return high_soft_price
     */
    public String getHighSoftPrice() {
        return highSoftPrice;
    }

    /**
     * @param highSoftPrice
     */
    public void setHighSoftPrice(String highSoftPrice) {
        this.highSoftPrice = highSoftPrice;
    }

    /**
     * @return soft_first_price
     */
    public String getSoftFirstPrice() {
        return softFirstPrice;
    }

    /**
     * @param softFirstPrice
     */
    public void setSoftFirstPrice(String softFirstPrice) {
        this.softFirstPrice = softFirstPrice;
    }

    /**
     * @return moter_price
     */
    public String getMoterPrice() {
        return moterPrice;
    }

    /**
     * @param moterPrice
     */
    public void setMoterPrice(String moterPrice) {
        this.moterPrice = moterPrice;
    }

    /**
     * @return hard_second_price
     */
    public String getHardSecondPrice() {
        return hardSecondPrice;
    }

    /**
     * @param hardSecondPrice
     */
    public void setHardSecondPrice(String hardSecondPrice) {
        this.hardSecondPrice = hardSecondPrice;
    }

    /**
     * @return soft_price
     */
    public String getSoftPrice() {
        return softPrice;
    }

    /**
     * @param softPrice
     */
    public void setSoftPrice(String softPrice) {
        this.softPrice = softPrice;
    }

    /**
     * @return hard_price
     */
    public String getHardPrice() {
        return hardPrice;
    }

    /**
     * @param hardPrice
     */
    public void setHardPrice(String hardPrice) {
        this.hardPrice = hardPrice;
    }

    /**
     * @return no_seat_price
     */
    public String getNoSeatPrice() {
        return noSeatPrice;
    }

    /**
     * @param noSeatPrice
     */
    public void setNoSeatPrice(String noSeatPrice) {
        this.noSeatPrice = noSeatPrice;
    }

    @Override
    public double getPrice() {
        return this.getPrice(false);
    }

    @Override
    public double getDurations() {
        String[] result = getDuration().split(":");
        return Long.valueOf(result[0]) * 60 + Long.valueOf(result[1]);
    }

    @Override
    public double getComfort() {
        return juegeWeightByNo(this.getTrainNo().charAt(0));
    }

    @Override
    public double getTranferTime() {
        return 0;
    }
    public int juegeWeightByNo(char no) {
        switch (no) {
            case 'G':
                return 5;
            case 'D':
                return 10;
            case 'Z':
                return 25;
            case 'K':
                return 35;
            default:
                return 40;
        }
    }
}