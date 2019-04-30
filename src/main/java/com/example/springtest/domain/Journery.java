package com.example.springtest.domain;

import java.util.Date;
import javax.persistence.*;

public class Journery {
    @Column(name = "journey_id")
    private Integer journeyId;

    @Column(name = "start_city")
    private String startCity;

    @Column(name = "end_city")
    private String endCity;

    private String way;

    @Column(name = "user_id")
    private Integer userId;

    private Date time;

    @Column(name = "isCutOff")
    private Integer iscutoff;

    /**
     * @return journey_id
     */
    public Integer getJourneyId() {
        return journeyId;
    }

    /**
     * @param journeyId
     */
    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    /**
     * @return start_city
     */
    public String getStartCity() {
        return startCity;
    }

    /**
     * @param startCity
     */
    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    /**
     * @return end_city
     */
    public String getEndCity() {
        return endCity;
    }

    /**
     * @param endCity
     */
    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    /**
     * @return way
     */
    public String getWay() {
        return way;
    }

    /**
     * @param way
     */
    public void setWay(String way) {
        this.way = way;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return isCutOff
     */
    public Integer getIscutoff() {
        return iscutoff;
    }

    /**
     * @param iscutoff
     */
    public void setIscutoff(Integer iscutoff) {
        this.iscutoff = iscutoff;
    }
}