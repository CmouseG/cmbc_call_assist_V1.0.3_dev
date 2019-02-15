package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.*;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.manager.CacheManager;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.vo.*;
import com.guiji.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
    @Autowired
    AuthService authService;
    @Autowired
    CallOutDetailLogMapper callOutDetailLogMapper;
    @Autowired
    AgentMapper agentMapper;

    @Override
    public void updateIsRead(String callId) {
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setCallId(new BigInteger(callId));
        callOutPlan.setIsread(1);
        callOutPlanMapper.updateByPrimaryKeySelective(callOutPlan);
    }

    public CallOutPlanExample getExample(Date startDate, Date endDate, String customerId,String orgCode, String phoneNum, String durationMin, String durationMax,
                                         String accurateIntent, String freason, String callId, String tempId, String isRead,Boolean isSuperAdmin) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        if (startDate != null) {
            criteria.andCallStartTimeGreaterThan(startDate);
        }
        if (endDate != null) {
            criteria.andCallStartTimeLessThan(endDate);
        }
        if(!isSuperAdmin){//不是管理员
            long userId = Long.valueOf(customerId);
            if (authService.isAgentOrCompanyAdmin(userId) ) {//代理商 或者企业管理员
                criteria.andOrgCodeLike(orgCode+"%");
            } else if(authService.isSeat(userId)){//客服
                String userName = authService.getUserName(userId);
                AgentExample agentExample = new AgentExample();
                agentExample.createCriteria().andCrmLoginIdEqualTo(userName);
                List<Agent> listAgent = agentMapper.selectByExample(agentExample);
                Long agentId = listAgent.get(0).getUserId();
                criteria.andAgentIdEqualTo(String.valueOf(agentId));
            }else {
                criteria.andCustomerIdEqualTo(Integer.valueOf(customerId));
            }
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
            criteria.andCallIdEqualTo(new BigInteger(callId));
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
    public List<Map> getCallRecordList(CallRecordReq callRecordReq) {

        Map map = new HashMap();
        if (callRecordReq.getUserId() != null)
            map.put("customerId", callRecordReq.getUserId());
        if (callRecordReq.getIsDesensitization() != null)
            map.put("isDesensitization", callRecordReq.getIsDesensitization());
        if (callRecordReq.getOrgCode() != null)
            map.put("orgCode", callRecordReq.getOrgCode());
        map.put("limitStart", (callRecordReq.getPageNo() - 1) * callRecordReq.getPageSize());
        map.put("limitEnd", callRecordReq.getPageSize());
        if (callRecordReq.getTime() != null)
            map.put("time", callRecordReq.getTime() - 1);
        if (callRecordReq.getAccurateIntent() != null)
            map.put("accurateIntent", callRecordReq.getAccurateIntent());
        List<Map> list = callOutPlanMapper.selectCallPlanRecord4Encrypt(map);
        return list;
    }

    @Override
    public int countCallRecordList(CallRecordReq callRecordReq) {

        Map map = new HashMap();
        if (callRecordReq.getUserId() != null)
            map.put("customerId", callRecordReq.getUserId());
        if (callRecordReq.getOrgCode() != null)
            map.put("orgCode", callRecordReq.getOrgCode());
        if (callRecordReq.getTime() != null)
            map.put("time", callRecordReq.getTime()-1);
        if (callRecordReq.getAccurateIntent() != null)
            map.put("accurateIntent", callRecordReq.getAccurateIntent());
        int i = callOutPlanMapper.countCallRecordList(map);
        return i;
    }

    @Override
    public List<CallOutPlan4ListSelect> callrecord(Date startDate, Date endDate,Boolean isSuperAdmin, String customerId, String orgCode,
                                                   int pageSize, int pageNo, String phoneNum, String durationMin, String durationMax,
                                                   String accurateIntent, String freason, String callId, String tempId, String isRead, Integer isDesensitization) {

        CallOutPlanExample example = getExample(startDate, endDate, customerId, orgCode, phoneNum, durationMin,
                durationMax, accurateIntent, freason, callId, tempId, isRead, isSuperAdmin);
        int limitStart = (pageNo - 1) * pageSize;
        example.setLimitStart(limitStart);
        example.setLimitEnd(pageSize);
        example.setOrderByClause("create_time desc");

        Long userId = Long.valueOf(customerId);
        List<CallOutPlan> list;
//        if(isSuperAdmin || authService.isSeatOrAgent(userId)){
            example.setIsDesensitization(isDesensitization);
            list = callOutPlanMapper.selectByExample4Encrypt(example);
//        }else{
//            list = callOutPlanMapper.selectByExample(example);
//        }

        List<CallOutPlan4ListSelect> listResult = new ArrayList<CallOutPlan4ListSelect>();

        if (list != null && list.size() > 0) {
            for (CallOutPlan callOutPlan : list) {
                CallOutPlan4ListSelect callOutPlan4ListSelect = new CallOutPlan4ListSelect();
                BeanUtil.copyProperties(callOutPlan, callOutPlan4ListSelect);

                callOutPlan4ListSelect.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
                callOutPlan4ListSelect.setUserName(cacheManager.getUserName(callOutPlan.getCustomerId()));
                callOutPlan4ListSelect.setCallId(callOutPlan.getCallId().toString());
                listResult.add(callOutPlan4ListSelect);
            }
        }

        return listResult;
    }

    @Override
    public int callrecordCount(Date startDate, Date endDate, Boolean isSuperAdmin, String customerId, String orgCode, String phoneNum, String durationMin, String durationMax,
                               String accurateIntent, String freason, String callId, String tempId, String isRead) {

        CallOutPlanExample example = getExample(startDate, endDate, customerId, orgCode,
                phoneNum, durationMin, durationMax, accurateIntent, freason, callId, tempId, isRead, isSuperAdmin);

        return callOutPlanMapper.countByExample(example);
    }

    @Override
    public CallPlanDetailRecordVO getCallDetail(BigInteger callId) {

        CallOutPlan callOutPlan = callOutPlanMapper.selectByPrimaryKey(callId);
        CallOutRecord callOutRecord = callOutRecordMapper.selectByPrimaryKey(callId);
        callOutPlan.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
        if (callOutPlan != null) {

            CallOutDetailExample example = new CallOutDetailExample();
            CallOutDetailExample.Criteria criteria = example.createCriteria();
            criteria.andCallIdEqualTo(callId);
            example.setOrderByClause("IF(ISNULL(bot_answer_time),IF(ISNULL(agent_answer_time),customer_say_time,agent_answer_time),bot_answer_time)");
            List<CallOutDetail> details = callOutDetailMapper.selectByExample(example);

            CallOutDetailRecordExample exampleRecord = new CallOutDetailRecordExample();
            CallOutDetailRecordExample.Criteria criteriaRecord = exampleRecord.createCriteria();
            criteriaRecord.andCallIdEqualTo(callId);
            List<CallOutDetailRecord> records = callOutDetailRecordMapper.selectByExample(exampleRecord);

            List<CallOutDetailVO> resList = new ArrayList<CallOutDetailVO>();
//            for (CallOutDetail callOutDetail : details) {
            for (int i=0; i<details.size(); i++) { //加上无声音的判断
                CallOutDetail callOutDetail = details.get(i);
                CallOutDetailVO callOutDetailVO = new CallOutDetailVO();
                BeanUtil.copyProperties(callOutDetail, callOutDetailVO);
                callOutDetailVO.setCallDetailId(callOutDetail.getCallDetailId().toString());
                for (CallOutDetailRecord callOutDetailRecord : records) {
                    if (callOutDetailRecord.getCallDetailId().equals(callOutDetail.getCallDetailId())) {
                        BeanUtil.copyProperties(callOutDetailRecord, callOutDetailVO);
                    }
                }

                if(callOutDetail.getBotAnswerTime()!=null && i>0){
                    CallOutDetail callOutDetailBefore = details.get(i-1);
                    if(callOutDetailBefore.getBotAnswerTime()!=null){//出现连续2个机器人说话，中间插入一个无声音
                        CallOutDetailVO callOutDetailVOInsert = new CallOutDetailVO();
                        callOutDetailVOInsert.setCustomerSayText("无声音");
                        callOutDetailVOInsert.setCustomerSayTime(callOutDetailBefore.getBotAnswerTime());
                        resList.add(callOutDetailVOInsert);
                    }
                }

                if(callOutDetail.getAgentAnswerTime()!=null && i>0){
                    CallOutDetail callOutDetailBefore = details.get(i-1);
                    if(callOutDetailBefore.getAgentAnswerTime()!=null){//出现连续2个坐席说话，中间插入一个无声音
                        CallOutDetailVO callOutDetailVOInsert = new CallOutDetailVO();
                        callOutDetailVOInsert.setCustomerSayText("无声音");
                        callOutDetailVOInsert.setCustomerSayTime(callOutDetailBefore.getAgentAnswerTime());
                        resList.add(callOutDetailVOInsert);
                    }
                }

                resList.add(callOutDetailVO);
            }

            CallPlanDetailRecordVO callPlanDetailRecordVO = new CallPlanDetailRecordVO();
            BeanUtil.copyProperties(callOutPlan, callPlanDetailRecordVO);
            callPlanDetailRecordVO.setCallId(callOutPlan.getCallId().toString());
            callPlanDetailRecordVO.setDetailList(resList);
            if(callOutRecord!=null && callOutRecord.getRecordUrl()!=null){
                callPlanDetailRecordVO.setRecordUrl(callOutRecord.getRecordUrl());
            }

            return callPlanDetailRecordVO;
        }

        return null;
    }

    @Override
    public List<CallPlanDetailRecordVO> getCallPlanDetailRecord(List<String> uuids) {

        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andPlanUuidIn(uuids);
        List<CallOutPlan> listPlan = callOutPlanMapper.selectByExample(example);

        List<BigInteger> callIds = new ArrayList<>();
        if (listPlan != null && listPlan.size() > 0) {
            for(CallOutPlan callOutPlan:listPlan){
                callIds.add(callOutPlan.getCallId());
            }
        }

        if (callIds != null && callIds.size() > 0) {

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
                    callOutDetailVO.setCallDetailId(callOutDetail.getCallDetailId().toString());
                    if (records != null && records.size() > 0) {
                        for (CallOutDetailRecord callOutDetailRecord : records) {
                            if (callOutDetailRecord.getCallDetailId().compareTo(callOutDetail.getCallDetailId())==0) {
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
                callPlanDetailRecordVO.setCallId(callOutPlan.getCallId().toString());
                if (detailList != null && detailList.size() > 0) {
                    for (CallOutDetailVO callOutDetailVO : detailList) {
                        if (callOutDetailVO.getCallId().compareTo(callOutPlan.getCallId())==0) {
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
                        if (callPlanDetailRecordVO.getCallId().equals(callOutRecord.getCallId().toString())) {
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
        criteria.andCallIdEqualTo(new BigInteger(callId));
        example.setOrderByClause("IF(ISNULL(bot_answer_time),IF(ISNULL(agent_answer_time),customer_say_time,agent_answer_time),bot_answer_time)");
        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);
        String result = "";
        if (list != null && list.size() > 0) {
            for (CallOutDetail callOutDetail: list) {
                String botSay = callOutDetail.getBotAnswerText();
                String customerSay = callOutDetail.getCustomerSayText();
                String agentSay = callOutDetail.getAgentAnswerText();
                result += getContext(botSay, customerSay, agentSay);
            }
        }
        return result;
    }

    @Override
    public List<CallOutPlan4ListSelect> getCallPlanList(List<BigInteger> idList, Long userID, Boolean isSuperAdmin, Integer isDesensitization) {

        String customerId = String.valueOf(userID);
        List<CallOutPlan> list;
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdIn(idList);
        example.setOrderByClause("create_time desc");
//        if(isSuperAdmin || authService.isAgent(Long.valueOf(customerId))){
            example.setIsDesensitization(isDesensitization);
            list = callOutPlanMapper.selectByExample4Encrypt(example);
//        }else{
//            list = callOutPlanMapper.selectByExample(example);
//        }

        List<CallOutPlan4ListSelect> listResult = new ArrayList<CallOutPlan4ListSelect>();

        if (list != null && list.size() > 0) {
            for (CallOutPlan callOutPlan : list) {
                CallOutPlan4ListSelect callOutPlan4ListSelect = new CallOutPlan4ListSelect();
                BeanUtil.copyProperties(callOutPlan, callOutPlan4ListSelect);

                callOutPlan4ListSelect.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
                callOutPlan4ListSelect.setUserName(cacheManager.getUserName(callOutPlan.getCustomerId()));
                callOutPlan4ListSelect.setCallId(callOutPlan.getCallId().toString());
                listResult.add(callOutPlan4ListSelect);
            }
        }

        return listResult;
    }

    @Override
    public Map<String, String> getDialogues(List<BigInteger> idList) {
        CallOutDetailExample example = new CallOutDetailExample();

        example.createCriteria().andCallIdIn(idList);
        example.setOrderByClause("IF(ISNULL(bot_answer_time),IF(ISNULL(agent_answer_time),customer_say_time,agent_answer_time),bot_answer_time)");

        List<CallOutDetail> list = callOutDetailMapper.selectByExample(example);



        Map<String, String> map = new HashMap<String, String>();
        if (list != null && list.size() > 0) {
            for (CallOutDetail callOutDetail : list) {
                BigInteger callId = callOutDetail.getCallId();

                String botSay = callOutDetail.getBotAnswerText();
                String customerSay = callOutDetail.getCustomerSayText();
                String agentSay = callOutDetail.getAgentAnswerText();

                if (map.get(String.valueOf(callId)) == null) {
                    String result = getContext(botSay, customerSay, agentSay);
                    map.put(String.valueOf(callId), result);
                } else {
                    String result = map.get(String.valueOf(callId));
                    result += getContext(botSay, customerSay, agentSay);
                    map.put(String.valueOf(callId), result);
                }
            }
        }
        return map;
    }

    private String getContext(String botSay, String customerSay, String agentSay) {
        if (StringUtils.isNotBlank(customerSay)) {
            return "客户：" + customerSay + "\r\n";
        } else if (StringUtils.isNotBlank(botSay)) {
            return "机器人：" + botSay + "\r\n";
        }else if (StringUtils.isNotBlank(agentSay)) {
            return "坐席：" + agentSay + "\r\n";
        }else{
            return "";
        }
    }


    @Override
    public String getRecordFileUrl(String callId) {
        CallOutRecord callOutRecord = callOutRecordMapper.selectByPrimaryKey(new BigInteger(callId));
        if (callOutRecord != null) {
            return callOutRecord.getRecordUrl();
        }
        return null;
    }

    @Override
    public List<CallOutRecord> getRecords(String callIds) {

        String[] callidArr = callIds.split(",");
        List<BigInteger> idList = new ArrayList();
        for(String callId: callidArr){
            idList.add(new BigInteger(callId));
        }
        CallOutRecordExample example = new CallOutRecordExample();
        example.createCriteria().andCallIdIn(idList);
        return callOutRecordMapper.selectByExample(example);

    }

    @Override
    public void delRecord(String callIds) {
        String[] callidArr = callIds.split(",");
        List<BigInteger> idList = new ArrayList();
        for(String callId: callidArr){
            idList.add(new BigInteger(callId));
        }
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setIsdel(1);
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdIn(idList);
        callOutPlanMapper.updateByExampleSelective(callOutPlan, example);
    }

    @Override
    public List<String> getFtypes() {
        return errorMatchMapper.selectDistinctErrorName();
    }

    @Override
    @Transactional
    public void updateCallDetailCustomerSayText(CallDetailUpdateReq callDetailUpdateReq,Long userId) {
        BigInteger detailId = new BigInteger(callDetailUpdateReq.getCallDetailId());

        CallOutDetail callOutDetail =  callOutDetailMapper.selectByPrimaryKey(detailId);
        String customerSayText = callOutDetail.getCustomerSayText();

        CallOutDetail record = new CallOutDetail();
        String customerSayTextNew = callDetailUpdateReq.getCustomerSayText();
        record.setCustomerSayText(customerSayTextNew);
        record.setCallDetailId(detailId);
        record.setIsupdate(1);
        callOutDetailMapper.updateByPrimaryKeySelective(record);

        CallOutDetailLog detailLog = new CallOutDetailLog();
        detailLog.setCallDetailId(detailId);
        detailLog.setUpdateBy(userId.intValue());
        detailLog.setUpdateTime(new Date());
        detailLog.setCustomerSayTextNew(customerSayTextNew);
        if(customerSayText!=null){
            detailLog.setCustomerSayText(customerSayText);
        }
        callOutDetailLogMapper.insertSelective(detailLog);
    }

    @Override
    public List<CallOutPlan> getCallRecordListByPhone(String phone) {
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andPhoneNumEqualTo(phone);
        return callOutPlanMapper.selectByExample(example);
    }
}
