package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailExample;
import com.guiji.calloutserver.service.CallOutDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        callOutDetailMapper.insertSelective(callOutDetail);
    }

    @Override
    public void save(CallOutDetail calloutdetail, String recordFile) {

    }

    @Override
    public CallOutDetail getLastDetail(String callId) {

        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdEqualTo(callId);
        criteria.andAccurateIntentIsNotNull();
        example.setOrderByClause("customer_say_time desc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
