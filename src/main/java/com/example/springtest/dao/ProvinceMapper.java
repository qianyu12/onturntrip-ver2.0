package com.example.springtest.dao;

import com.example.springtest.domain.Province;
import com.example.springtest.utils.MyMapper;

public interface ProvinceMapper extends MyMapper<Province> {
    Province getOrientationByStationName(String stationName);
    Province getOrientationByCityName(String cityName);
}