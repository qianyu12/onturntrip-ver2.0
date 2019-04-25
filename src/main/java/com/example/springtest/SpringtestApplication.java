package com.example.springtest;

import com.alibaba.druid.util.TransactionInfo;
import com.example.springtest.utils.TransferStationInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
//扫描 mybatis mapper 包路径
@MapperScan(basePackages = "com.example.springtest.dao")
//开启定时任务
@EnableScheduling
//开启异步调用方法
@EnableAsync
public class SpringtestApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringtestApplication.class, args);
        TransferStationInfo transferStationInfo = TransferStationInfo.getInstance();
        transferStationInfo.printAllStation();
    }

}
