package com.guiji.billing.controller;

import com.guiji.api.IAcctUser;
import com.guiji.billing.dto.QueryUserAcctDto;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.service.BillingUserAcctService;
import com.guiji.component.result.Result;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.JsonUtils;
import com.guiji.vo.ArrearageNotifyVo;
import com.guiji.vo.BillingUserAcctVo;

import com.xxl.job.core.biz.model.ReturnT;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(value = "/billing/api/acctUser")
public class ApiAcctUserController implements IAcctUser {

    private Logger logger = LoggerFactory.getLogger(ApiAcctUserController.class);

    @Autowired
    private BillingUserAcctService billingUserAcctService;

    @Override
    @ApiOperation(value="根据企业员工ID查询企业账户", notes="根据企业员工ID查询企业账户")
    @RequestMapping(value = "/billing/api/acctUser/queryAcctByUserId", method = {RequestMethod.POST})
    @ResponseBody
    public Result.ReturnData<BillingUserAcctVo> queryAcctByUserId(@RequestHeader String userId) {
        BillingUserAcctBean acct = billingUserAcctService.queryUserAcctByUserId(userId);
        BillingUserAcctVo acctVo = new BillingUserAcctVo();
        if(null != acct){
            BeanUtils.copyProperties(acct, acctVo, BillingUserAcctVo.class);
        }else{
            acctVo = null;
        }
        return new Result.ReturnData<BillingUserAcctVo>(acctVo);
    }

    @Override
    @ApiOperation(value="查询欠费企业用户列表", notes="查询欠费企业用户列表")
    @RequestMapping(value = "/billing/api/acctUser/queryArrearageUserList", method = {RequestMethod.POST})
    public Result.ReturnData<ArrearageNotifyVo> queryArrearageUserList() {
        ArrearageNotifyVo arr = billingUserAcctService.queryArrearageUserList();
        return new Result.ReturnData<ArrearageNotifyVo>(arr);
    }

    @Autowired
    private com.guiji.billing.task.AcctArrearageTaskJobHandler AcctArrearageTaskJobHandler;

    @ApiOperation(value="测试定时任务，查询欠费企业用户，消息通知", notes="测试定时任务，查询欠费企业用户，消息通知")
    @RequestMapping(value = "/billing/api/acctUser/testAcctArrearageTaskJob", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ReturnT<String> testAcctArrearageTaskJob(@RequestBody QueryUserAcctDto queryUserAcctDto) throws Exception {
        try {
            return AcctArrearageTaskJobHandler.execute("");
        }catch(Exception e){
            return null;
        }
    }

    @Autowired
    QueueSender queueSender;

    @ApiOperation(value="测试定时任务，查询欠费企业用户，消息通知", notes="测试定时任务，查询欠费企业用户，消息通知")
    @RequestMapping(value = "/billing/api/acctUser/testPushCharging", method = {RequestMethod.POST, RequestMethod.GET})
    public ReturnT<String> testPushCharging() throws Exception {
        try {
            queueSender.send("billing.ACCTCHARGING","{\"beginTime\":1548842036000,\"billSec\":0,\"endTime\":1548842042000,\"lineId\":276,\"phone\":\"18652003060\",\"userId\":41}");
            return ReturnT.SUCCESS;
        }catch(Exception e){
            return null;
        }
    }

    @Autowired
    @ApiOperation(value="测试定时任务，查询欠费企业用户，消息通知", notes="测试定时任务，查询欠费企业用户，消息通知")
    @RequestMapping(value = "/billing/api/acctUser/testAddAcct", method = {RequestMethod.POST, RequestMethod.GET})
    public ReturnT<String> testAddAcct() throws Exception {
        try {

            return ReturnT.SUCCESS;
        }catch(Exception e){
            return null;
        }
    }



}
