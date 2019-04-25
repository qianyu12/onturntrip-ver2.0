package com.example.springtest.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单例模式实现
 */
public class TransferStationInfo {
    private Map<String, List<String>> transferStation = new HashMap<>();
    private static TransferStationInfo transferStationInfo;
    public TransferStationInfo(){
        transferStation.put("E",new ArrayList<>());
        transferStation.put("S",new ArrayList<>());
        transferStation.put("N",new ArrayList<>());
        transferStation.put("M",new ArrayList<>());
        transferStation.put("NE",new ArrayList<>());
        transferStation.put("SW",new ArrayList<>());
        transferStation.put("NW",new ArrayList<>());
        try{
            readDataFromResource();
        }catch (Exception e) {
            System.out.println("Something wrong when readDataFromResource");
        }
    }
    public static TransferStationInfo getInstance(){
        if(transferStationInfo==null){
            transferStationInfo = new TransferStationInfo();
        }
        return transferStationInfo;
    }
    public  void addTransferStation(String oriention, String station) throws Exception{
        List<String> oneOrientionStations = transferStation.get(oriention);
        if (oneOrientionStations==null){
            throw new Exception();
        }else{
            oneOrientionStations.add(station);
        }
        transferStation.replace(oriention,oneOrientionStations);
    }

    public  void addTransferStation(String oriention, List<String> stations) throws Exception{
        if(oriention == null){
            throw new Exception();
        }else{
            transferStation.replace(oriention,stations);
        }
    }
    public  void printAllStation(){
        for(Map.Entry<String,List<String>> entry:transferStation.entrySet()){
            System.out.println("oriention:"+entry.getKey()+"  "+"city:"+ entry.getValue());
        }
    }

    public  void readDataFromResource() throws IOException {
        InputStream inputStream = TransferStationInfo.class.getResourceAsStream("/dataFile/transferStations");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = br.readLine()) != null) {
            String[] orientionAndStations = line.split(":");
            List<String> stations = new ArrayList<>();
            for(String station:orientionAndStations[1].split(",")){
                stations.add(station);
            }
            String orientation = orientionAndStations[0];
            try {
                addTransferStation(orientation,stations);
            }catch (Exception e){
                System.out.println("添加失败");
            }

        }
    }
    //得到中转转MAP
    public  Map returnTransferStation(){
        return transferStation;
    }
}

//package com.example.springtest.utils;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 单例模式实现
// */
//public class TransferStationInfo {
//    private static Map<String, List<String>> transferStation = new HashMap<>();
//    static {
//        transferStation.put("E",new ArrayList<>());
//        transferStation.put("S",new ArrayList<>());
//        transferStation.put("N",new ArrayList<>());
//        transferStation.put("M",new ArrayList<>());
//        transferStation.put("NE",new ArrayList<>());
//        transferStation.put("SW",new ArrayList<>());
//        transferStation.put("NW",new ArrayList<>());
//    }
//    public TransferStationInfo(){
//
//    }
//    public static TransferStationInfo getInstance(){
//        if()
//    }
//    public static void addTransferStation(String oriention, String station) throws Exception{
//        List<String> oneOrientionStations = transferStation.get(oriention);
//        if (oneOrientionStations==null){
//            throw new Exception();
//        }else{
//            oneOrientionStations.add(station);
//        }
//        transferStation.replace(oriention,oneOrientionStations);
//    }
//
//    public static void addTransferStation(String oriention, List<String> stations) throws Exception{
//        if(oriention == null){
//            throw new Exception();
//        }else{
//            transferStation.replace(oriention,stations);
//        }
//    }
//    public static void printAllStation(){
//        for(Map.Entry<String,List<String>> entry:transferStation.entrySet()){
//            System.out.println("oriention:"+entry.getKey()+"  "+"city:"+ entry.getValue());
//        }
//    }
//
//    public static void readDataFromResource() throws IOException {
//        InputStream inputStream = TransferStationInfo.class.getResourceAsStream("/dataFile/transferStations");
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        String line = null;
//        while((line = br.readLine()) != null) {
//            String[] orientionAndStations = line.split(":");
//            List<String> stations = new ArrayList<>();
//            for(String station:orientionAndStations[1].split(",")){
//                stations.add(station);
//            }
//            String orientation = orientionAndStations[0];
//            try {
//                addTransferStation(orientation,stations);
//            }catch (Exception e){
//                System.out.println("添加失败");
//            }
//
//        }
//    }
//    //得到中转转MAP
//    public static Map returnTransferStation(){
//        return transferStation;
//    }
//}