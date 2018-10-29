package com.guiji.dispatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.dispatch.api.IDispatchPlanService;
import com.guiji.dispatch.model.CommonResponse;

@RestController
public class testController {
    @Autowired
    private IDispatchPlanService jssPlanService;
    
    
    @RequestMapping(value = "/queryreboot", method = RequestMethod.GET)
    CommonResponse querySchedules(@RequestParam final String reboot) throws Exception {
    	return jssPlanService.querySchedules(reboot);
    }
}
