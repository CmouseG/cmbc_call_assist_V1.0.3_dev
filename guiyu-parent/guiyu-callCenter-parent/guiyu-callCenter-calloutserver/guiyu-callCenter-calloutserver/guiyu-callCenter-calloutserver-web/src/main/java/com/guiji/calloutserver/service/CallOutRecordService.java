package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutRecord;

import java.math.BigInteger;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 16:56
 * @Project：guiyu-parent
 * @Description:
 */
public interface CallOutRecordService {
    CallOutRecord findByCallId(BigInteger callId);
    void save(CallOutRecord callOutRecord);

    void update(CallOutRecord callOutRecord);
}
