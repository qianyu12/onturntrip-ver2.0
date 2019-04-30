package com.example.springtest.service.impl;

import com.example.springtest.dao.DomesticFlightMapper;
import com.example.springtest.dao.OnewayMapper;
import com.example.springtest.dao.TrainAndFlightMapper;
import com.example.springtest.domain.*;
import com.example.springtest.service.FlightService;
import com.example.springtest.service.TrainAndFlightService;
import com.example.springtest.service.TrainService;
import com.example.springtest.utils.MathUtil;
import com.example.springtest.utils.helpclass.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TrainAndFlightImpl implements TrainAndFlightService {
    @Autowired
    private TrainService trainService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private TrainAndFlightMapper trainAndFlightMapper;
    @Autowired
    private OnewayMapper onewayMapper;
    @Autowired
    private DomesticFlightMapper domesticFlightMapper;
    @Autowired
    private Util util;

    /**
     * 飞机转火车，抛弃出生点的飞机；
     *
     * @param startCity
     * @param endCity
     * @return
     */
    @Override
    public List<FlightToTrain> getFlightToTrain(String startCity, String endCity) {
        try {
            List<String> tranferStations = util.getTransfeCitys(startCity, endCity, false, true);
            List<String> start = new LinkedList<>();
            start.add(startCity);
            List<String> end = new LinkedList<>();
            end.add(endCity);
            List<DomesticFlight> flights = trainAndFlightMapper.slectDomesticFlightByManyCityName(start, tranferStations);
            List<Oneway> trains = trainAndFlightMapper.selectTrainByManyCityName(tranferStations, end);
            List<FlightToTrain> result = new LinkedList<>();
            for (DomesticFlight f : flights) {
                for (Oneway o : trains) {
                    if (o.getFromStation().getCityName().trim().equals(f.getArrCityName())) {
                        Long minutes = util.getMinuteOfFlightAndTrainDate(o, f, true);
                        //应该请求高德接口判断两点之间距离
                        if (minutes >= 60 && minutes <= 180) {
                            result.add(new FlightToTrain(f, o));
                        }
                    }
                }
            }
            return result;
        } catch (CityNotExisitException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrainToFlight> getTrainToFlight(String startCity, String endCity) {
        try {
            List<String> tranferStations = util.getTransfeCitys(startCity, endCity, true, false);
            List<String> start = new LinkedList<>();
            start.add(startCity);
            List<String> end = new LinkedList<>();
            end.add(endCity);
            List<Oneway> trains = trainAndFlightMapper.selectTrainByManyCityName(start, tranferStations);
            List<DomesticFlight> flights = trainAndFlightMapper.slectDomesticFlightByManyCityName(tranferStations, end);
            List<TrainToFlight> result = new LinkedList<>();
            for (DomesticFlight f : flights) {
                for (Oneway o : trains) {
                    if (o.getToStation().getCityName().trim().equals(f.getDepCityName())) {
                        Long minutes = util.getMinuteOfFlightAndTrainDate(o, f, false);
                        //应该请求高德接口判断两点之间距离
                        if (minutes >= 60 && minutes <= 180) {
                            result.add(new TrainToFlight(o, f));
                        }
                    }
                }
            }
            return result;
        } catch (CityNotExisitException e) {
            e.printStackTrace();
        }
        return null;
    }

    //对各个纬度的数据进行归一化处理，然后用比例算出结果
    @Override
    public List<ResultWay> getWaysByWeight(String start, String end, Weight weight) {
        List<ResultWay> resultList = getAllTransferWay(start,end);
        try {
            List<Oneway> onewayList = trainService.getDirectOneWay(start, end);
            List<DoubleWay> doubleWayList = trainService.getTransferWay(start, end);
            List<DomesticFlight> domesticFlightList = flightService.getFlightInfoByCitypotName(start, end);

            resultList.addAll(util.constructReusltWayByOneWay(onewayList));
            resultList.addAll(util.constructReusltWayByDoubleWay(doubleWayList));
            resultList.addAll(util.constructReusltWayByFlight(domesticFlightList));

            //计算每个路程的优先值
            System.out.println(weight.getComftScale() + "--" + weight.getTimeScale() + "--" + weight.getTranferTimeScale() + "--" + weight.getPriceScale());
            for (ResultWay resultWay : resultList) {
                double priceScore;
                double transferScore;
                double comforScore;
                double timeScore;
                //价格权重
                if (MinAndMax.minPrice == MinAndMax.maxPrice) {
                    priceScore = 1;
                } else {
                    priceScore = MathUtil.Normalization(resultWay.getTotalPrice(), MinAndMax.minPrice, MinAndMax.maxPrice) * weight.getPriceScale();
                }
                //时间权重
                if (MinAndMax.minTimeMinutes == MinAndMax.maxTimeMinutes) {
                    timeScore = 1;
                } else {
                    timeScore = MathUtil.Normalization(resultWay.getTotalTime(), MinAndMax.minTimeMinutes, MinAndMax.maxTimeMinutes) * weight.getTimeScale();
                }
                //舒适度权重
                if (MinAndMax.maxComfortData == MinAndMax.minComfortData) {
                    comforScore = 1;
                } else {
                    comforScore = MathUtil.Normalization(resultWay.getComfortDegree(), MinAndMax.minComfortData, MinAndMax.maxComfortData) * weight.getComftScale();
                }
                //中转权重
                if (MinAndMax.maxTransferMinutes == MinAndMax.minTransferMinutes) {
                    transferScore = 1;
                } else {
                    transferScore = MathUtil.Normalization(resultWay.getTransferTime(), MinAndMax.minTransferMinutes, MinAndMax.maxTransferMinutes) * weight.getTranferTimeScale();
                }
                resultWay.setResultScore(priceScore + timeScore + comforScore + transferScore);
                if (priceScore + timeScore + comforScore + transferScore < 0) {
                    System.out.println(resultWay.getTotalPrice() + "---" + resultWay.getComfortDegree());
                    System.out.println(resultWay.toString());
                    System.out.println(priceScore);
                    System.out.println(timeScore);
                    System.out.println(comforScore);
                    System.out.println(resultWay.getTransferTime());
                    System.out.println(transferScore);
                }
            }
            //按照比例将进行排序
            Collections.sort(resultList, new Comparator<ResultWay>() {
                @Override
                public int compare(ResultWay o1, ResultWay o2) {
                    return o1.getResultScore() < o2.getResultScore() ? -1 : o1.getResultScore() > o2.getResultScore() ? 1 : 0;
                }
            });
            List<ResultWay> temp = new LinkedList<>();
            Set<String> set = new HashSet<>();
            for (ResultWay resultWay : resultList) {
                if (resultWay.getWay() instanceof DoubleWay) {
                    String no = ((DoubleWay) resultWay.getWay()).getFirstTrip().getTrainNo();
                    if (!set.contains(no)) {
                        set.add(no);
                        temp.add(resultWay);
                    }
                } else if (resultWay.getWay() instanceof FlightToTrain) {
                    String no = resultWay.getFlightToTrain().getFirstTrip().getFlightNumber();
                    if (!set.contains(no)) {
                        set.add(no);
                        temp.add(resultWay);
                    }
                } else if (resultWay.getWay() instanceof TrainToFlight) {
                    String no = resultWay.getTrainToFlight().getFirstTrip().getTrainNo();
                    if (!set.contains(no)) {
                        set.add(no);
                        temp.add(resultWay);
                    }
                } else {
                    temp.add(resultWay);
                }
            }
            resultList = temp;
            MinAndMax.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<ResultWay> getAllTransferWay(String start,String end) {
        List<ResultWay> resultList = new LinkedList();

        List<DoubleFight> doubleFightList = flightService.getDoubleFlight(start, end);
        List<FlightToTrain> flightToTrainList = this.getFlightToTrain(start, end);
        List<TrainToFlight> trainToFlightList = this.getTrainToFlight(start, end);

        resultList.addAll(util.constructReusltWayByDoubleFlight(doubleFightList));
        resultList.addAll(util.constructReusltWayByFlightToTrain(flightToTrainList));
        resultList.addAll(util.constructReusltWayByTrain2Flight(trainToFlightList));


        return  resultList;
    }

    /**
     * 0:默认
     * 1:总时间
     * 2:总价格
     * 3:中转时间
     * 4:时间
     *
     * @param resultWayList
     * @param order
     * @return
     */
    @Override
    public List<ResultWay> orderWeightWay(List<ResultWay> resultWayList, int order) {
        if (order == 1) {
            Collections.sort(resultWayList, new Comparator<ResultWay>() {
                @Override
                public int compare(ResultWay o1, ResultWay o2) {
                    return Double.compare(o1.getTotalTime(), o2.getTotalTime());
                }
            });
        } else if (order == 2) {
            Collections.sort(resultWayList, new Comparator<ResultWay>() {
                @Override
                public int compare(ResultWay o1, ResultWay o2) {
                    return Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
                }
            });
        } else if (order == 3) {
            Collections.sort(resultWayList, new Comparator<ResultWay>() {
                @Override
                public int compare(ResultWay o1, ResultWay o2) {
                    return Double.compare(o1.getTransferTime(), o2.getTransferTime());
                }
            });
        } else if (order == 0) {
            Collections.sort(resultWayList, new Comparator<ResultWay>() {
                @Override
                public int compare(ResultWay o1, ResultWay o2) {
                    return Long.compare(o1.getDepartTime().getTime(), o2.getDepartTime().getTime());
                }
            });
        } else {
            System.out.println("enter normal");
            return resultWayList;
        }
        return resultWayList;
    }

    @Override
    public List<Way> testWays(String start, String end) throws IOException {
        List<Way> ways = new LinkedList<>();
        List<Oneway> onewayList = trainService.getDirectOneWay(start, end);
        List<DoubleWay> doubleWayList = trainService.getTransferWay(start, end);
        List<DomesticFlight> domesticFlightList = flightService.getFlightInfoByCitypotName(start, end);
        List<DoubleFight> doubleFightList = flightService.getDoubleFlight(start, end);
        List<FlightToTrain> flightToTrainList = getFlightToTrain(start, end);
        List<TrainToFlight> trainToFlightList = getTrainToFlight(start, end);
        ways.addAll(onewayList);
        ways.addAll(doubleFightList);
        ways.addAll(domesticFlightList);
        ways.addAll(doubleWayList);
        ways.addAll(flightToTrainList);
        ways.addAll(trainToFlightList);
        return ways;
    }

}
