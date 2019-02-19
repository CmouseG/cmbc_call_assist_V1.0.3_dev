package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.service.LineReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
public class TestController {

    @Autowired
    LineReportService lineReportService;

    @GetMapping(value = "test1234566")
    public void test1234566() throws ParseException {
        lineReportService.statisticsReportLineCode();
    }



}
