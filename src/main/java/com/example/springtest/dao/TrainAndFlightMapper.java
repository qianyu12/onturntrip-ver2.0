package com.example.springtest.dao;

import com.example.springtest.domain.DomesticFlight;
import com.example.springtest.domain.Oneway;

import java.util.List;

public interface TrainAndFlightMapper {

    List<DomesticFlight> slectDomesticFlightByManyCityName(List<String>start, List<String> end);
    List<Oneway> selectTrainByManyCityName(List<String>start,List<String> end);
}
