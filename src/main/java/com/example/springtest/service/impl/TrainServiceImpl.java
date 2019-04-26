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

    @Override
    public List<Oneway> getDirectOneWayByStationName(String startStation, String endStation) {
        return onewayMapper.getDirectOneWayByStationNames(startStation,endStation);
    }

    @Override
    public List<Oneway> getDirectOneWay(String start, String end) {
        List<Oneway> onewayList = new LinkedList<>();
        if(isStation(start)&&isStation(end)){
            return getDirectOneWayByStationName(start.substring(0,start.length()-1),end.substring(0,end.length()-1));
        }else if (!isStation(start)&&isStation(end)){
           return onewayMapper.getDirecOneWay(start,end.substring(0,end.length()-1));
        }else if(isStation(start) && !isStation(end)){
           return onewayMapper.getDirecOneWay(start.substring(0,start.length()-1),end);
        }else if (!isStation(start)&&!isStation(end)){
            //两个都不是车站名，嵌套查询
           return onewayMapper.getDirecOneWay(start,end);
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
//        for(String transferCity:allTransferStations){
//            List<Station> stations = stationMapper.selectStationByCity(transferCity);
//            for(Station s:stations){
//                List<DoubleWay> doubleWayList = onewayMapper.getDoubleWayByStationName(startStation,s.getTsZh(),endStation);
//                List<DoubleWay> temp = new LinkedList<>();
//                for(DoubleWay d:doubleWayList){
//                    if(getTransferMinute(d.getFirstTrip(),d.getSecondTrip())<=90&&getTransferMinute(d.getFirstTrip(),d.getSecondTrip())>=30){
//                        temp.add(d);
//                    }
//                }
//                doubleWayList = temp;
//                results.addAll(doubleWayList);
//            }
//        }
        for(String transferCity:allTransferStations){
            List<DoubleWay> doubleWayList = onewayMapper.getDoubleWay(startStation,transferCity,endStation);
            List<DoubleWay> temp = new LinkedList<>();
                for(DoubleWay d:doubleWayList){
                    if(getTransferMinute(d.getFirstTrip(),d.getSecondTrip())<=90&&getTransferMinute(d.getFirstTrip(),d.getSecondTrip())>=30){
                        temp.add(d);
                    }
                }
                doubleWayList = temp;
                results.addAll(doubleWayList);
        }
        return results;
    }

    //同站换乘
    @Override
    public List<DoubleWay> getTransferWay(String start, String end) throws IOException {
        List<DoubleWay> doubleWays = new LinkedList<>();
        if(isStation(start)&&isStation(end)){
            return getTransferWayByStation(start.substring(0,start.length()-1),end.substring(0,end.length()-1));
        }else if (!isStation(start)&&isStation(end)){
                doubleWays.addAll(getTransferWayByStation(start,end.substring(0,end.length()-1)));
        }else if(isStation(start) && !isStation(end)){
                doubleWays.addAll(getTransferWayByStation(start.substring(0,start.length()-1),end));
        }else if (!isStation(start)&&!isStation(end)){
                doubleWays.addAll(getTransferWayByStation(start,end));
        }
        return doubleWays;
    }

    //对各个纬度的数据进行归一化处理，然后用比例算出结果
    @Override
    public List<ResultWay> getWaysByWeight(String start, String end, Weight weight) {
        List<ResultWay> resultList = new LinkedList<>();
        List<Oneway> onewayList = getDirectOneWay(start,end);
        boolean isBusiness =false;
        try{
            List<DoubleWay> doubleWayList = getTransferWay(start,end);
            //归一化用for oneway
            double maxPriceForOneWay = Double.MIN_VALUE;
            double minPriceForOneWay = Double.MAX_VALUE;
            long maxTimeForOneWay = Long.MIN_VALUE;
            long minTimeForOneWay = Long.MAX_VALUE;
           for(Oneway o:onewayList){
               resultList.add(new ResultWay(o));
               //价格最大最小值;
               if(o.getPrice(isBusiness)>maxPriceForOneWay){
                   maxPriceForOneWay = o.getPrice(isBusiness);
               }else if(o.getPrice(isBusiness)<minPriceForOneWay){
                   minPriceForOneWay= o.getPrice(isBusiness);
               }
               //时间最大最小值：用min表示
               String[] hourAndMinute = o.getDuration().split(":");
               Long minutes = Long.valueOf(hourAndMinute[0])*60+Long.valueOf(hourAndMinute[1]);
               maxTimeForOneWay = minutes>maxTimeForOneWay?minutes:maxTimeForOneWay;
               minTimeForOneWay = minutes<minTimeForOneWay?minutes:minTimeForOneWay;
           }
           //归一化用for DoubleWay
            double maxPriceForDoubleWay = Double.MIN_VALUE;
            double minPriceForDoubleWay = Double.MAX_VALUE;
            long maxTimeForDoubleWay = Long.MIN_VALUE;
            long minTimeForDoubleWay = Long.MAX_VALUE;
            long minTransferTime = Long.MAX_VALUE;
            long maxTransferTime = Long.MIN_VALUE;
           for(DoubleWay d:doubleWayList){
               resultList.add(new ResultWay(d));
               //totalPrice
               double totalPirce = d.getFirstTrip().getPrice(isBusiness)+d.getSecondTrip().getPrice(isBusiness);
               maxPriceForDoubleWay = totalPirce>maxPriceForDoubleWay?totalPirce:maxPriceForDoubleWay;
               minPriceForDoubleWay = totalPirce<minPriceForDoubleWay?totalPirce:minPriceForDoubleWay;
               //total时间：
               Long totalMinutes = calculateMinutes(d.getFirstTrip())+calculateMinutes(d.getSecondTrip());
               maxTimeForDoubleWay = totalMinutes>maxTimeForDoubleWay?totalMinutes:maxTimeForDoubleWay;
               minTimeForDoubleWay = totalMinutes<minTimeForDoubleWay?totalMinutes:minTimeForDoubleWay;
               //中转时间：
               Long transfertime = getTransferMinute(d.getFirstTrip(),d.getSecondTrip());
               maxTransferTime = transfertime>maxTransferTime?transfertime:maxTransferTime;
               minTransferTime = transfertime<minTransferTime?transfertime:minTransferTime;
           }
           double maxPrice = maxPriceForDoubleWay>maxPriceForOneWay?maxPriceForDoubleWay:maxPriceForOneWay;
           double minPrice = minPriceForDoubleWay<minPriceForOneWay?minPriceForDoubleWay:minPriceForOneWay;
           long maxTime = maxTimeForDoubleWay>maxTimeForOneWay?maxTimeForDoubleWay:maxTimeForOneWay;
           long minTime = minTimeForDoubleWay<minTimeForOneWay?minTimeForDoubleWay:minTimeForOneWay;
           minTransferTime = 0;
           //计算每个路程的优先值
          for(ResultWay resultWay:resultList){
              Object o =  resultWay.getWay();
              if(o instanceof Oneway){
                  double priceScore = MathUtil.Normalization(((Oneway) o).getPrice(isBusiness),minPrice,maxPrice)*weight.getPriceScale();
                  double timeScore = MathUtil.Normalization(calculateMinutes((Oneway)o),minTime,maxTime)*weight.getTimeScale();
                  char  trainNoChar = ((Oneway) o).getTrainNo().toUpperCase().charAt(0);
                  double comforScore = (trainNoChar=='G'?0:trainNoChar=='D'?0.25:trainNoChar=='Z'?0.625:trainNoChar=='K'?0.875:1)*weight.getComftScale();
                  resultWay.setResultScore(priceScore+timeScore+comforScore);
              }else if (o instanceof DoubleWay){
                  Oneway firstTrip = ((DoubleWay) o).getFirstTrip();
                  Oneway secondTrap = ((DoubleWay) o).getSecondTrip();
                  double priceScore = MathUtil.Normalization(firstTrip.getPrice(isBusiness)+secondTrap.getPrice(isBusiness),minPrice,maxPrice)*weight.getPriceScale();
                  double timeScore = MathUtil.Normalization(calculateMinutes(firstTrip)+calculateMinutes(secondTrap),minTime,maxTime)*weight.getTimeScale();
                  char trainNoCharForFistTrip = firstTrip.getTrainNo().toUpperCase().charAt(0);
                  char trianNoCharForSecondTrip = secondTrap.getTrainNo().toUpperCase().charAt(0);
                  int trainWeight = juegeWeightByNo(trainNoCharForFistTrip)+juegeWeightByNo(trianNoCharForSecondTrip);
                  double comforScore = trainWeight/80.0*weight.getComftScale();
                  double transferScore = MathUtil.Normalization(getTransferMinute(firstTrip,secondTrap),minTransferTime,maxTransferTime)*weight.getTranferTimeScale();
                  System.out.println(priceScore+"-"+timeScore+"-"+comforScore+"-"+transferScore);
                  resultWay.setResultScore(priceScore+timeScore+comforScore+transferScore);
              }else{
                  throw new Exception();
              }
          }
          //按照比例将进行排序,待完成
            Collections.sort(resultList, new Comparator<ResultWay>() {
                @Override
                public int compare(ResultWay o1, ResultWay o2) {
                    return o1.getResultScore()<o2.getResultScore()?-1:o1.getResultScore()>o2.getResultScore()?1:0;
                }
            });
        }catch (Exception e){
            System.out.println("something error when query transfering");
        }
        return resultList;
    }


    public String getStationOrientation(String station){
        return provinceMapper.getOrientationByStationName(station).getOreintation();
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

    private Long calculateMinutes(Oneway o){
        String[] result = o.getDuration().split(":");
        return Long.valueOf(result[0])*60+Long.valueOf(result[1]);
    }

    private Long getTransferMinute(Oneway firstTrip,Oneway secondTrip){
        String firstArriveTime = firstTrip.getArrTime();
        String secondDepTime = secondTrip.getDepTime();
        Long firstHour = Long.valueOf(firstArriveTime.split(":")[0]);
        Long firstMinuete = Long.valueOf(firstArriveTime.split(":")[1]);
        Long secondHour = Long.valueOf(secondDepTime.split(":")[0]);
        Long secondMinuete = Long.valueOf(secondDepTime.split(":")[1]);
        return secondHour*60+secondMinuete-(firstHour*60+secondMinuete);
    }
}
