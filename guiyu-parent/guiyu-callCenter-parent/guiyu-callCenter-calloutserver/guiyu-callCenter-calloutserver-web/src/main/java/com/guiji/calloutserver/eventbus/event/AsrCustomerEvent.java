package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.Data;

/**
 * ASR识别事件
 */
@Data
public class AsrCustomerEvent extends AsrBaseEvent{

    private CallOutPlan callPlan;
}
