package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.CallOutDetailRecordMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.vo.CallOutDetailVO;
import com.guiji.ccmanager.vo.CallOutPlanVO;
import com.guiji.utils.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public CallOutPlanExample getExample(Date startDate, Date endDate, String customerId, String phoneNum,String durationMin, String durationMax,
                                         String accurateIntent, String freason,String callId, String tempId){
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        if(startDate!=null){
            criteria.andCallStartTimeGreaterThan(startDate);
        }
        if(endDate!=null){
            criteria.andCallStartTimeLessThan(endDate);
        }
        if(StringUtils.isNotBlank(customerId)){
            criteria.andCustomerIdEqualTo(customerId);
        }
        if(StringUtils.isNotBlank(phoneNum)){
            criteria.andPhoneNumEqualTo(phoneNum);
        }
        if(StringUtils.isNotBlank(durationMin)){
            criteria.andDurationGreaterThanOrEqualTo(Integer.valueOf(durationMin));
        }
        if(StringUtils.isNotBlank(durationMax)){
            criteria.andDurationLessThanOrEqualTo(Integer.valueOf(durationMax));
        }
        if(StringUtils.isNotBlank(accurateIntent)){
            if(accurateIntent.contains(",")){
                String[] arr = accurateIntent.split(",");
                criteria.andAccurateIntentIn(Arrays.asList(arr));
            }else{
                criteria.andAccurateIntentEqualTo(accurateIntent);
            }
        }
        if(StringUtils.isNotBlank(freason)){
            if(freason.contains(",")){
                String[] arr = freason.split(",");
                List<Integer> list = new ArrayList<Integer>();
                for(String s:arr){
                    list.add(Integer.parseInt(s));
                }
                criteria.andFreasonIn(list);
            }else{
                criteria.andFreasonEqualTo(Integer.parseInt(freason));
            }
        }
        if(StringUtils.isNotBlank(callId)){
            criteria.andCallIdEqualTo(callId);
        }
        if(StringUtils.isNotBlank(tempId)){
            criteria.andTempIdEqualTo(tempId);
        }
        return example;
    }

    @Override
    public List<CallOutPlan> callrecord(Date startDate, Date endDate, String customerId, int pageSize, int pageNo,String phoneNum,String durationMin, String durationMax,
                                        String accurateIntent, String freason,String callId, String tempId ){


        CallOutPlanExample example = getExample( startDate,  endDate,  customerId, phoneNum,  durationMin, durationMax,  accurateIntent,  freason, callId,  tempId);
        int limitStart = (pageNo-1)*pageSize;
        example.setLimitStart(limitStart);
        example.setLimitEnd(pageSize);

        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);
        return list;
    }

    @Override
    public int callrecordCount(Date startDate, Date endDate, String customerId, String phoneNum,String durationMin, String durationMax,
                               String accurateIntent, String freason,String callId, String tempId) {

        CallOutPlanExample example = getExample( startDate,  endDate,  customerId, phoneNum, durationMin, durationMax, accurateIntent,  freason, callId,  tempId);

        return callOutPlanMapper.countByExample(example);
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
                    if(callOutDetailRecord.getCallDetailId().equals(callOutDetail.getCallDetailId()) ){
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

    @Override
    public String getDialogue(String callId) {
        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdEqualTo(callId);
        example.setOrderByClause("bot_answer_time");
        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);
        String result="";
        if(list!=null && list.size()>0){
            for(CallOutDetail callOutDetail:list){
                String botSay = callOutDetail.getBotAnswerText();
                String customerSay = callOutDetail.getCustomerSayText();
                result += "机器人："+(botSay==null ? "":botSay)+"\r\n客户："+ (customerSay==null ? "":customerSay)+"\r\n";
            }
        }
        return  result;
    }

}
