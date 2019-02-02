package com.guiji.ccmanager.service;

import com.guiji.ccmanager.entity.LineRateResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LineRateService {
    LineRateResponse getLineRate(Integer lineId, Date startTime, Date endTime);

    List<LineRateResponse> getLineRateAll(Date startTime, Date endTime);
}
