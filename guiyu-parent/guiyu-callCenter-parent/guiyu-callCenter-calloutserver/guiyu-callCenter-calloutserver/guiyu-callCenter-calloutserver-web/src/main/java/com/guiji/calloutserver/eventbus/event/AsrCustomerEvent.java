package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.Data;

/**
 * ASR识别事件
 */
public class AsrCustomerEvent extends AsrBaseEvent{

    private CallOutPlan callPlan;

    public CallOutPlan getCallPlan() {
        return callPlan;
    }

    public void setCallPlan(CallOutPlan callPlan) {
        this.callPlan = callPlan;
    }

    @Override
    public String toString() {
        return super.toString()+",callid:"+callPlan.getCallId();
    }
}
