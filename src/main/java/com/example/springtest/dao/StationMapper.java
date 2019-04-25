package com.example.springtest.dao;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.example.springtest.domain.DoubleWay;
import com.example.springtest.domain.Station;
import com.example.springtest.utils.MyMapper;

import java.util.List;

public interface StationMapper extends MyMapper<Station> {
    List<Station> getStationsByCity(String cityName);
}