package com.guiji.service.impl;

import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailExample;
import com.guiji.service.CallOutDetailService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class CallOutDetailServiceImpl implements CallOutDetailService {
    @Autowired
    CallOutDetailMapper callOutDetailMapper;

    @Override
    public void insertAll(List<CallOutDetail> callOutDetails){
        for (CallOutDetail detail:callOutDetails) {
            callOutDetailMapper.insert(detail);
        }
    }

    @Override
    public CallOutDetail insert(CallOutDetail detail){
        log.info("准备更新callDetail[{}]", detail);
//        Random random = new Random();
//        detail.setShardingValue(random.nextInt(100));
        callOutDetailMapper.insert(detail);
        return detail;
    }

    @Override
    public List<CallOutDetail> findByCallPlanId(String callPlanId,Integer orgId){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(callPlanId), "null callPlanId");
        CallOutDetailExample callOutDetailExample = new CallOutDetailExample();
        callOutDetailExample.createCriteria()
                .andCallIdEqualTo(new BigInteger(callPlanId))
                .andOrgIdEqualTo(orgId);
        callOutDetailExample.setOrderByClause("IF(ISNULL(bot_answer_time),customer_say_time,bot_answer_time)");

        return callOutDetailMapper.selectByExample(callOutDetailExample);
    }
}
