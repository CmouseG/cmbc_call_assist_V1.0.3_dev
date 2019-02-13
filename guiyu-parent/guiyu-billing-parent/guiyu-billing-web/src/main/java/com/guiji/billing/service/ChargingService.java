package com.guiji.billing.service;

import com.guiji.billing.dto.CallChargingNotifyDto;

public interface ChargingService {

    /**
     * 通话计费处理
     * @param callChargingNotifyDto
     * @return
     */
    boolean charging(CallChargingNotifyDto callChargingNotifyDto);
}
