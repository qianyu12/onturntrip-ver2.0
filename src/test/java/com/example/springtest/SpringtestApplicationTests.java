package com.example.springtest;

import com.example.springtest.domain.*;
import com.example.springtest.service.FlightService;
import com.example.springtest.service.TrainAndFlightService;
import com.example.springtest.service.TrainService;
import com.example.springtest.service.UserSerive;
import com.example.springtest.utils.helpclass.Scope;
import com.example.springtest.utils.helpclass.Weight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringtestApplicationTests {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private TrainService trainService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private TrainAndFlightService trainAndFlightServiced;
    @Before
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hello")).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void scopeTest(){
        List<Scope> scopes = Scope.returnScopes();
        for(Scope scope:scopes){
            System.out.println(scope.toString());
        }
    }
    @Test
    public void TestGetDirectOneWayByStationName(){
        String startStation = "南京南";
        String endStation = "西安北";
        List<Oneway> onewayList =trainService.getDirectOneWayByStationName(startStation,endStation);
        for(Oneway oneway:onewayList){
            System.out.println(oneway.toString());
        }
    }

    @Test
    public void TestGetDirectOneWay(){
        String start ="南京";
        String end = "成都";
        List<Oneway> list = trainService.getDirectOneWay(start,end);
        for(Oneway oneway:list){
            System.out.println(oneway.toString());
        }
    }

    @Test
    public void TestGetTransferWay(){
        String start ="南京南站";
        String end = "成都";
        try {
            List<DoubleWay> list = trainService.getTransferWay(start,end);
            for(DoubleWay d:list){
                System.out.println(d.toString());
            }
        }catch (Exception e){

        }

    }
    @Test
    public void TestGetTransferWay2(){
        String start ="南京";
        String end = "成都";
        try {
            List<DoubleWay> list = trainService.getTransferWay(start,end);
            for(DoubleWay d:list){
                System.out.println(d.toString());
            }
        }catch (Exception e){

        }

    }

    @Test
    public void TestScope(){

        System.out.println("result+:"+Scope.returnMiddleOrientation("SW","E"));
    }

    @Test
    public void TestWeight(){
        Weight weight = new Weight(2,4,5,1);
    }
    @Test
    public void testgetWaysByWeight(){
        Weight weight = new Weight();
        weight.setComfort(1);
        weight.setPrice(5);
        weight.setTime(3);
        weight.setTransferTime(1);
        List<ResultWay> resultWays = trainAndFlightServiced.getWaysByWeight("南京","成都",weight);
        for(ResultWay r:resultWays){
            System.out.println(r.toString());
        }
    }
    @Test
    public void testgetDoubleFlight(){
        List<DoubleFight> doubleFights = flightService.getDoubleFlight("北京","成都");
        for(DoubleFight d:doubleFights){
            System.out.println(d.getFirstTrip().getFlightNumber()+"->"+d.getSecondTrip().getFlightNumber()+":"+d.getSecondTrip().getDepDate()+d.getSecondTrip().getArrDate()+d.getFirstTrip().getArrDate()+d.getFirstTrip().getDepDate());
        }
    }
    @Test
    public void testgetFlightToTrain(){
        List<FlightToTrain> list =trainAndFlightServiced.getFlightToTrain("北京","成都");
        for(FlightToTrain ft:list){
            System.out.println(ft.getFirstTrip().getArrCityName()+"->"+ft.getSecondTrip().getFromStation().getCityName()+ft.getSecondTrip().getToStation().getCityName());
        }
    }

    @Test
    public void testGetTrainToFlight(){
        List<TrainToFlight> list =trainAndFlightServiced.getTrainToFlight("南京","成都");
        for(TrainToFlight ft:list){
            System.out.println(ft.getFirstTrip().getToStation().getCityName()+"->"+ft.getSecondTrip().getDepCityName());
        }
    }
}
