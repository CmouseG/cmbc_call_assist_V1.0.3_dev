package com.guiji.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.CallOutRecordMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.callcenter.dao.entity.CallOutRecordExample;
import com.guiji.service.CallOutRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

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
    @Autowired
    CallOutPlanMapper callOutPlanMapper;
    @Override
    public CallOutRecord findByRecordId(String recordId) {
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        callOutPlanExample.createCriteria().andPlanUuidEqualTo(recordId);
        List<CallOutPlan> list =callOutPlanMapper.selectByExample(callOutPlanExample);
        if(list.size()>0){
            CallOutPlan callOutPlan =list.get(0);
            return callOutRecordMapper.selectByPrimaryKey(callOutPlan.getCallId());
        }
       return null;
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
