package com.example.springtest.service;

import com.example.springtest.domain.FlightToTrain;
import com.example.springtest.domain.ResultWay;
import com.example.springtest.domain.TrainToFlight;
import com.example.springtest.domain.Way;
import com.example.springtest.utils.helpclass.Weight;

import java.io.IOException;
import java.util.List;

public interface TrainAndFlightService {
    //飞机->火车：去掉起始点的省
    List<FlightToTrain> getFlightToTrain(String startCity, String endCity);
    //火车->飞机：去掉末尾点的省
    List<TrainToFlight> getTrainToFlight(String startCity, String endCity) ;

    //综合结果：
    List<ResultWay> getWaysByWeight(String start, String end, Weight weight) ;

    //所有中转:
    List<ResultWay> getAllTransferWay(String start,String end);

    //排序
    List<ResultWay> orderWeightWay(List<ResultWay> resultWayList,int order);

    List<Way> testWays(String start,String end)  throws IOException;

}
