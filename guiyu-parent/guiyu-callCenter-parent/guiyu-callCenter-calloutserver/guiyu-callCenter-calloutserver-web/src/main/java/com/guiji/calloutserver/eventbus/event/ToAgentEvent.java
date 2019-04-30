package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 转人工事件
 */
@Data
@AllArgsConstructor
public class ToAgentEvent {
    private String uuid;
}
