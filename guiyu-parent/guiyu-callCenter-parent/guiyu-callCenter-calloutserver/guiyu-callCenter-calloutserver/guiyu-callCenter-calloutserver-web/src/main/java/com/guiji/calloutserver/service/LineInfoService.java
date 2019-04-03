package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.LineInfo;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 15:23
 * @Project：guiyu-parent
 * @Description:
 */
public interface LineInfoService {
    LineInfo getByLineId(Integer lineId);

//    void add(List<CallOutPlan> totalCallPlans);
}
