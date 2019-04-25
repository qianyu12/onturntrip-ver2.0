package com.example.springtest.dao;

import com.example.springtest.domain.DoubleWay;
import com.example.springtest.domain.Oneway;
import com.example.springtest.utils.MyMapper;

import java.util.List;

public interface OnewayMapper extends MyMapper<Oneway> {
    List<Oneway> getDirectOneWayByStationName(String startStation, String endStation);
    List<DoubleWay> getDoubleWayByStationName(String startStation, String transferStation, String endStation);
}