package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchBatchLine;

/**
 * @Classname LineMarketService
 * @Description TODO
 * @Date 2019/5/21 16:30
 * @Created by qinghua
 */
public interface LineMarketService {

    /**
     * 根据lineId查询线路信息
     * @param lineId
     * @param userId
     * @return
     */
    DispatchBatchLine getByLineId(Integer lineId, Integer userId);

}
