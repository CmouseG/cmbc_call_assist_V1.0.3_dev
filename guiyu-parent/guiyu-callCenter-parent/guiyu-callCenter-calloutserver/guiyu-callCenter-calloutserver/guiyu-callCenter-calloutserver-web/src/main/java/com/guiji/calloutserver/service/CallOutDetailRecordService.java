package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutDetailRecord;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 15:35
 * @Project：guiyu-parent
 * @Description:
 */
public interface CallOutDetailRecordService {
    void save(CallOutDetailRecord callOutDetailRecord);
    void update(CallOutDetailRecord callOutDetailRecord);
    void add(BigInteger callId, BigInteger callDetailId,String botWavFile);
    List<CallOutDetailRecord> findByCallId(BigInteger callId);
}
