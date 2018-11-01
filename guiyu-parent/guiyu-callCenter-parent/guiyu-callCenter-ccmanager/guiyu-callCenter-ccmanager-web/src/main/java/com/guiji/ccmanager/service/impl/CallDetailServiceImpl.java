package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.CallOutDetailRecordMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.vo.CallOutDetailVO;
import com.guiji.ccmanager.vo.CallOutPlanVO;
import com.guiji.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:32
 * @Description:
 */
@Service
public class CallDetailServiceImpl implements CallDetailService {

    @Autowired
    private CallOutPlanMapper callOutPlanMapper;

    @Autowired
    private CallOutDetailMapper callOutDetailMapper;

    @Autowired
    private CallOutDetailRecordMapper callOutDetailRecordMapper;

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

    @Override
    public CallOutPlanVO getCallDetail(String callId) {

        CallOutPlan callOutPlan = callOutPlanMapper.selectByPrimaryKey(callId);

        if(callOutPlan!=null){

            CallOutDetailExample example = new CallOutDetailExample();
            CallOutDetailExample.Criteria criteria = example.createCriteria();
            criteria.andCallIdEqualTo(callId);
            List<CallOutDetail> details = callOutDetailMapper.selectByExample(example);

            CallOutDetailRecordExample exampleRecord = new CallOutDetailRecordExample();
            CallOutDetailRecordExample.Criteria criteriaRecord = exampleRecord.createCriteria();
            criteriaRecord.andCallIdEqualTo(callId);
            List<CallOutDetailRecord> records = callOutDetailRecordMapper.selectByExample(exampleRecord);

            List<CallOutDetailVO> resList = new ArrayList<CallOutDetailVO>();
            for (CallOutDetail callOutDetail:details){
                CallOutDetailVO callOutDetailVO = new CallOutDetailVO();
                BeanUtil.copyProperties(callOutDetail,callOutDetailVO);
                for(CallOutDetailRecord callOutDetailRecord:records){
                    if(callOutDetailRecord.getCallDetailId() == callOutDetail.getCallDetailId() ){
                        BeanUtil.copyProperties(callOutDetailRecord,callOutDetailVO);
                    }
                }
                resList.add(callOutDetailVO);
            }

            CallOutPlanVO callOutPlanVO = new CallOutPlanVO();
            BeanUtil.copyProperties(callOutPlan,callOutPlanVO);
            callOutPlanVO.setDetailList(resList);
            return callOutPlanVO;
        }

        return null;
    }
}
