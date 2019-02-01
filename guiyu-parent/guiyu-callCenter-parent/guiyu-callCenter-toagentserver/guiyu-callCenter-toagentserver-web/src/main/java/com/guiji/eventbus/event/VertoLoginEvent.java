package com.guiji.eventbus.event;

import com.guiji.callcenter.dao.entity.Agent;
import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2019/2/1 11:50
 * @Project：guiyu-parent
 * @Description:
 */
@Data
public class VertoLoginEvent {
    private Agent agent;
}
