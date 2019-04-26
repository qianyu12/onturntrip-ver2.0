//package com.example.springtest.controller;
//
//import com.example.springtest.service.TrainService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@EnableAutoConfiguration
//public class StationToStationController {
//    @Autowired
//    private TrainService trainService;
//    @RequestMapping("/testTrain")
//    public List<Oneway> testTrain(){
//        return trainService.getDirectOneWayByStationName("南京南站","成都东站");
//    }
//}
