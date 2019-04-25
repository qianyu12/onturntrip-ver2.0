package com.example.springtest.dao;

import com.example.springtest.domain.Oneway;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<Oneway> getDirectOneWayByStationName(String startStation, String endStation);
}
