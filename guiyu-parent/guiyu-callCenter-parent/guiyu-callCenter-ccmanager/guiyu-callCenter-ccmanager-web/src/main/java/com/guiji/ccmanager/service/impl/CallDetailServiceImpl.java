package com.guiji.ccmanager.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.CallOutDetailRecordMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.CallOutRecordMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.vo.CallOutDetailVO;
import com.guiji.ccmanager.vo.CallOutPlan4ListSelect;
import com.guiji.ccmanager.vo.CallOutPlanVO;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:32
 * @Description:
 */
@Slf4j
@Service
public class CallDetailServiceImpl implements CallDetailService {

    @Autowired
    private CallOutPlanMapper callOutPlanMapper;

    @Autowired
    private CallOutDetailMapper callOutDetailMapper;

    @Autowired
    private CallOutDetailRecordMapper callOutDetailRecordMapper;

    @Autowired
    CallOutRecordMapper callOutRecordMapper;

    @Autowired
    IAuth auth;

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
        criteria.andIsdelEqualTo(0);
        return example;
    }

    @Override
    public List<CallOutPlan4ListSelect> callrecord(Date startDate, Date endDate, String customerId, int pageSize, int pageNo, String phoneNum, String durationMin, String durationMax,
                                                   String accurateIntent, String freason, String callId, String tempId ){


        CallOutPlanExample example = getExample( startDate,  endDate,  customerId, phoneNum,  durationMin, durationMax,  accurateIntent,  freason, callId,  tempId);
        int limitStart = (pageNo-1)*pageSize;
        example.setLimitStart(limitStart);
        example.setLimitEnd(pageSize);
        example.setOrderByClause("call_start_time desc");

        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);

        List<CallOutPlan4ListSelect> listResult = new ArrayList<CallOutPlan4ListSelect>();

        Map<String,String> map = new HashMap<String,String>();

        if(list!=null && list.size()>0){
            for(CallOutPlan callOutPlan:list){
                CallOutPlan4ListSelect callOutPlan4ListSelect = new CallOutPlan4ListSelect();
                BeanUtil.copyProperties(callOutPlan,callOutPlan4ListSelect);
                String userId = callOutPlan.getCustomerId();
                if(map.get(userId)==null){
                    try {
                        Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
                        if(result!=null && result.getBody()!=null) {
                            String userName = result.getBody().getUsername();
                            if (userName != null) {
                                map.put(userId, userName);
                                callOutPlan4ListSelect.setUserName(userName);
                            }
                        }
                    }catch (Exception e){
                        log.error(" auth.getUserById error :"+ e);
                    }

                }else{
                    callOutPlan4ListSelect.setUserName(map.get(userId));
                }
                listResult.add(callOutPlan4ListSelect);
            }
        }

        return listResult;
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
            example.setOrderByClause("bot_answer_time asc");
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
                result += getContext(botSay, customerSay);
            }
        }
        return  result;
    }

    @Override
    public Map<String, String> getDialogues(String callIds) {
        CallOutDetailExample example = new CallOutDetailExample();
        String[] callidArr = callIds.split(",");
        example.createCriteria().andCallIdIn(Arrays.asList(callidArr));
        example.setOrderByClause("call_id,bot_answer_time");

        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);

        Map<String,String> map = new HashMap<String,String>();
        if(list!=null && list.size()>0){
            for(CallOutDetail callOutDetail:list){
                String callId = callOutDetail.getCallId();

                String botSay = callOutDetail.getBotAnswerText();
                String customerSay = callOutDetail.getCustomerSayText();

                if(map.get(callId)== null){
                    String result = getContext(botSay, customerSay);
                    map.put(callId,result);
                }else{
                    String result=map.get(callId);
                    result += getContext(botSay, customerSay);
                    map.put(callId,result);
                }
            }
        }
        return  map;
    }

    private String getContext(String botSay,String customerSay){
        if(StringUtils.isBlank(botSay)){
           return  "客户："+ (customerSay==null ? "":customerSay)+"\r\n";
        }else if(StringUtils.isBlank(customerSay)){
            return "机器人："+(botSay==null ? "":botSay)+"\r\n";
        }else{
            return  "机器人："+(botSay==null ? "":botSay)+"\r\n客户："+ (customerSay==null ? "":customerSay)+"\r\n";
        }
    }



    @Override
    public String getRecordFileUrl(String callId) {
        CallOutRecord callOutRecord = callOutRecordMapper.selectByPrimaryKey(callId);
        if(callOutRecord!=null){
            return callOutRecord.getRecordUrl();
        }
        return null;
    }

    @Override
    public List<CallOutRecord> getRecords(String callIds) {

        String[] callidArr = callIds.split(",");
        CallOutRecordExample example = new CallOutRecordExample();
        example.createCriteria().andCallIdIn(Arrays.asList(callidArr));
        return  callOutRecordMapper.selectByExample(example);

    }

    @Override
    public void delRecord(String callIds) {
        String[] callidArr = callIds.split(",");
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setIsdel(1);
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdIn(Arrays.asList(callidArr));
        callOutPlanMapper.updateByExampleSelective(callOutPlan,example);
    }
}
