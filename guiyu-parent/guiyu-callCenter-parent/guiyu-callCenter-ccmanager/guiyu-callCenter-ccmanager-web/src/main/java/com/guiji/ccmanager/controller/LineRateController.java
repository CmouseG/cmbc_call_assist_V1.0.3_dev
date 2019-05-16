package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.api.ILineRate;
import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.ccmanager.entity.RateTimeReq;
import com.guiji.ccmanager.service.LineRateService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 线路了接通率，供调度中心使用
 */
@Validated
@RestController
@Slf4j
public class LineRateController implements ILineRate {

    @Autowired
    LineRateService lineRateService;

    @Override
    public Result.ReturnData<LineRateResponse> getLineRate(@RequestParam(value = "lineId") Integer lineId,
                                                           @RequestParam(value = "startTime") Date startTime,
                                                           @RequestParam(value = "endTime") Date endTime) {

        LineRateResponse lineRateResponse = lineRateService.getLineRate(lineId, startTime, endTime);

        return Result.ok(lineRateResponse);
    }

    @Override
    public Result.ReturnData<List<LineRateResponse>> getLineRateAll( @RequestParam(value = "startTime") String startTime,
                                                                     @RequestParam(value = "endTime") String endTime)throws Exception{
        Date start = null;
        Date end = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
        } catch (Exception e) {
            log.error("getLineRateAll传递参数不正确"+e);
            start = getStartTime();
            end = getnowEndTime();
        }

        List<LineRateResponse> list =lineRateService.getLineRateAll(start, end);
//        log.info("getLineRateAll返回数据,list[{}]",list);
        return Result.ok(list);
    }

    private static Date getStartTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        return calendar1.getTime();
    }

    private static Date getnowEndTime() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }
}
