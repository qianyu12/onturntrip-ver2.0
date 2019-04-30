package com.example.springtest.dao;

import com.example.springtest.domain.Station;
import com.example.springtest.utils.MyMapper;

import java.util.List;

public interface StationMapper extends MyMapper<Station> {
    List<Station> selectStationByCity(String cityName);
    List<Station> selectStationByTsZh(String tsTlc);
}