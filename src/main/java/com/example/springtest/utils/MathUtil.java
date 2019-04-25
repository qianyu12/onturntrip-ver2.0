package com.example.springtest.utils;

public class MathUtil {
    public static double Normalization(double value,double min,double max){
        return (value-min)/(max-min);
    }

    public static double Normalization2(double value){
        return Math.atan(value)*2/Math.PI;
    }
}
