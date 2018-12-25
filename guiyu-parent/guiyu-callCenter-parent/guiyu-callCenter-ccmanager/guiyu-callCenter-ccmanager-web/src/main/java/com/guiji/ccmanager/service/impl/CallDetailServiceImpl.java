package com.guiji.ccmanager.service.impl;

import ai.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.callcenter.dao.*;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.manager.CacheManager;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.vo.CallDetailUpdateReq;
import com.guiji.ccmanager.vo.CallOutDetailVO;
import com.guiji.ccmanager.vo.CallOutPlan4ListSelect;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.utils.BeanUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
    ErrorMatchMapper errorMatchMapper;

    @Autowired
    CacheManager cacheManager;

    @Override
    public void updateIsRead(String callId) {
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setCallId(callId);
        callOutPlan.setIsread(1);
        callOutPlanMapper.updateByPrimaryKeySelective(callOutPlan);
    }

    public CallOutPlanExample getExample(Date startDate, Date endDate, String customerId, String phoneNum, String durationMin, String durationMax,
                                         String accurateIntent, String freason, String callId, String tempId, String isRead,Boolean isSuperAdmin) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        if (startDate != null) {
            criteria.andCallStartTimeGreaterThan(startDate);
        }
        if (endDate != null) {
            criteria.andCallStartTimeLessThan(endDate);
        }
        if (StringUtils.isNotBlank(customerId) && !isSuperAdmin) {
            criteria.andCustomerIdEqualTo(customerId);
        }
        if (StringUtils.isNotBlank(phoneNum)) {
            criteria.andPhoneNumEqualTo(phoneNum);
        }
        if (StringUtils.isNotBlank(durationMin)) {
            criteria.andDurationGreaterThan(Integer.valueOf(durationMin));
        }
        if (StringUtils.isNotBlank(durationMax)) {
            criteria.andDurationLessThanOrEqualTo(Integer.valueOf(durationMax));
        }
        if (StringUtils.isNotBlank(accurateIntent)) {
            if (accurateIntent.contains(",")) {
                String[] arr = accurateIntent.split(",");
                criteria.andAccurateIntentIn(Arrays.asList(arr));
            } else {
                criteria.andAccurateIntentEqualTo(accurateIntent);
            }
        }
        if (StringUtils.isNotBlank(freason)) {
            if (freason.contains(",")) {
                String[] arr = freason.split(",");
                criteria.andReasonIn(Arrays.asList(arr));
            } else {
                criteria.andReasonEqualTo(freason);
            }
        }
        if (StringUtils.isNotBlank(callId)) {
            criteria.andCallIdEqualTo(callId);
        }
        if (StringUtils.isNotBlank(tempId)) {
            criteria.andTempIdEqualTo(tempId);
        }
        if (StringUtils.isNotBlank(isRead)) {
            criteria.andIsreadEqualTo(Integer.valueOf(isRead));
        }
        criteria.andIsdelEqualTo(0);
        criteria.andCallStateGreaterThanOrEqualTo(Constant.CALLSTATE_HANGUP_OK);
        return example;
    }

    @Override
    public List<CallOutPlan4ListSelect> callrecord(Date startDate, Date endDate,Boolean isSuperAdmin, String customerId, int pageSize, int pageNo, String phoneNum, String durationMin, String durationMax,
                                                   String accurateIntent, String freason, String callId, String tempId, String isRead) {


        CallOutPlanExample example = getExample(startDate, endDate, customerId, phoneNum, durationMin, durationMax, accurateIntent, freason, callId, tempId, isRead, isSuperAdmin);
        int limitStart = (pageNo - 1) * pageSize;
        example.setLimitStart(limitStart);
        example.setLimitEnd(pageSize);
        example.setOrderByClause("create_time desc");

        List<CallOutPlan> list;
        if(isSuperAdmin){
            example.setCustomerId(customerId);
            list = callOutPlanMapper.selectByExample4Encrypt(example);
        }else{
            list = callOutPlanMapper.selectByExample(example);
        }

        List<CallOutPlan4ListSelect> listResult = new ArrayList<CallOutPlan4ListSelect>();

        if (list != null && list.size() > 0) {
            for (CallOutPlan callOutPlan : list) {
                CallOutPlan4ListSelect callOutPlan4ListSelect = new CallOutPlan4ListSelect();
                BeanUtil.copyProperties(callOutPlan, callOutPlan4ListSelect);

                callOutPlan4ListSelect.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
                callOutPlan4ListSelect.setUserName(cacheManager.getUserName(callOutPlan.getCustomerId()));

                listResult.add(callOutPlan4ListSelect);
            }
        }

        return listResult;
    }

    @Override
    public int callrecordCount(Date startDate, Date endDate, String customerId, String phoneNum, String durationMin, String durationMax,
                               String accurateIntent, String freason, String callId, String tempId, String isRead, Boolean isSuperAdmin) {

        CallOutPlanExample example = getExample(startDate, endDate, customerId, phoneNum, durationMin, durationMax, accurateIntent, freason, callId, tempId, isRead, isSuperAdmin);

        return callOutPlanMapper.countByExample(example);
    }

    @Override
    public CallPlanDetailRecordVO getCallDetail(String callId) {

        CallOutPlan callOutPlan = callOutPlanMapper.selectByPrimaryKey(callId);
        callOutPlan.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
        if (callOutPlan != null) {

            CallOutDetailExample example = new CallOutDetailExample();
            CallOutDetailExample.Criteria criteria = example.createCriteria();
            criteria.andCallIdEqualTo(callId);
            example.setOrderByClause("IF(ISNULL(bot_answer_time),customer_say_time,bot_answer_time)");
            List<CallOutDetail> details = callOutDetailMapper.selectByExample(example);

            CallOutDetailRecordExample exampleRecord = new CallOutDetailRecordExample();
            CallOutDetailRecordExample.Criteria criteriaRecord = exampleRecord.createCriteria();
            criteriaRecord.andCallIdEqualTo(callId);
            List<CallOutDetailRecord> records = callOutDetailRecordMapper.selectByExample(exampleRecord);

            List<CallOutDetailVO> resList = new ArrayList<CallOutDetailVO>();
            for (CallOutDetail callOutDetail : details) {
                CallOutDetailVO callOutDetailVO = new CallOutDetailVO();
                BeanUtil.copyProperties(callOutDetail, callOutDetailVO);
                for (CallOutDetailRecord callOutDetailRecord : records) {
                    if (callOutDetailRecord.getCallDetailId().equals(callOutDetail.getCallDetailId())) {
                        BeanUtil.copyProperties(callOutDetailRecord, callOutDetailVO);
                    }
                }
                resList.add(callOutDetailVO);
            }

            CallPlanDetailRecordVO callPlanDetailRecordVO = new CallPlanDetailRecordVO();
            BeanUtil.copyProperties(callOutPlan, callPlanDetailRecordVO);
            callPlanDetailRecordVO.setDetailList(resList);
            return callPlanDetailRecordVO;
        }

        return null;
    }

    @Override
    public List<CallPlanDetailRecordVO> getCallPlanDetailRecord(List<String> callIds) {

        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdIn(callIds);
        List<CallOutPlan> listPlan = callOutPlanMapper.selectByExample(example);

        if (listPlan != null && listPlan.size() > 0) {

            //获取CallOutRecord列表
            CallOutRecordExample recordExample = new CallOutRecordExample();
            recordExample.createCriteria().andCallIdIn(callIds);
            List<CallOutRecord> recordList = callOutRecordMapper.selectByExample(recordExample);

            //获取CallOutDetail列表
            CallOutDetailExample exampleDetail = new CallOutDetailExample();
            exampleDetail.createCriteria().andCallIdIn(callIds);
            exampleDetail.setOrderByClause("call_id,bot_answer_time asc");
            List<CallOutDetail> details = callOutDetailMapper.selectByExample(exampleDetail);

            //获取CallOutDetailRecord列表
            CallOutDetailRecordExample exampleRecord = new CallOutDetailRecordExample();
            exampleRecord.createCriteria().andCallIdIn(callIds);
            List<CallOutDetailRecord> records = callOutDetailRecordMapper.selectByExample(exampleRecord);

            //CallOutDetailRecord属性加到callOutDetail上
            List<CallOutDetailVO> detailList = new ArrayList<CallOutDetailVO>();
            if (details != null && details.size() > 0) {
                for (CallOutDetail callOutDetail : details) {
                    CallOutDetailVO callOutDetailVO = new CallOutDetailVO();
                    BeanUtil.copyProperties(callOutDetail, callOutDetailVO);
                    if (records != null && records.size() > 0) {
                        for (CallOutDetailRecord callOutDetailRecord : records) {
                            if (callOutDetailRecord.getCallDetailId().equals(callOutDetail.getCallDetailId())) {
                                BeanUtil.copyProperties(callOutDetailRecord, callOutDetailVO);
                            }
                        }
                    }
                    detailList.add(callOutDetailVO);
                }
            }

            //detailList加到resList的DetailList属性上,record也加上
            List<CallPlanDetailRecordVO> resList = new ArrayList<CallPlanDetailRecordVO>();
            for (CallOutPlan callOutPlan : listPlan) {
                CallPlanDetailRecordVO callPlanDetailRecordVO = new CallPlanDetailRecordVO();
                BeanUtil.copyProperties(callOutPlan, callPlanDetailRecordVO);
                if (detailList != null && detailList.size() > 0) {
                    for (CallOutDetailVO callOutDetailVO : detailList) {
                        if (callOutDetailVO.getCallId().equals(callOutPlan.getCallId())) {
                            List<CallOutDetailVO> detailIn = callPlanDetailRecordVO.getDetailList();
                            if (detailIn != null) {
                                detailIn.add(callOutDetailVO);
                            } else {
                                List<CallOutDetailVO> detailInNew = new ArrayList<CallOutDetailVO>();
                                detailInNew.add(callOutDetailVO);
                                callPlanDetailRecordVO.setDetailList(detailInNew);
                            }
                        }
                    }
                }

                if (recordList != null && recordList.size() > 0) {
                    for (CallOutRecord callOutRecord : recordList) {
                        if (callPlanDetailRecordVO.getCallId().equals(callOutRecord.getCallId())) {
                            callPlanDetailRecordVO.setRecordUrl(callOutRecord.getRecordUrl());
                        }
                    }
                }
                resList.add(callPlanDetailRecordVO);
            }

            return resList;
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
        String result = "";
        if (list != null && list.size() > 0) {
            for (CallOutDetail callOutDetail : list) {
                String botSay = callOutDetail.getBotAnswerText();
                String customerSay = callOutDetail.getCustomerSayText();
                result += getContext(botSay, customerSay);
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getDialogues(String callIds) {
        CallOutDetailExample example = new CallOutDetailExample();
        String[] callidArr = callIds.split(",");
        example.createCriteria().andCallIdIn(Arrays.asList(callidArr));
        example.setOrderByClause("call_id,bot_answer_time");

        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);

        Map<String, String> map = new HashMap<String, String>();
        if (list != null && list.size() > 0) {
            for (CallOutDetail callOutDetail : list) {
                String callId = callOutDetail.getCallId();

                String botSay = callOutDetail.getBotAnswerText();
                String customerSay = callOutDetail.getCustomerSayText();

                if (map.get(callId) == null) {
                    String result = getContext(botSay, customerSay);
                    map.put(callId, result);
                } else {
                    String result = map.get(callId);
                    result += getContext(botSay, customerSay);
                    map.put(callId, result);
                }
            }
        }
        return map;
    }

    private String getContext(String botSay, String customerSay) {
        if (customerSay == null) {
            customerSay = "";
        }
        if (botSay == null) {
            botSay = "";
        }
        if (StringUtils.isBlank(botSay)) {
            return "客户：" + customerSay + "\r\n";
        } else if (StringUtils.isBlank(customerSay)) {
            return "机器人：" + botSay + "\r\n";
        } else {
            return "机器人：" + botSay + "\r\n客户：" + customerSay + "\r\n";
        }
    }


    @Override
    public String getRecordFileUrl(String callId) {
        CallOutRecord callOutRecord = callOutRecordMapper.selectByPrimaryKey(callId);
        if (callOutRecord != null) {
            return callOutRecord.getRecordUrl();
        }
        return null;
    }

    @Override
    public List<CallOutRecord> getRecords(String callIds) {

        String[] callidArr = callIds.split(",");
        CallOutRecordExample example = new CallOutRecordExample();
        example.createCriteria().andCallIdIn(Arrays.asList(callidArr));
        return callOutRecordMapper.selectByExample(example);

    }

    @Override
    public void delRecord(String callIds) {
        String[] callidArr = callIds.split(",");
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setIsdel(1);
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdIn(Arrays.asList(callidArr));
        callOutPlanMapper.updateByExampleSelective(callOutPlan, example);
    }

    @Override
    public List<String> getFtypes() {
        return errorMatchMapper.selectDistinctErrorName();
    }

    @Override
    public void updateCallDetailCustomerSayText(CallDetailUpdateReq callDetailUpdateReq) {
        CallOutDetail record = new CallOutDetail();
        record.setCustomerSayText(callDetailUpdateReq.getCustomerSayText());
        record.setCallDetailId(callDetailUpdateReq.getCallDetailId());
        callOutDetailMapper.updateByPrimaryKeySelective(record);
    }


}
