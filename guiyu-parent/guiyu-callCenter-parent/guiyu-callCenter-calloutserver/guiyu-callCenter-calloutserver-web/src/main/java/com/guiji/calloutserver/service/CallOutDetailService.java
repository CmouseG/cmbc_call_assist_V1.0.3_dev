package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutDetail;

import java.math.BigInteger;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 15:35
 * @Project：guiyu-parent
 * @Description:
 */
public interface CallOutDetailService {
    void save(CallOutDetail callOutDetail);
    void update(CallOutDetail callOutDetail);
    void save(CallOutDetail calloutdetail, String recordFile);

    CallOutDetail getLastDetail(String callId,Integer orgId);

    Integer getTalkNum(BigInteger bigInteger, Integer orgId);

    CallOutDetail getLastCustomerDetail(BigInteger callId, Integer orgId);
}
