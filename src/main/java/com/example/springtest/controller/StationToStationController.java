package com.example.springtest.controller;

import com.example.springtest.domain.DomesticFlight;
import com.example.springtest.domain.Oneway;
import com.example.springtest.domain.ResultWay;
import com.example.springtest.domain.Way;
import com.example.springtest.service.FlightService;
import com.example.springtest.service.TrainAndFlightService;
import com.example.springtest.service.TrainService;
import com.example.springtest.utils.helpclass.Weight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class StationToStationController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private TrainAndFlightService trainAndFlightService;
    @Autowired
    private FlightService flightService;

    /**
     * type:0->商务
     * 1：-》个人旅游
     * 2：-》家庭游
     *
     * 0:出发时间
     *      * 1:总时间
     *      * 2:总价格
     *      * 3:中转时间
     *      * 4:默认
     * @param from
     * @param to
     * @return
     */
    @RequestMapping("/getComprehensiveList")
    private List<ResultWay> getComprehensiveList(String from, String to, int order, int type) {
        Weight weight = new Weight();
        switch (type) {
            case 0: {
                weight.setTransferTime(2);
                weight.setTime(3);
                weight.setPrice(1);
                weight.setComfort(4);
                break;
            }
            case 1: {
                weight.setTransferTime(1);
                weight.setTime(3);
                weight.setPrice(4);
                weight.setComfort(2);
                break;
            }
            case 2: {
                weight.setTransferTime(3);
                weight.setTime(3);
                weight.setPrice(1);
                weight.setComfort(3);
                break;
            }
        }

        return trainAndFlightService.orderWeightWay(trainAndFlightService.getWaysByWeight(from.trim(), to.trim(), weight), order);
    }

    /**
     * oder:
     * 0:出发时间排序;
     * 1:旅行耗时时间；
     * 2:价格排序
     *
     * @param from
     * @param to
     * @param order
     * @return
     */
    @RequestMapping("/getDirectTrain")
    private List<Oneway> getDirectTrain(String from, String to, int order) {
        System.out.println(order);
        return trainService.orderDirectWay(trainService.getDirectOneWay(from.trim(), to.trim()), order);
    }

    @RequestMapping("/getDirectFlight")
    private List<DomesticFlight> getFlight(String from, String to, int order) {
        return flightService.orderFlight(flightService.getFlightInfoByCitypotName(from.trim(), to.trim()),order);
    }

    /**
     * 0:默认
     *      * 1:总时间
     *      * 2:总价格
     *      * 3:中转时间
     *      * 4:时间
     * @param from
     * @param to
     * @return
     */
    @RequestMapping("/getTransfer")
    private List<ResultWay> getTransfer(String from, String to,int order) {
        List<ResultWay> resultWays = null;
        Weight weight = new Weight();
        weight.setPrice(2);
        weight.setTime(2);
        weight.setTransferTime(2);
        weight.setComfort(2);
        resultWays = trainAndFlightService.getAllTransferWay(from.trim(),to.trim());
        return trainAndFlightService.orderWeightWay(resultWays,order);

    }
}
