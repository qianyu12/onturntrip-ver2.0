package com.example.springtest.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GaodeController {
    private final String key = "c396dac010119285652f3e2f1143eb29";
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/testGaode")
    public JSONObject getGeoCode(){
        String url = "https://restapi.amap.com/v3/geocode/geo?";
        url+="key="+key;
        url+="&address="+"四川大学江安校区";
        url+="&city="+"成都";
        String result= restTemplate.getForObject(url,String.class);
        JSONObject jsonResult = JSONObject.parseObject(result);
        JSONArray jsonResultArray = jsonResult.getJSONArray("geocodes");
        System.out.println(jsonResultArray);
        String location = jsonResultArray.getJSONObject(0).getString("location");
        System.out.println(location);
        return jsonResult;
    }
}
