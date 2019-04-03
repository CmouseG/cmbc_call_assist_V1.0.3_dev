package com.guiji.calloutserver.eventbus.event;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统计报表事件，电话结束后，记录到统计数据到表里
 */
@Data
@AllArgsConstructor
public class StatisticReportEvent {

    CallOutPlan callPlan;
}
