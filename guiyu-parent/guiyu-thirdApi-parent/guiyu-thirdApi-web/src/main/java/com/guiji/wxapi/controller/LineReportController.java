package com.guiji.wxapi.controller;

import com.guiji.ccmanager.api.IReportLine;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class LineReportController {

    @Autowired
    IReportLine iReportLine;


    @ApiOperation(value = "线路监控信息")
    @GetMapping(value = "getLineMonitorReport")
    public Result.ReturnData getLineMonitorReport( String lineId,String dimension,@RequestHeader Integer authLevel,
                                                   @RequestHeader String orgCode,@RequestHeader Long userId) {
            return iReportLine.getLineMonitorReport(lineId,dimension,orgCode,userId,authLevel);

    }


    @ApiOperation(value = "线路错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getLineHangupDetail")
    public Result.ReturnData getLineHangupDetail( String lineId, String startDate,String endDate,
                                                  String orgCode, String userId, String authLevel) throws ParseException {

        return iReportLine.getLineHangupDetail(lineId,startDate,endDate,orgCode,Integer.valueOf(userId),Integer.valueOf(authLevel));
    }


}
