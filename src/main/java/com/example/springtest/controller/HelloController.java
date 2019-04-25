package com.example.springtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@EnableAutoConfiguration
public class HelloController {

    String name;

    String id;
    @RequestMapping("/hello")
    private String index(int idTest){
        System.out.println(idTest);
        return "test234";
    }
    @RequestMapping("/test")
    private ModelAndView goToIndex(){
        ModelAndView modelAndView =  new ModelAndView("/index.html");
        return modelAndView;
    }
}
