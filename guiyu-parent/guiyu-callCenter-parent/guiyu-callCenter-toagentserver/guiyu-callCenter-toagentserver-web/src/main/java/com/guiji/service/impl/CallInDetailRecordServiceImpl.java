package com.guiji.service.impl;

import com.guiji.callcenter.dao.entity.CallInDetailRecord;
import com.guiji.service.CallInDetailRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/16 19:44
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallInDetailRecordServiceImpl implements CallInDetailRecordService {
    @Override
    public void save(CallInDetailRecord callInDetailRecord) {
        
    }

    @Override
    public void update(CallInDetailRecord callInDetailRecord) {

    }

    @Override
    public void add(BigInteger callId, BigInteger callDetailId, String botWavFile) {

    }

    @Override
    public List<CallInDetailRecord> findByCallId(BigInteger callId) {
        return null;
    }
}
