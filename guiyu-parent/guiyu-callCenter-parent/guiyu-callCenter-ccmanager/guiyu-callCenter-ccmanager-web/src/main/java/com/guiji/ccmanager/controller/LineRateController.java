package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.api.ILineRate;
import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.ccmanager.service.LineRateService;
import com.guiji.component.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 线路了接通率，供调度中心使用
 */
@RestController
public class LineRateController implements ILineRate {

    @Autowired
    LineRateService lineRateService;

    @Override
    public Result.ReturnData<LineRateResponse> getLineRate(Integer lineId, Date startTime, Date endTime) {

        LineRateResponse lineRateResponse = lineRateService.getLineRate(lineId, startTime, endTime);

        return Result.ok(lineRateResponse);
    }

    @Override
    public Result.ReturnData<List<LineRateResponse>> getLineRateAll(Date startTime, Date endTime) {

        List<LineRateResponse> list =lineRateService.getLineRateAll(startTime, endTime);

        return Result.ok(list);
    }
}
