package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.vo.CallOutPlanVO;
import com.guiji.utils.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:16
 * @Description:  通话记录列表，通话记录详情
 */
@RestController
public class CallDetailController {

    @Autowired
    private CallDetailService callDetailService;

    @ApiOperation(value = "获取客户指定时间内的通话记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "false"),
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="callrecord")
    public List<CallOutPlan> callrecord(String startDate, String endDate, String customerId){
        Date start = null;
        Date end = null;
        if(StringUtils.isNotBlank(startDate)){
            start = DateUtil.stringToDate(startDate,"yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isNotBlank(endDate)){
            end = DateUtil.stringToDate(endDate,"yyyy-MM-dd HH:mm:ss");
        }
        return callDetailService.callrecord(start,end,customerId);
    }


    @ApiOperation(value = "查看通话记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query")
    })
    @GetMapping(value="getCallDetail")
    public CallOutPlanVO getCallDetail(String callId){
        if(StringUtils.isNotBlank(callId)){
        return callDetailService.getCallDetail(callId);
        }
        return null;//应该放回缺少参数
    }

}
