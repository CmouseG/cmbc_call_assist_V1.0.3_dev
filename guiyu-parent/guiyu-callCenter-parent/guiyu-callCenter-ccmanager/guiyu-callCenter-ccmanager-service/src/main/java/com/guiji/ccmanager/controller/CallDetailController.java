package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.utils.DateUtil;
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
 * @Description:
 */
@RestController
public class CallDetailController {

    @Autowired
    private CallDetailService callDetailService;

    @ApiOperation(value = "获取客户指定时间内的通话记录列表")
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
    public void getCallDetail(){

    }

}
