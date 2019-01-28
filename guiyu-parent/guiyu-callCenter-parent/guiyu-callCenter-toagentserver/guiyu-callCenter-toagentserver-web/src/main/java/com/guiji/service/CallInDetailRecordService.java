package com.guiji.service;

import com.guiji.callcenter.dao.entity.CallInDetailRecord;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 15:35
 * @Project：guiyu-parent
 * @Description:
 */
public interface CallInDetailRecordService {
    void save(CallInDetailRecord callInDetailRecord);
    void update(CallInDetailRecord callInDetailRecord);
    void add(BigInteger callId, BigInteger callDetailId, String botWavFile);
    List<CallInDetailRecord> findByCallId(BigInteger callId);
}
