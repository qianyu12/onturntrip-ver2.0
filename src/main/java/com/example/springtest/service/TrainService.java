package com.example.springtest.service;

import com.example.springtest.domain.DoubleWay;
import com.example.springtest.domain.Oneway;
import com.example.springtest.domain.ResultWay;
import com.example.springtest.utils.helpclass.Weight;

import java.io.IOException;
import java.util.List;

public interface TrainService {
    List<Oneway> getDirectOneWayByStationName(String startStation, String endStation);
    List<Oneway> getDirectOneWay(String start,String end);
    List<DoubleWay> getTransferWayByStation(String startStation, String endStation) throws IOException;
    List<DoubleWay> getTransferWay(String startStation, String endStation) throws IOException;
    List<ResultWay> getWaysByWeight(String start, String end, Weight weight);
}
