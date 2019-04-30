package com.example.springtest.service.impl;

import com.example.springtest.dao.CityMapper;
import com.example.springtest.dao.DomesticFlightMapper;
import com.example.springtest.domain.DomesticFlight;
import com.example.springtest.domain.DoubleFight;
import com.example.springtest.domain.Oneway;
import com.example.springtest.service.FlightService;
import com.example.springtest.utils.helpclass.CityNotExisitException;
import com.example.springtest.utils.helpclass.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    @Autowired
    private DomesticFlightMapper domesticFlightMapper;
    @Autowired
    private Util util;
    @Autowired
    private CityMapper cityMapper;
    @Override
    public List<DomesticFlight> getFlightInfoByAirportName(String depAirportName, String arrAirportName) {
        return domesticFlightMapper.selectDomesticFLightByAirportName(depAirportName,arrAirportName);
    }

    @Override
    public List<DomesticFlight> getFlightInfoByCitypotName(String depCityName, String arrCityName) {
        return domesticFlightMapper.selectDomesticFlightByCityName(depCityName,arrCityName);
    }

    /**
     * 目前只能根据两个城市之间进行查询，如果直接输入机场的名字会失败，或者输入火车站会失败，应该先在输入的时候转成城市的名字
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<DoubleFight> getDoubleFlight(String start, String end) {
        //得到中转站
        try {
            List<String> allTransferStations = util.getTransfeCitys(start,end,false,false);
            List<DoubleFight> doubleFights =  domesticFlightMapper.selectDoubleFlightway(start,allTransferStations,end);
            return doubleFights;
        }catch (CityNotExisitException e){

        }
        return null;
    }

    /**
     * 0:出发时间
     * 1：总时间
     * 2：价格
     * @param flights
     * @param order
     * @return
     */
    @Override
    public List<DomesticFlight> orderFlight(List<DomesticFlight> flights, int order) {
        if(order==1){
            Collections.sort(flights, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight o1, DomesticFlight o2) {
                    return Double.compare(o1.getDurations(),o2.getDurations());
                }
            });
        }else if(order==2){
            //价格排序:
            Collections.sort(flights, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight o1, DomesticFlight o2) {
                    return Double.compare(o1.getPrice(),o2.getPrice());
                }
            });
        }else {
            //出发时间
            Collections.sort(flights, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight o1, DomesticFlight o2) {
                    return Long.compare(o1.getDepDate().getTime(),o2.getDepDate().getTime());
                }
            });
        }
        return flights;
    }


}
