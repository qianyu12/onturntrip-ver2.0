package com.example.springtest.utils.helpclass;

import com.example.springtest.dao.ProvinceMapper;
import com.example.springtest.domain.*;
import com.example.springtest.utils.TransferStationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Util {

    @Autowired
    private ProvinceMapper provinceMapper;

    private static Util util;

    @PostConstruct
    public void init(){
        util = this;
        util.provinceMapper = this.provinceMapper;
    }

    public boolean isStaion(String name) {
        if (name.endsWith("站")) {
            return true;
        } else {
            return false;
        }
    }

    public String getOrientation(String position) throws CityNotExisitException {
        if (util.isStaion(position)) {
            return provinceMapper.getOrientationByStationName(position.substring(0,position.length()-1)).getOreintation();
        } else {
            System.out.println(position);
            return provinceMapper.getOrientationByCityName(position).getOreintation();
        }
    }

    public List<String> getTransfeCitys(String start, String end, boolean containStart, boolean containEnd) throws CityNotExisitException{
        //中转表
        TransferStationInfo transferStationInfo = TransferStationInfo.getInstance();
        Map<String, List<String>> transferStations = transferStationInfo.returnTransferStation();
        //开始地点的方位
        System.out.println(start);
        String startOrientation = getOrientation(start);
        //结束地点的方位
        String endOrientation = getOrientation(end);
        //中间的方位：
        String middleOrientation = Scope.returnMiddleOrientation(startOrientation.trim(), endOrientation.trim());
        List<String> allTransferStations = new ArrayList<>();
        if (!middleOrientation.isEmpty()) {
            String[] middleOrientations = middleOrientation.split("\\|");
            for (String s : middleOrientations) {
                allTransferStations.addAll(transferStations.get(s));
            }
        }
        if (containStart) {
            allTransferStations.addAll(transferStations.get(startOrientation.trim()));
        }
        if (containEnd) {
            allTransferStations.addAll(transferStations.get(endOrientation.trim()));
        }
        return allTransferStations;
    }

    public Long getMinuteOfFlightAndTrainDate(Oneway train, DomesticFlight flight, boolean flightToTrain) {
        String trainDepTime = train.getDepTime();
        double duration = train.getDurations();
        Date date = train.getDate();

        Date startDateTrain;
        Date arrDateTrain;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR, Integer.valueOf(trainDepTime.split(":")[0]));
        calendar.set(GregorianCalendar.MINUTE, Integer.valueOf(trainDepTime.split(":")[1]));
        startDateTrain = calendar.getTime();
        calendar.add(GregorianCalendar.MINUTE, (int)duration);
        arrDateTrain = calendar.getTime();

        //飞机
        Date flightDepTime = flight.getDepDate();
        Date flightArrTime = flight.getArrDate();
        //如果是飞机转火车的话：
        if (flightToTrain) {
            return calculteMinutes(flightArrTime, startDateTrain);
        } else {
            //火车转飞机：
            return calculteMinutes(arrDateTrain, flightDepTime);
        }
    }

    public Long calculteMinutes(Date fitst, Date second) {
        return (second.getTime() - fitst.getTime()) / (1000 * 60);
    }

    private Date getDepTimeByOneway(Oneway oneway){
        Date date = oneway.getDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        System.out.println(oneway.getDepTime());
        calendar.set(GregorianCalendar.HOUR,Integer.valueOf(oneway.getDepTime().split(":")[0]));
        calendar.set(GregorianCalendar.MINUTE,Integer.valueOf(oneway.getDepTime().split(":")[1]));
        date = calendar.getTime();
        System.out.println(date);
        return date;
    }

    public Long getTransferMinute(Oneway firstTrip, Oneway secondTrip) {
        String firstArriveTime = firstTrip.getArrTime();
        String secondDepTime = secondTrip.getDepTime();
        Date firstTripDate = firstTrip.getDate();
        Date secondTripDate = secondTrip.getDate();
        Calendar firstTripCalender = new GregorianCalendar();
        firstTripCalender.setTime(firstTripDate);
        firstTripCalender.set(GregorianCalendar.HOUR,Integer.valueOf(firstArriveTime.split(":")[0]));
        firstTripCalender.set(GregorianCalendar.MINUTE,Integer.valueOf(firstArriveTime.split(":")[1]));
        firstTripDate = firstTripCalender.getTime();
        Calendar secondTripCalender = new GregorianCalendar();
        secondTripCalender.setTime(secondTripDate);
        secondTripCalender.set(GregorianCalendar.HOUR,Integer.valueOf(secondDepTime.split(":")[0]));
        secondTripCalender.set(GregorianCalendar.MINUTE,Integer.valueOf(secondDepTime.split(":")[1]));
        secondTripDate = secondTripCalender.getTime();
        return (secondTripDate.getTime()-firstTripDate.getTime())/(1000*60);
    }

    public List<ResultWay> constructReusltWayByOneWay(List<Oneway> ways) {
        List<ResultWay> resultWays = new LinkedList<>();
        for (Oneway oneway : ways) {
            ResultWay resultWay = new ResultWay(oneway);
            double price = oneway.getPrice();
            double minutes = oneway.getDurations();
            int comfotDegree = (int)oneway.getComfort();
            Date date = getDepTimeByOneway(oneway);
            resultWay.setTotalTime(minutes);
            resultWay.setComfortDegree(comfotDegree);
            resultWay.setTransferTime(0);
            resultWay.setTotalPrice(price);
            resultWay.setDepartTime(date);
            System.out.println(resultWay.getDepartTime());
            resultWays.add(resultWay);
            //设置最大最小值，方便后面使用
            setMinAndMaxValue(price, minutes, 0, comfotDegree);
        }
        return resultWays;
    }

    public List<ResultWay> constructReusltWayByDoubleWay(List<DoubleWay> ways) {
        List<ResultWay> resultWays = new LinkedList<>();
        for (DoubleWay doubleWay : ways) {
            ResultWay resultWay = new ResultWay(doubleWay);
            double price = doubleWay.getPrice();
            double minutes = doubleWay.getDurations();
            int comfotDegree = (int)doubleWay.getComfort();
            double transfer = doubleWay.getTranferTime();
            Date date = getDepTimeByOneway(doubleWay.getFirstTrip());
            resultWay.setTotalTime(minutes);
            resultWay.setTransferTime(transfer);
            resultWay.setComfortDegree(comfotDegree);
            resultWay.setTotalPrice(price);
            resultWay.setDepartTime(date);
            resultWays.add(resultWay);
            setMinAndMaxValue(price,minutes,transfer,comfotDegree);
        }
        return resultWays;
    }

    public List<ResultWay> constructReusltWayByFlight(List<DomesticFlight> ways) {
        List<ResultWay> resultWays = new LinkedList<>();
        for(DomesticFlight domesticFlight:ways){
            ResultWay resultWay = new ResultWay(domesticFlight);
            double price = domesticFlight.getPrice();
            double minutes = domesticFlight.getDurations();
            int comfotDegree = (int)domesticFlight.getComfort();
            double transfer = domesticFlight.getTranferTime();
            resultWay.setTotalTime(minutes);
            resultWay.setTransferTime(transfer);
            resultWay.setComfortDegree(comfotDegree);
            resultWay.setTotalPrice(price);
            resultWay.setDepartTime(domesticFlight.getDepDate());
            resultWays.add(resultWay);
            setMinAndMaxValue(price,minutes,transfer,comfotDegree);
        }
        return resultWays;
    }

    public List<ResultWay> constructReusltWayByDoubleFlight(List<DoubleFight> ways) {
        List<ResultWay> resultWays = new LinkedList<>();
        for(DoubleFight doubleFight:ways){
            ResultWay resultWay = new ResultWay(doubleFight);
            double price = doubleFight.getPrice();
            double minutes = doubleFight.getDurations();
            int comfotDegree = (int)doubleFight.getComfort();
            double transfer = doubleFight.getTranferTime();
            resultWay.setTotalTime(minutes);
            resultWay.setTransferTime(transfer);
            resultWay.setDepartTime(doubleFight.getFirstTrip().getDepDate());
            resultWay.setComfortDegree(comfotDegree);
            resultWay.setTotalPrice(price);
            resultWays.add(resultWay);
            setMinAndMaxValue(price,minutes,transfer,comfotDegree);
        }
        return resultWays;
    }

    public List<ResultWay> constructReusltWayByTrain2Flight(List<TrainToFlight> ways) {
        List<ResultWay> resultWays = new LinkedList<>();
        for(TrainToFlight trainToFlight:ways){
            ResultWay resultWay = new ResultWay(trainToFlight);
            double price = trainToFlight.getPrice();
            double minutes = trainToFlight.getDurations();
            int comfotDegree = (int)trainToFlight.getComfort();
            double transfer = trainToFlight.getTranferTime();
            Date date = getDepTimeByOneway(trainToFlight.getFirstTrip());
            resultWay.setTotalTime(minutes);
            resultWay.setTransferTime(transfer);
            resultWay.setComfortDegree(comfotDegree);
            resultWay.setDepartTime(date);
            resultWay.setTotalPrice(price);
            resultWays.add(resultWay);
            setMinAndMaxValue(price,minutes,transfer,comfotDegree);
        }
        return resultWays;
    }

    public List<ResultWay> constructReusltWayByFlightToTrain(List<FlightToTrain> ways) {
        List<ResultWay> resultWays = new LinkedList<>();
        for(FlightToTrain flightToTrain:ways){
            ResultWay resultWay = new ResultWay(flightToTrain);
            double price = flightToTrain.getPrice();
            double minutes = flightToTrain.getDurations();
            int comfotDegree = (int)flightToTrain.getComfort();
            double transfer = flightToTrain.getTranferTime();
            resultWay.setTotalTime(minutes);
            resultWay.setTransferTime(transfer);
            resultWay.setComfortDegree(comfotDegree);
            resultWay.setDepartTime(flightToTrain.getFirstTrip().getDepDate());
            resultWay.setTotalPrice(price);
            resultWays.add(resultWay);
            setMinAndMaxValue(price,minutes,transfer,comfotDegree);
        }
        return resultWays;
    }

    private void setMinAndMaxValue(double price, double minutes, double transferMinutes, int comfortDegree) {
        MinAndMax.maxPrice = price > MinAndMax.maxPrice ? price : MinAndMax.maxPrice;
        MinAndMax.minPrice = price < MinAndMax.minPrice ? price : MinAndMax.minPrice;
        MinAndMax.maxTimeMinutes = minutes > MinAndMax.maxTimeMinutes ?minutes: MinAndMax.maxTimeMinutes;
        MinAndMax.minTimeMinutes = minutes < MinAndMax.minTimeMinutes ?minutes:MinAndMax.minTimeMinutes;
        MinAndMax.maxTransferMinutes = transferMinutes > MinAndMax.maxTransferMinutes ? transferMinutes : MinAndMax.maxTransferMinutes;
        MinAndMax.maxComfortData = comfortDegree > MinAndMax.maxComfortData ? comfortDegree : MinAndMax.maxComfortData;
        MinAndMax.minComfortData = comfortDegree < MinAndMax.minComfortData ? comfortDegree : MinAndMax.minComfortData;
    }
}
