package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AfterCallEvent {
    /**
     * 当前已结束的呼叫计划
     */
    CallOutPlan callPlan;

}
