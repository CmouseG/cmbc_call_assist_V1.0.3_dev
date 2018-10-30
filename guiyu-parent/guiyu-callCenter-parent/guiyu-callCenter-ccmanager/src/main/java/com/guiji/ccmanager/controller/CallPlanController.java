package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.service.CallPlanService;
import com.guiji.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 16:58
 * @Description:
 */
@RestController
public class CallPlanController {

    @Autowired
    private CallPlanService callPlanService;

    @GetMapping(value="startcallplan")
    public void startcallplan(){



        callPlanService.startcallplan();

    }

}
