package com.example.springtest.dao;

import com.example.springtest.domain.City;
import com.example.springtest.domain.Province;
import com.example.springtest.domain.Station;
import com.example.springtest.utils.MyMapper;

public interface CityMapper extends MyMapper<City> {
    City getCityByStationName(String cityName);
}