package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.LineCount;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 20:12
 * @Project：guiyu-parent
 * @Description:
 */
public interface LineCountService {
    List<LineCount> findByInstanceIdAndLineId(String instanceId, Integer lineId);
}
