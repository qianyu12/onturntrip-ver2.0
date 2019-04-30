package com.example.springtest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import javax.persistence.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "domestic_flight")
public class DomesticFlight extends Way{
    @Column(name = "airline_no")
    private Integer airlineNo;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "airline_name")
    private String airlineName;

    @Column(name = "arr_airport_name")
    private String arrAirportName;

    @Column(name = "arr_airport_tlc")
    private String arrAirportTlc;

    @Column(name = "arr_city_name")
    private String arrCityName;

    @Column(name = "arr_city_tlc")
    private String arrCityTlc;

    @Column(name = "arr_terminal")
    private String arrTerminal;

    @Column(name = "dep_airport_name")
    private String depAirportName;

    @Column(name = "dep_airport_tlc")
    private String depAirportTlc;

    @Column(name = "dep_city_name")
    private String depCityName;

    @Column(name = "dep_city_tlc")
    private String depCityTlc;

    @Column(name = "dep_terminal")
    private String depTerminal;

    @Column(name = "arr_date")
    private Date arrDate;

    @Column(name = "dep_date")
    private Date depDate;

    @Column(name = "craft_type_name")
    private String craftTypeName;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "punctuality_rate")
    private String punctualityRate;

    @Column(name = "lowest_enconomy_price")
    private Integer lowestEnconomyPrice;

    @Column(name = "lowest_first_price")
    private Integer lowestFirstPrice;

    @Column(name = "lowest_business_price")
    private Integer lowestBusinessPrice;

    /**
     * @return airline_no
     */
    public Integer getAirlineNo() {
        return airlineNo;
    }

    /**
     * @param airlineNo
     */
    public void setAirlineNo(Integer airlineNo) {
        this.airlineNo = airlineNo;
    }

    /**
     * @return airline_code
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * @param airlineCode
     */
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    /**
     * @return airline_name
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * @param airlineName
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * @return arr_airport_name
     */
    public String getArrAirportName() {
        return arrAirportName;
    }

    /**
     * @param arrAirportName
     */
    public void setArrAirportName(String arrAirportName) {
        this.arrAirportName = arrAirportName;
    }

    /**
     * @return arr_airport_tlc
     */
    public String getArrAirportTlc() {
        return arrAirportTlc;
    }

    /**
     * @param arrAirportTlc
     */
    public void setArrAirportTlc(String arrAirportTlc) {
        this.arrAirportTlc = arrAirportTlc;
    }

    /**
     * @return arr_city_name
     */
    public String getArrCityName() {
        return arrCityName;
    }

    /**
     * @param arrCityName
     */
    public void setArrCityName(String arrCityName) {
        this.arrCityName = arrCityName;
    }

    /**
     * @return arr_city_tlc
     */
    public String getArrCityTlc() {
        return arrCityTlc;
    }

    /**
     * @param arrCityTlc
     */
    public void setArrCityTlc(String arrCityTlc) {
        this.arrCityTlc = arrCityTlc;
    }

    /**
     * @return arr_terminal
     */
    public String getArrTerminal() {
        return arrTerminal;
    }

    /**
     * @param arrTerminal
     */
    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    /**
     * @return dep_airport_name
     */
    public String getDepAirportName() {
        return depAirportName;
    }

    /**
     * @param depAirportName
     */
    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName;
    }

    /**
     * @return dep_airport_tlc
     */
    public String getDepAirportTlc() {
        return depAirportTlc;
    }

    /**
     * @param depAirportTlc
     */
    public void setDepAirportTlc(String depAirportTlc) {
        this.depAirportTlc = depAirportTlc;
    }

    /**
     * @return dep_city_name
     */
    public String getDepCityName() {
        return depCityName;
    }

    /**
     * @param depCityName
     */
    public void setDepCityName(String depCityName) {
        this.depCityName = depCityName;
    }

    /**
     * @return dep_city_tlc
     */
    public String getDepCityTlc() {
        return depCityTlc;
    }

    /**
     * @param depCityTlc
     */
    public void setDepCityTlc(String depCityTlc) {
        this.depCityTlc = depCityTlc;
    }

    /**
     * @return dep_terminal
     */
    public String getDepTerminal() {
        return depTerminal;
    }

    /**
     * @param depTerminal
     */
    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    /**
     * @return arr_date
     */
    public Date getArrDate() {
        return arrDate;
    }

    /**
     * @param arrDate
     */
    public void setArrDate(Date arrDate) {
        this.arrDate = arrDate;
    }

    /**
     * @return dep_date
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * @param depDate
     */
    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    /**
     * @return craft_type_name
     */
    public String getCraftTypeName() {
        return craftTypeName;
    }

    /**
     * @param craftTypeName
     */
    public void setCraftTypeName(String craftTypeName) {
        this.craftTypeName = craftTypeName;
    }

    /**
     * @return duration_days
     */
    public Integer getDurationDays() {
        return durationDays;
    }

    /**
     * @param durationDays
     */
    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    /**
     * @return flight_number
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return punctuality_rate
     */
    public String getPunctualityRate() {
        return punctualityRate;
    }

    /**
     * @param punctualityRate
     */
    public void setPunctualityRate(String punctualityRate) {
        this.punctualityRate = punctualityRate;
    }

    /**
     * @return lowest_enconomy_price
     */
    public Integer getLowestEnconomyPrice() {
        return lowestEnconomyPrice;
    }

    /**
     * @param lowestEnconomyPrice
     */
    public void setLowestEnconomyPrice(Integer lowestEnconomyPrice) {
        this.lowestEnconomyPrice = lowestEnconomyPrice;
    }

    /**
     * @return lowest_first_price
     */
    public Integer getLowestFirstPrice() {
        return lowestFirstPrice;
    }

    /**
     * @param lowestFirstPrice
     */
    public void setLowestFirstPrice(Integer lowestFirstPrice) {
        this.lowestFirstPrice = lowestFirstPrice;
    }

    /**
     * @return lowest_business_price
     */
    public Integer getLowestBusinessPrice() {
        return lowestBusinessPrice;
    }

    /**
     * @param lowestBusinessPrice
     */
    public void setLowestBusinessPrice(Integer lowestBusinessPrice) {
        this.lowestBusinessPrice = lowestBusinessPrice;
    }

    @Override
    public double getPrice() {
         return this.getLowestEnconomyPrice();
    }

    @Override
    public double getDurations() {
        return (this.getArrDate().getTime()-this.getDepDate().getTime())/(1000*60);
    }

    @Override
    public double getComfort() {
        return 0;
    }

    @Override
    public double getTranferTime() {
        return 0;
    }
}