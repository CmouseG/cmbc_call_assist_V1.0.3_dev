package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.component.result.Result;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 13:45
 * @Description:
 */
public interface CallManagerOutService {

    public Result.ReturnData<Object> startcallplan(String customerId, String tempId, String lineId);

    public CallOutPlan getCallRecordById(String callId);
}
