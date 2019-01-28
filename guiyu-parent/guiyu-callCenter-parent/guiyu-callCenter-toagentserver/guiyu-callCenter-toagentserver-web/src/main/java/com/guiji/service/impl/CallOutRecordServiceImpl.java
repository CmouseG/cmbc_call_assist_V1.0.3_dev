package com.guiji.service.impl;

import com.guiji.callcenter.dao.CallOutRecordMapper;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.service.CallOutRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 16:56
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallOutRecordServiceImpl implements CallOutRecordService {
    @Autowired
    CallOutRecordMapper callOutRecordMapper;

    @Override
    public CallOutRecord findByCallId(BigInteger callId) {
        return callOutRecordMapper.selectByPrimaryKey(callId);
    }

    @Override
    public void save(CallOutRecord callOutRecord) {
        callOutRecordMapper.insert(callOutRecord);
    }

    @Override
    public void update(CallOutRecord callOutRecord) {
        callOutRecordMapper.updateByPrimaryKeySelective(callOutRecord);
    }
}
