//package com.guiji.dispatch.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.guiji.common.result.Result;
//import com.guiji.dispatch.api.IDispatchPlanService;
//import com.guiji.dispatch.model.CommonResponse;
//import com.guiji.dispatch.model.Schedule;
//import com.guiji.dispatch.util.Log;
//
//@RestController
//public class testController {
//    @Autowired
//    private IDispatchPlanService jssPlanService;
//    
//    
//    @RequestMapping(value = "/queryreboot", method = RequestMethod.GET)
//    boolean querySchedules(@RequestParam final String reboot) throws Exception {
//    	
//    	for(int i =0;i<100;i++){
//    		Schedule schedule = new Schedule();
//    		schedule.setId(String.valueOf(i++));
//    		schedule.setPhones(phones);
//    		jssPlanService.addSchedule(schedule);
//    	}
//    	jssPlanService.addSchedule(schedule)
//    	return false;
//    }
//}
