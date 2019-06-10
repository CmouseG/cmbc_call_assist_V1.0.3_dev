package com.guiji.clm.service;

import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.model.SipLineVO;

/**
 * @Classname LineService
 * @Description TODO
 * @Date 2019/6/10 10:11
 * @Created by qinghua
 */
public interface LineService {

    /**
     * 根据lineId和userId查询线路信息
     * @param userId
     * @param lineId
     * @return
     */
    SipLineVO queryLineInfo(Integer userId, Integer lineId);


    void updateLineInfo(SipLineExclusive exclusive, int op);

    void updateLineInfo(VoipGwPort voipGwPort, int op);
}
