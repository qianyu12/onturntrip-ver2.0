package com.example.springtest;

import com.example.springtest.domain.DoubleWay;
import com.example.springtest.domain.Oneway;
import com.example.springtest.domain.ResultWay;
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
        String start ="南京南";
        String end = "成都东";
        try {
            List<DoubleWay> list = trainService.getTransferWayByStation(start,end);
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
        weight.setComfort(2);
        weight.setPrice(2);
        weight.setTime(5);
        weight.setTransferTime(1);
        List<ResultWay> resultWays = trainService.getWaysByWeight("南京","成都",weight);
        for(ResultWay r:resultWays){
            Object o = r.getWay();
            if(o instanceof Oneway){
                System.out.println(((Oneway) o).getTrainNo());
            }else if(o instanceof  DoubleWay){
                System.out.println(o.toString());
            }
            System.out.println(r.getResultScore());
        }
    }

}
