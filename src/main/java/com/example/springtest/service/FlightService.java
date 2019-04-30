package com.example.springtest.service;

import com.example.springtest.domain.DomesticFlight;
import com.example.springtest.domain.DoubleFight;
import java.util.List;

public interface FlightService {
    List<DomesticFlight> getFlightInfoByAirportName(String depAirportName,String arrAirportName);
    List<DomesticFlight> getFlightInfoByCitypotName(String depCityName,String arrCityName);
    //得到飞机->飞机中转:得到飞机->火车中转：达到火车->飞机中转
    //飞机->飞机：去除掉包含省的中间城市
    List<DoubleFight> getDoubleFlight(String startCity,String endCity);

    //排序
    List<DomesticFlight> orderFlight(List<DomesticFlight> flights,int order);
}
