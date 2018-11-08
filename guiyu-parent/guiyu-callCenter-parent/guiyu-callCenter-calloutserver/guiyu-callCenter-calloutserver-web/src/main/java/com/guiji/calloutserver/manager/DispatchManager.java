package com.guiji.calloutserver.manager;

import com.guiji.callcenter.dao.entity.CallOutPlan;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:18
 * @Project：guiyu-parent
 * @Description:
 */
public interface DispatchManager {
    /**
     * 拉取外呼计划
     * @param customerId     用户id
     * @param requestCount   请求的呼叫计划数量
     * @param lineId         线路id
     * @return
     */
    List<CallOutPlan> pullCallPlan(String customerId, Integer requestCount, Integer lineId);
}
