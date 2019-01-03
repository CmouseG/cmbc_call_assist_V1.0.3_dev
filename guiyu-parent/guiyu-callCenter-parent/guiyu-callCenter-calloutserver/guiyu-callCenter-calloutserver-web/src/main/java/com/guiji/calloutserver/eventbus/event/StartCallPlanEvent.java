package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.LineCount;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 19:40
 * @Project：guiyu-parent
 * @Description:
 */
@Data
@AllArgsConstructor
public class StartCallPlanEvent {
    private Integer customerId;
    private String tempId;
    private LineCount lineCount;
}
