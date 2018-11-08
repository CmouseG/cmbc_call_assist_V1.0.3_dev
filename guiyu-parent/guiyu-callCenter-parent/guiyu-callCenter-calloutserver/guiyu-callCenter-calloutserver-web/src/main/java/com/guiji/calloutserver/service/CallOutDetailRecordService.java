package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutDetailRecord;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 15:35
 * @Project：guiyu-parent
 * @Description:
 */
public interface CallOutDetailRecordService {
    void save(CallOutDetailRecord callOutDetailRecord);
    void add(String callId, String callDetailId, String customerWavFile, String botWavFile);
    List<CallOutDetailRecord> findByCallId(String callId);
}
