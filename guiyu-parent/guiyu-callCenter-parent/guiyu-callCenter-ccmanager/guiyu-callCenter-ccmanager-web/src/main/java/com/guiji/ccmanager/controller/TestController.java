package com.guiji.ccmanager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    TestController2 testController2;

    @GetMapping(value="test12345")
    public void test(@RequestParam(value="count", required = true) String count){
        for(int i=0;i<Integer.valueOf(count);i++){
            testController2.test();
        }
    }

}
