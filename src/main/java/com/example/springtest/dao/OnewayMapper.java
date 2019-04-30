package com.example.springtest.dao;

import com.example.springtest.domain.DoubleWay;
import com.example.springtest.domain.Oneway;
import com.example.springtest.utils.MyMapper;

import java.util.List;

public interface OnewayMapper extends MyMapper<Oneway> {
   List<Oneway> getDirectOneWayByTlc(String fromNo,String toNo);
   List<Oneway> getDirectOneWayByStationNames(String fromStation,String toStation);
   List<Oneway> getDirecOneWay(String from,String to);
   List<DoubleWay> getDoubleWayByStationName(String startStation,String transferStation,String endStation);
   List<DoubleWay> getDoubleWay(String start,List<String> middle,String end);
}