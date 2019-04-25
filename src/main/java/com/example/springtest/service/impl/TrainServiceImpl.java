package com.example.springtest.service.impl;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.example.springtest.dao.*;
import com.example.springtest.domain.*;
import com.example.springtest.service.TrainService;
import com.example.springtest.utils.MathUtil;
import com.example.springtest.utils.TransferStationInfo;
import com.example.springtest.utils.helpclass.Scope;
import com.example.springtest.utils.helpclass.Weight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private  CityMapper cityMapper;
    @Autowired
    private OnewayMapper onewayMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private TestMapper testMapper;
    @Override
    public List<Oneway> getDirectOneWayByStationName(String startStation, String endStation) {
//        return onewayMapper.getDirectOneWayByStationName(startStation.substring(0,startStation.length()-1),endStation.substring(0,endStation.length()-1));
        List<Oneway> onewayList =  testMapper.getDirectOneWayByStationName(startStation,endStation);
        return onewayList;
    }

    @Override
    public List<Oneway> getDirectOneWay(String start, String end) {
        List<Oneway> onewayList = new LinkedList<>();
        if(isStation(start)&&isStation(end)){
            return getDirectOneWayByStationName(start.substring(0,start.length()-1),end.substring(0,end.length()-1));
        }else if (!isStation(start)&&isStation(end)){
            //通过城市名字查询所有车站名字
            List<Station> startStations = stationMapper.getStationsByCity(start);
            //遍历所有的开始车站，到终点站
            for(Station s:startStations){
                onewayList.addAll(getDirectOneWayByStationName(s.getStationName(),end.substring(0,end.length()-1)));
            }
        }else if(isStation(start) && !isStation(end)){
            //通过城市名字查询所有车站名字
            List<Station> endStations = stationMapper.getStationsByCity(end);
            //遍历所有的到达车站，从开始站
            for(Station s:endStations){
                onewayList.addAll(getDirectOneWayByStationName(start.substring(0,start.length()-1),s.getStationName()));
            }
        }else if (!isStation(start)&&!isStation(end)){
            //两个都不是车站名，嵌套查询
            List<Station> startStations = stationMapper.getStationsByCity(start);
            List<Station> endStations = stationMapper.getStationsByCity(end);
            for(Station s1:startStations){
                for(Station s2:endStations){
                    onewayList.addAll(getDirectOneWayByStationName(s1.getStationName(),s2.getStationName()));
                }
            }
        }
        return onewayList;
    }

    /**
     * 中转的具体实现，判断开始站和终点站的方位，根据方位范围选择合适的中转站，通过sql实现。
     * @param startStation
     * @param endStation
     * @return
     */
    @Override
    public List<DoubleWay> getTransferWayByStation(String startStation, String endStation) throws IOException {
        //中转表
        TransferStationInfo transferStationInfo = TransferStationInfo.getInstance();
        Map<String,List<String>> transferStations = transferStationInfo.returnTransferStation();
        //结果
        List<DoubleWay> results = new LinkedList<>();
        //开始的站的方位
        String startOrientation = getStationOrientation(startStation);
        //结束的站的方位
        String endOrientation = getStationOrientation(endStation);
        //中间的方位：
        String middleOrientation = Scope.returnMiddleOrientation(startOrientation.trim(),endOrientation.trim());
        List<String> allTransferStations = new ArrayList<>();
        if(!middleOrientation.isEmpty()){
            String[] middleOrientations = middleOrientation.split("\\|");
            for(String s:middleOrientations){
                allTransferStations.addAll(transferStations.get(s));
            }
        }
        allTransferStations.addAll(transferStations.get(startOrientation.trim()));
        allTransferStations.addAll(transferStations.get(endOrientation.trim()));
        System.out.println(allTransferStations.toString());
        for(String transferCity:allTransferStations){
            List<Station> stations = stationMapper.getStationsByCity(transferCity);
            for(Station s:stations){
                results.addAll(onewayMapper.getDoubleWayByStationName(startStation,s.getStationName(),endStation));
            }
        }
        return results;
    }

    @Override
    public List<DoubleWay> getTransferWay(String start, String end) throws IOException {
        List<DoubleWay> doubleWays = new LinkedList<>();
        if(isStation(start)&&isStation(end)){
            return getTransferWayByStation(start.substring(0,start.length()-1),end.substring(0,end.length()-1));
        }else if (!isStation(start)&&isStation(end)){
            //通过城市名字查询所有车站名字
            List<Station> startStations = stationMapper.getStationsByCity(start);
            //遍历所有的开始车站，到终点站
            for(Station s:startStations){
                doubleWays.addAll(getTransferWayByStation(s.getStationName(),end.substring(0,end.length()-1)));
            }
        }else if(isStation(start) && !isStation(end)){
            //通过城市名字查询所有车站名字
            List<Station> endStations = stationMapper.getStationsByCity(end);
            //遍历所有的到达车站，从开始站
            for(Station s:endStations){
                doubleWays.addAll(getTransferWayByStation(start.substring(0,start.length()-1),s.getStationName()));
            }
        }else if (!isStation(start)&&!isStation(end)){
            //两个都不是车站名，嵌套查询
            List<Station> startStations = stationMapper.getStationsByCity(start);
            List<Station> endStations = stationMapper.getStationsByCity(end);
            for(Station s1:startStations){
                for(Station s2:endStations){
                    doubleWays.addAll(getTransferWayByStation(s1.getStationName(),s2.getStationName()));
                }
            }
        }
        return doubleWays;
    }

    //对各个纬度的数据进行归一化处理，然后用比例算出结果
    @Override
    public List<ResultWay> getWaysByWeight(String start, String end, Weight weight) {
        List<ResultWay> resultList = new LinkedList<>();
        List<Oneway> onewayList = getDirectOneWay(start,end);
        try{
            List<DoubleWay> doubleWayList = getTransferWay(start,end);
            //归一化用for oneway
            BigDecimal maxPriceForOneWay = BigDecimal.valueOf(Double.MIN_VALUE);
            BigDecimal minPriceForOneWay = BigDecimal.valueOf(Double.MAX_VALUE);
            long maxTimeForOneWay = Long.MIN_VALUE ;
            long minTimeForOneWay = Long.MAX_VALUE;
           for(Oneway o:onewayList){
               resultList.add(new ResultWay(o));
               //价格最大最小值;
               if(o.getPrice().compareTo(maxPriceForOneWay)==1){
                   maxPriceForOneWay = o.getPrice();
               }else if(o.getPrice().compareTo(minPriceForOneWay)==-1){
                   minPriceForOneWay= o.getPrice();
               }
               //时间最大最小值：用min表示
               Long minutes = calculateMinutes(o.getEndTime(),o.getStartTime());
               maxTimeForOneWay = minutes>maxTimeForOneWay?minutes:maxTimeForOneWay;
               minTimeForOneWay = minutes<minTimeForOneWay?minutes:minTimeForOneWay;
           }
           //归一化用for DoubleWay
            BigDecimal maxPriceForDoubleWay = BigDecimal.valueOf(Double.MIN_VALUE);
            BigDecimal minPriceForDoubleWay = BigDecimal.valueOf(Double.MAX_VALUE);
            long maxTimeForDoubleWay = Long.MIN_VALUE;
            long minTimeForDoubleWay = Long.MAX_VALUE;
            long minTransferTime = Long.MAX_VALUE;
            long maxTransferTime = Long.MIN_VALUE;
           for(DoubleWay d:doubleWayList){
               resultList.add(new ResultWay(d));
               //totalPrice
               BigDecimal totalPirce = d.getFirstTrip().getPrice().add(d.getSecondTrip().getPrice());
               maxPriceForDoubleWay = totalPirce.compareTo(maxPriceForDoubleWay)==1?totalPirce:maxPriceForDoubleWay;
               minPriceForDoubleWay = totalPirce.compareTo(minPriceForDoubleWay)==-1?totalPirce:minPriceForDoubleWay;
               //total时间：
               Long totalMinutes = calculateMinutes(d.getFirstTrip().getEndTime(),d.getFirstTrip().getStartTime())+calculateMinutes(d.getSecondTrip().getEndTime(),d.getSecondTrip().getStartTime());
               maxTimeForDoubleWay = totalMinutes>maxTimeForDoubleWay?totalMinutes:maxTimeForDoubleWay;
               minTimeForDoubleWay = totalMinutes<minTimeForDoubleWay?totalMinutes:minTimeForDoubleWay;
               //中转时间：
               Long transfertime = calculateMinutes(d.getSecondTrip().getStartTime(),d.getFirstTrip().getEndTime());
               maxTransferTime = transfertime>maxTransferTime?transfertime:maxTransferTime;
               minTransferTime = transfertime<minTransferTime?transfertime:minTransferTime;
           }
           BigDecimal maxPrice = maxPriceForDoubleWay.compareTo(maxPriceForOneWay)==1?maxPriceForDoubleWay:maxPriceForOneWay;
           BigDecimal minPrice = minPriceForDoubleWay.compareTo(minPriceForOneWay)==-1?minPriceForDoubleWay:minPriceForOneWay;
           long maxTime = maxTimeForDoubleWay>maxTimeForOneWay?maxTimeForDoubleWay:maxTimeForOneWay;
           long minTime = minTimeForDoubleWay<minTimeForOneWay?minTimeForDoubleWay:minTimeForOneWay;
           minTransferTime = 0;
           //计算每个路程的优先值
          for(ResultWay resultWay:resultList){
              Object o =  resultWay.getWay();
              if(o instanceof Oneway){
                  double priceScore = MathUtil.Normalization(((Oneway) o).getPrice().doubleValue(),minPrice.doubleValue(),maxPrice.doubleValue())*weight.getPriceScale();
                  double timeScore = MathUtil.Normalization(calculateMinutes(((Oneway) o).getEndTime(),((Oneway) o).getStartTime()),minTime,maxTime)*weight.getTimeScale();
                  char  trainNoChar = ((Oneway) o).getTrainNo().toUpperCase().charAt(0);
                  double comforScore = (trainNoChar=='G'?0:trainNoChar=='D'?0.25:trainNoChar=='Z'?0.625:trainNoChar=='K'?0.875:1)*weight.getComftScale();
                  resultWay.setResultScore(priceScore+timeScore+comforScore);
              }else if (o instanceof DoubleWay){
                  Oneway firstTrip = ((DoubleWay) o).getFirstTrip();
                  Oneway secondTrap = ((DoubleWay) o).getSecondTrip();
                  double priceScore = MathUtil.Normalization(firstTrip.getPrice().add(secondTrap.getPrice()).doubleValue(),minPrice.doubleValue(),maxPrice.doubleValue())*weight.getPriceScale();
                  double timeScore = MathUtil.Normalization(calculateMinutes(firstTrip.getEndTime(),firstTrip.getEndTime())+calculateMinutes(secondTrap.getEndTime(),secondTrap.getStartTime()),minTime,maxTime)*weight.getTimeScale();
                  char trainNoCharForFistTrip = firstTrip.getTrainNo().toUpperCase().charAt(0);
                  char trianNoCharForSecondTrip = secondTrap.getTrainNo().toUpperCase().charAt(0);
                  int trainWeight = juegeWeightByNo(trainNoCharForFistTrip)+juegeWeightByNo(trianNoCharForSecondTrip);
                  double comforScore = trainWeight/80.0*weight.getComftScale();
                  double transferScore = MathUtil.Normalization(calculateMinutes(secondTrap.getStartTime(),firstTrip.getEndTime()),minTransferTime,maxTransferTime)*weight.getTranferTimeScale();
                  System.out.println(priceScore+"-"+timeScore+"-"+comforScore+"-"+transferScore);
                  resultWay.setResultScore(priceScore+timeScore+comforScore+transferScore);
              }else{
                  throw new Exception();
              }
          }
          //按照比例将进行排序,待完成
        }catch (Exception e){
            System.out.println("something error when query transfering");
        }
        return resultList;
    }


    public String getStationOrientation(String station){
        return provinceMapper.getOrientationByStationName(station).getOriention();
    }

    private boolean isStation(String name){
        if(name.endsWith("站")){
            return true;
        }else {
            return false;
        }
    }

    private int juegeWeightByNo(char no){
        switch (no){
            case 'G': return 0;
            case 'D': return 10;
            case 'Z': return 25;
            case 'K':return 35;
            default:return 40;
        }
    }

    private Long calculateMinutes(Date d1,Date d2){
        return (d1.getTime()-d2.getTime())/(1000*60);
    }
}
