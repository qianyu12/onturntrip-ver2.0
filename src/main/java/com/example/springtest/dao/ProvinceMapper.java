package com.example.springtest.dao;

import com.example.springtest.domain.Province;
import com.example.springtest.utils.MyMapper;

public interface ProvinceMapper extends MyMapper<Province> {
    Province getOrientationByCityName(String cityName);
    Province getOrientationByStationName(String stationName);
}