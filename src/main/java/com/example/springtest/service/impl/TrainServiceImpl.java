package com.example.springtest.service.impl;

import com.example.springtest.dao.*;
import com.example.springtest.domain.*;
import com.example.springtest.service.TrainService;
import com.example.springtest.utils.helpclass.CityNotExisitException;
import com.example.springtest.utils.helpclass.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private OnewayMapper onewayMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private Util util;

    @Override
    public List<Oneway> getDirectOneWayByStationName(String startStation, String endStation) {
        return onewayMapper.getDirectOneWayByStationNames(startStation, endStation);
    }

    @Override
    public List<Oneway> getDirectOneWay(String start, String end) {
        if (util.isStaion(start) && util.isStaion(end)) {
            return getDirectOneWayByStationName(start.substring(0, start.length() - 1), end.substring(0, end.length() - 1));
        } else if (!util.isStaion(start) && util.isStaion(end)) {
            return onewayMapper.getDirecOneWay(start, end.substring(0, end.length() - 1));
        } else if (util.isStaion(start) && !util.isStaion(end)) {
            return onewayMapper.getDirecOneWay(start.substring(0, start.length() - 1), end);
        } else if (!util.isStaion(start) && !util.isStaion(end)) {
            //两个都不是车站名
            return onewayMapper.getDirecOneWay(start,end);
        }else{
            return null;
        }
    }

    /**
     * 中转的具体实现，判断开始站和终点站的方位，根据方位范围选择合适的中转站，通过sql实现。
     *
     * @param startStation
     * @param endStation
     * @return
     */
    @Override
    public List<DoubleWay> getTransferWay(String startStation, String endStation) throws IOException {
        try{
            //得到中转站:
            List<String> allTransferStations = util.getTransfeCitys(startStation,endStation,true,true);
            System.out.println(allTransferStations.toString());
            List<DoubleWay> doubleWayList = onewayMapper.getDoubleWay(startStation, allTransferStations, endStation);
            List<DoubleWay> temp = new LinkedList<>();
            for (DoubleWay d : doubleWayList) {
                if (util.getTransferMinute(d.getFirstTrip(), d.getSecondTrip()) <= 90 && util.getTransferMinute(d.getFirstTrip(), d.getSecondTrip()) >= 30) {
                    temp.add(d);
                }
            }
            doubleWayList = temp;

            return doubleWayList;
        }catch (CityNotExisitException e){
            e.printStackTrace();
        }
       return  null;
    }

    /**
     * 0:出发时间排序;
     * 1:旅行耗时时间；
     * 2:价格排序
     * @param onewayList
     * @param order
     * @return
     */
    @Override
    public List<Oneway> orderDirectWay(List<Oneway> onewayList, int order) {
        //耗时时间
        if(order==1){
            Collections.sort(onewayList, new Comparator<Oneway>() {
                @Override
                public int compare(Oneway o1, Oneway o2) {
                    return o1.getDurations()<o2.getDurations()?-1:o1.getDurations()>o2.getDurations()?1:0;
                }
            });
        }else if(order==2){
          //价格排序:
            Collections.sort(onewayList, new Comparator<Oneway>() {
                @Override
                public int compare(Oneway o1, Oneway o2) {
                    return o1.getPrice()<o2.getPrice()?-1:o1.getPrice()>o2.getPrice()?1:0;
                }
            });
        }else {
            //出发时间
            Collections.sort(onewayList, new Comparator<Oneway>() {
                @Override
                public int compare(Oneway o1, Oneway o2) {
                    int dep1 = Integer.valueOf(o1.getDepTime().split(":")[0])*60+Integer.valueOf(o1.getDepTime().split(":")[1]);
                    int dep2 = Integer.valueOf(o2.getDepTime().split(":")[0])*60+Integer.valueOf(o2.getDepTime().split(":")[1]);
                    return dep1<dep2?-1:dep1>dep2?1:0;
                }
            });
        }
        return onewayList;
    }


}
