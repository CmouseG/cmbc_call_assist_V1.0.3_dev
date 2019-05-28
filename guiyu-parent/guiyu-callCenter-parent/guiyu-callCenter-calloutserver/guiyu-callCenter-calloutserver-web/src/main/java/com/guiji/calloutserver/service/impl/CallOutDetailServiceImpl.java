package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailExample;
import com.guiji.calloutserver.service.CallOutDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 15:36
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallOutDetailServiceImpl implements CallOutDetailService {
    @Autowired
    CallOutDetailMapper callOutDetailMapper;

    @Override
    public void save(CallOutDetail callOutDetail) {
        //设置分表字段的值
//        callOutDetail.setShardingValue(new Random().nextInt(100));
        //处理  意向标签  accurate_intent":"D*有效对话轮数:0</br>未命中关键词

        String intent = callOutDetail.getAccurateIntent();
        if (intent != null) {
            if (intent.contains("*")) {
                String subIntent = intent.substring(0, 1);
                String subReason = intent.substring(2);
                callOutDetail.setAccurateIntent(subIntent);
                callOutDetail.setReason(subReason);
            }
        }
        if(callOutDetail.getReason()!=null && callOutDetail.getReason().length()>=500){
            callOutDetail.setReason(callOutDetail.getReason().substring(0,499));
        }

        callOutDetailMapper.insertSelective(callOutDetail);
    }

    @Override
    public void update(CallOutDetail callOutDetail) {
        callOutDetailMapper.updateByPrimaryKeySelective(callOutDetail);
    }


    @Override
    public void save(CallOutDetail calloutdetail, String recordFile) {

    }
    @Override
    public CallOutDetail getLastDetail(String callId,Integer orgId) {

        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdEqualTo(new BigInteger(callId))
                .andOrgIdEqualTo(orgId)
                .andAccurateIntentIsNotNull();
        example.setOrderByClause("bot_answer_time desc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public CallOutDetail getLastCustomerDetail(BigInteger callId, Integer orgId) {

        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdEqualTo(callId)
                .andOrgIdEqualTo(orgId)
                .andCustomerSayTimeIsNotNull();
        example.setOrderByClause("customer_say_time desc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer getTalkNum(BigInteger callId, Integer orgId) {
        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdEqualTo(callId);
        criteria.andOrgIdEqualTo(orgId);
        criteria.andBotAnswerTimeIsNotNull();
        return callOutDetailMapper.countByExample(example);
    }
}
