package com.guiji.service.impl;

import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.service.CallInRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/16 19:45
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallInRecordServiceImpl implements CallInRecordService {
    @Override
    public CallOutRecord findByCallId(BigInteger callId) {
        return null;
    }

    @Override
    public void save(CallOutRecord callOutRecord) {

    }

    @Override
    public void update(CallOutRecord callOutRecord) {

    }
}
