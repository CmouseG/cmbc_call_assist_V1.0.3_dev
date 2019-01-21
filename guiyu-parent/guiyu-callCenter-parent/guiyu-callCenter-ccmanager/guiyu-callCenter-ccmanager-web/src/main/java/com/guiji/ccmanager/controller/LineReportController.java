package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.api.IReportLine;
import com.guiji.ccmanager.service.LineReportService;
import com.guiji.component.result.Result;
import com.guiji.utils.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Validated
@RestController
public class LineReportController implements IReportLine {

    private final Logger log = LoggerFactory.getLogger(LineReportController.class);

    @Autowired
    private LineReportService lineReportService;

    @GetMapping(value = "test123")
    public List test123(){
        return   lineReportService.statisticsReportLineCode();
    }

    @ApiOperation(value = "线路监控信息")
    @GetMapping(value = "getLineMonitorReport")
    public Result.ReturnData getLineMonitorReport(String lineId, String dimension,
                                                  String orgCode,  Long userId) {

        if(StringUtils.isNotBlank(dimension)){
            dimension = "now";
        }
        Date start = null;
        if(dimension.equals("now")){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, -2);
            start  = c.getTime();
        }else  if(dimension.equals("day")){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, -24);
            start  = c.getTime();
        }else  if(dimension.equals("week")){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, -168);
            start  = c.getTime();
        }

        List<Map> list = lineReportService.getLineMonitorReport(StringUtils.isNotBlank(lineId) ? Integer.valueOf(lineId) : null,userId,start);

        return Result.ok(list);
    }


    @ApiOperation(value = "线路错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getLineHangupDetail")
    public Result.ReturnData getLineHangupDetail(String lineId,String startTime, String enTime) throws ParseException {

        Date end = null;
        Date start = null;
        if(StringUtils.isNotBlank(startTime)){
            start = DateUtil.stringToDate(startTime,"yyyy-MM-dd HH:mm:ss");
        }else{
            DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
            String today = dfs.format(new Date()) +" 00:00:00";
            start =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(today);
        }
        if(StringUtils.isNotBlank(enTime)){
            end = DateUtil.stringToDate(enTime,"yyyy-MM-dd HH:mm:ss");
        }

        Map map = lineReportService.getLineHangupDetail(Integer.valueOf(lineId),start,end);
        return Result.ok(map);
    }


}
