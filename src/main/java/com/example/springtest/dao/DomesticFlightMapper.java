package com.example.springtest.dao;

import com.example.springtest.domain.DomesticFlight;
import com.example.springtest.domain.DoubleFight;
import com.example.springtest.utils.MyMapper;

import java.util.List;

public interface DomesticFlightMapper extends MyMapper<DomesticFlight> {
    List<DomesticFlight> selectDomesticFlightByCityName(String start,String end);
    List<DomesticFlight> selectDomesticFLightByAirportName(String start,String end);
    List<DoubleFight> selectDoubleFlightway(String start,List<String>middle, String end);
}