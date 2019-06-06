package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.LineSimlimitConfig;
import com.guiji.ccmanager.vo.SimLimitConfigReq;

/**
 * author:liyang
 * Date:2019/5/30 10:01
 * Description:
 */
public interface SimLimitService {
    LineSimlimitConfig getSimLimitConfigInfo(Integer lineId);

    boolean updateSimLimitConfigInfo(SimLimitConfigReq simLimitConfigReq);
}
