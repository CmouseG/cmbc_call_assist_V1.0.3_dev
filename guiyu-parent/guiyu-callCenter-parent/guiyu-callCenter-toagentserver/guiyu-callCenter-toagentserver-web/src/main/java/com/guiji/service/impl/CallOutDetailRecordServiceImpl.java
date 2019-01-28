package com.guiji.service.impl;

import com.guiji.callcenter.dao.CallOutDetailRecordMapper;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutDetailRecordExample;
import com.guiji.service.CallOutDetailRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 17:31
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallOutDetailRecordServiceImpl implements CallOutDetailRecordService {
    @Autowired
    CallOutDetailRecordMapper callOutDetailRecordMapper;

    @Override
    public void save(CallOutDetailRecord callOutDetailRecord) {
        callOutDetailRecordMapper.insertSelective(callOutDetailRecord);
    }
    @Override
    public void update(CallOutDetailRecord callOutDetailRecord) {
        callOutDetailRecordMapper.updateByPrimaryKeySelective(callOutDetailRecord);
    }

    @Override
    public void add(BigInteger callId, BigInteger callDetailId, String botWavFile) {
        CallOutDetailRecord record = new CallOutDetailRecord();
        record.setCallId(callId);
        record.setCallDetailId(callDetailId);
        record.setBotRecordFile(botWavFile);
        callOutDetailRecordMapper.insertSelective(record);
    }

    @Override
    public List<CallOutDetailRecord> findByCallId(BigInteger callId) {
        CallOutDetailRecordExample recordExample = new CallOutDetailRecordExample();
        CallOutDetailRecordExample.Criteria criteria = recordExample.createCriteria();
        criteria.andCallIdEqualTo(callId);

        return callOutDetailRecordMapper.selectByExample(recordExample);
    }
}
