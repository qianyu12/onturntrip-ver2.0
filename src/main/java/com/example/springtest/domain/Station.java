package com.example.springtest.domain;

import javax.persistence.*;

public class Station {
    @Column(name = "ts_id")
    private Integer tsId;

    @Column(name = "ts_zh")
    private String tsZh;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "ts_tlc")
    private String tsTlc;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private String cityName;

    /**
     * @return ts_id
     */
    public Integer getTsId() {
        return tsId;
    }

    /**
     * @param tsId
     */
    public void setTsId(Integer tsId) {
        this.tsId = tsId;
    }

    /**
     * @return ts_zh
     */
    public String getTsZh() {
        return tsZh;
    }

    /**
     * @param tsZh
     */
    public void setTsZh(String tsZh) {
        this.tsZh = tsZh;
    }

    /**
     * @return city_id
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return ts_tlc
     */
    public String getTsTlc() {
        return tsTlc;
    }

    /**
     * @param tsTlc
     */
    public void setTsTlc(String tsTlc) {
        this.tsTlc = tsTlc;
    }
}