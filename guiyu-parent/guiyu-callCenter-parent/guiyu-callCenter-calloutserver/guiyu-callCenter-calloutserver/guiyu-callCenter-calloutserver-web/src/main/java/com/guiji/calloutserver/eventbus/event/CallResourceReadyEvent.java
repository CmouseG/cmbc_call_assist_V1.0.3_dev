package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/2 14:49
 * @Project：guiyu-parent
 * @Description:
 */
@Data
@AllArgsConstructor
public class CallResourceReadyEvent {
    private CallOutPlan callPlan;
}
