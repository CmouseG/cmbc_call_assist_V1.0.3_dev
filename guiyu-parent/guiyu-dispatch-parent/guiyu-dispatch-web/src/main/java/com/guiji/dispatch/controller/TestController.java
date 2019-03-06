package com.guiji.dispatch.controller;

import com.guiji.dispatch.dto.DispatchRobotOpDto;
import com.guiji.dispatch.pushcallcenter.IPushPhonesHandler;
import com.guiji.dispatch.service.IResourcePoolService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/dispatch/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IResourcePoolService resourcePoolService;

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/testDistributeByUser", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean testDistributeByUser(@RequestBody DispatchRobotOpDto opRobotDto){
        boolean bool =  false;
        try {
            bool = resourcePoolService.distributeByUser();
        }catch(Exception e){
            logger.error("testDistributeByUser>>>>>>>>>>>>>",e);
        }
        return bool;
    }

    @Autowired
    private IPushPhonesHandler iPushPhonesHandler;

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/pushHandler", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void pushHandler(@RequestBody DispatchRobotOpDto opRobotDto){
        boolean bool =  false;
        try {
            iPushPhonesHandler.pushHandler();
        }catch(Exception e){
            logger.error("pushHandler>>>>>>>>>>>>>",e);
        }
    }
}
