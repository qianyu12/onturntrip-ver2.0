package com.example.springtest.service;

import com.example.springtest.domain.DoubleWay;
import com.example.springtest.domain.Oneway;

import java.io.IOException;
import java.util.List;

public interface TrainService {
    List<Oneway> getDirectOneWayByStationName(String startStation, String endStation);

    List<Oneway> getDirectOneWay(String start, String end);

//    List<DoubleWay> getTransferWayByStation(String startStation, String endStation) throws IOException;

    List<DoubleWay> getTransferWay(String start, String end) throws IOException;

    List<Oneway> orderDirectWay(List<Oneway> onewayList,int order);

}
