package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:11
 * @Project：guiyu-parent
 * @Description:
 */
public interface CallService {
    public void makeCall(CallOutPlan callplan, String recordFile);
}
