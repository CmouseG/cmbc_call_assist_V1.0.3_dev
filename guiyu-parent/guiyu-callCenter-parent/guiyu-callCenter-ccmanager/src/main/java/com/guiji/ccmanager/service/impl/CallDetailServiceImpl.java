package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.ccmanager.service.CallDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:32
 * @Description:
 */
public class CallDetailServiceImpl implements CallDetailService {

    @Autowired
    private CallOutPlanMapper callOutPlanMapper;

    @Override
    public List<CallOutPlan> callrecord(Date startDate, Date endDate, String customerId){

        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        if(startDate!=null){
            criteria.andCreateTimeGreaterThan(startDate);
        }
        if(endDate!=null){
            criteria.andCreateTimeGreaterThan(endDate);
        }
        if(customerId!=null){
            criteria.andCustomerIdEqualTo(customerId);
        }
        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);
        return list;
    }
}
