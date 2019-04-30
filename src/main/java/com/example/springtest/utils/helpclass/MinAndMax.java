package com.example.springtest.utils.helpclass;

public class MinAndMax {
    public static double minPrice = Double.MAX_VALUE;
    public static double maxPrice = Double.MIN_VALUE;
    public static double minTimeMinutes = Double.MAX_VALUE;
    public static double maxTimeMinutes = Double.MIN_VALUE;
    public static double maxTransferMinutes = Double.MIN_VALUE;
    public static double minTransferMinutes = 0;
    public static int maxComfortData = Integer.MIN_VALUE;
    public static int minComfortData = Integer.MAX_VALUE;

    public static void print(){
        System.out.println("minprice:"+minPrice);
        System.out.println("maxPrice:"+maxPrice);
        System.out.println("mintime"+minTimeMinutes);
        System.out.println("maxtime"+maxTimeMinutes);
        System.out.println("maxtrans"+maxTransferMinutes);
        System.out.println("mintrans"+minTransferMinutes);
        System.out.println("maxCom"+maxComfortData);
        System.out.println("minCom"+minComfortData);
    }
}
