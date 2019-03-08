package com.guiji.billing.controller;

import com.guiji.billing.dto.CallChargingNotifyDto;
import com.guiji.billing.dto.ChargingTermNotifyDto;
import com.guiji.billing.dto.QueryUserAcctDto;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.service.BillingUserAcctService;
import com.guiji.billing.service.ChargingService;
import com.guiji.billing.task.AcctArrearageTaskJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/billing/test")
public class TestController {

    @Autowired
    private ChargingService chargingService;

    @Autowired
    private BillingUserAcctService billingUserAcctService;

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/charging", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void charging(@RequestBody CallChargingNotifyDto callChargingNotifyDto) {
        chargingService.charging(callChargingNotifyDto);
    }

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/receiveAcctUserChargingTerm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void receiveAcctUserChargingTerm(@RequestBody ChargingTermNotifyDto chargingTermNotifyDto) {
        billingUserAcctService.receiveAcctUserChargingTerm(chargingTermNotifyDto);
    }

    @Autowired
    private AcctArrearageTaskJobHandler acctArrearageTaskJobHandler;

    @RequestMapping(value = "/acctArrearageTaskJobHandler", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ReturnT<String> acctArrearageTaskJobHandler(@RequestBody ChargingTermNotifyDto chargingTermNotifyDto) throws Exception {
        return acctArrearageTaskJobHandler.execute("");
    }

    @RequestMapping(value = "/queryPlanRemarkById", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ReturnT<String> queryPlanRemarkById(@RequestBody ChargingTermNotifyDto chargingTermNotifyDto) throws Exception {
        return acctArrearageTaskJobHandler.execute("");
    }
}
