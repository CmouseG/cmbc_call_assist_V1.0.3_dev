package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.*;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import com.guiji.callcenter.dao.entityext.MyCallOutPlanQueryEntity;
import com.guiji.ccmanager.constant.AuthLevelEnum;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.entity.CallOutPlanQueryEntity;
import com.guiji.ccmanager.entity.CallPlanUuidQuery;
import com.guiji.ccmanager.manager.CacheManager;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.service.LineNameService;
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
    @Autowired
    CallPlanExportMapper callPlanExportMapper;
    @Autowired
    LineNameService lineNameService;
    @Autowired
    MyCallOutPlanMapper myCallOutPlanMapper;

    @Override
    public void updateIsRead(String callId, Integer orgId) {
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setIsread(1);

        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andOrgIdEqualTo(orgId)
                .andCallIdEqualTo(new BigInteger(callId));
        callOutPlanMapper.updateByExampleSelective(callOutPlan,example);
    }

    public CallOutPlanExample getExample(CallOutPlanQueryEntity callOutPlanQueryEntity,String queryUser) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        getCriteria(criteria, callOutPlanQueryEntity, queryUser, false);

        long userId = Long.valueOf(callOutPlanQueryEntity.getCustomerId());
        if(authService.isSeat(userId)){//具有人工坐席权限
            try{
                String userName = authService.getUserName(userId);
                AgentExample agentExample = new AgentExample();
                agentExample.createCriteria().andCrmLoginIdEqualTo(userName);
                List<Agent> listAgent = agentMapper.selectByExample(agentExample);
                Long agentId = listAgent.get(0).getUserId();
                CallOutPlanExample.Criteria criteria2 = example.or();
                criteria2.andAgentIdEqualTo(String.valueOf(agentId));
                getCriteria(criteria2, callOutPlanQueryEntity, queryUser, true);
            }catch (Exception e){
                log.error("人工坐席权限查询出现异常",e);
            }

        }

        return example;
    }

    public CallOutPlanExample.Criteria getCriteria(CallOutPlanExample.Criteria criteria, CallOutPlanQueryEntity callOutPlanQueryEntity,
                                                   String queryUser, boolean isSeat){

        if (callOutPlanQueryEntity.getStartDate() != null) {
            criteria.andCreateTimeGreaterThan(callOutPlanQueryEntity.getStartDate());
        }
        if (callOutPlanQueryEntity.getEndDate() != null) {
            criteria.andCreateTimeLessThan(callOutPlanQueryEntity.getEndDate());
        }
        if(!isSeat && callOutPlanQueryEntity.getAuthLevel() ==1) {
            criteria.andCustomerIdEqualTo(Integer.valueOf(callOutPlanQueryEntity.getCustomerId()));
        }else if(!isSeat && callOutPlanQueryEntity.getAuthLevel() ==2) {
            criteria.andOrgCodeEqualTo(callOutPlanQueryEntity.getOrgCode());
        }else if(!isSeat && callOutPlanQueryEntity.getAuthLevel() ==3) {
            criteria.andOrgCodeLike(callOutPlanQueryEntity.getOrgCode()+"%");
        }
        if(StringUtils.isNotBlank(queryUser)){
            criteria.andCustomerIdEqualTo(Integer.valueOf(queryUser));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getPhoneNum())) {
            criteria.andPhoneNumLike("%"+callOutPlanQueryEntity.getPhoneNum()+"%");
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getDurationMin())) {
            criteria.andDurationGreaterThan(Integer.valueOf(callOutPlanQueryEntity.getDurationMin()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getDurationMax())) {
            criteria.andDurationLessThanOrEqualTo(Integer.valueOf(callOutPlanQueryEntity.getDurationMax()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getBillSecMin())) {
            criteria.andBillSecGreaterThan(Integer.valueOf(callOutPlanQueryEntity.getBillSecMin()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getBillSecMax())) {
            criteria.andBillSecLessThanOrEqualTo(Integer.valueOf(callOutPlanQueryEntity.getBillSecMax()));
        }
        String accurateIntent =callOutPlanQueryEntity.getAccurateIntent();
        if (StringUtils.isNotBlank(accurateIntent)) {
            if (accurateIntent.contains(",")) {
                String[] arr = accurateIntent.split(",");
                criteria.andAccurateIntentIn(Arrays.asList(arr));
            } else {
                criteria.andAccurateIntentEqualTo(accurateIntent);
            }
        }
        String freason =callOutPlanQueryEntity.getFreason();
        if (StringUtils.isNotBlank(freason)) {
            if (freason.contains(",")) {
                String[] arr = freason.split(",");
                criteria.andReasonIn(Arrays.asList(arr));
            } else {
                criteria.andReasonEqualTo(freason);
            }
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getCallId())) {
            criteria.andCallIdEqualTo(new BigInteger(callOutPlanQueryEntity.getCallId()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getTempId())) {
            criteria.andTempIdEqualTo(callOutPlanQueryEntity.getTempId());
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getIsRead())) {
            criteria.andIsreadEqualTo(Integer.valueOf(callOutPlanQueryEntity.getIsRead()));
        }
        if (callOutPlanQueryEntity.getBatchId()!=null) {
            criteria.andBatchIdEqualTo(callOutPlanQueryEntity.getBatchId());
        }
        if (callOutPlanQueryEntity.getIntervened()!=null) {
            if(callOutPlanQueryEntity.getIntervened().equals("1")){
                criteria.andIntervenedEqualTo(true);
            }else if(callOutPlanQueryEntity.getIntervened().equals("0")){
                criteria.andIntervenedEqualTo(false);
            }
        }
        criteria.andIsdelEqualTo(0);
        criteria.andCallStateGreaterThanOrEqualTo(Constant.CALLSTATE_HANGUP_OK);

        return criteria;
    }

    @Override
    public List<Map> getCallRecordList(CallRecordReq callRecordReq) {

        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = new MyCallOutPlanQueryEntity();

        myCallOutPlanQueryEntity.setLimitStart((callRecordReq.getPageNo() - 1) * callRecordReq.getPageSize());
        myCallOutPlanQueryEntity.setLimitEnd(callRecordReq.getPageSize());

        if (callRecordReq.getTime() != null){
            myCallOutPlanQueryEntity.setTime(callRecordReq.getTime() - 1);
        }
        if (callRecordReq.getIsDesensitization() != null){
            myCallOutPlanQueryEntity.setIsDesensitization(callRecordReq.getIsDesensitization());
        }
        if (callRecordReq.getAccurateIntent() != null){
            myCallOutPlanQueryEntity.setAccurateIntent(callRecordReq.getAccurateIntent());
        }
        Integer authLevel = callRecordReq.getAuthLevel();
        if(authLevel==AuthLevelEnum.USER.getLevel()){
            if (callRecordReq.getUserId() != null) {
                myCallOutPlanQueryEntity.setCustomerId(callRecordReq.getUserId().intValue());
            }
        }
        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(authLevel,callRecordReq.getOrgId());
        if(orgIdList!=null){
            if(orgIdList.size()==1){
                myCallOutPlanQueryEntity.setOrgId(orgIdList.get(0));
            }else{
                myCallOutPlanQueryEntity.setOrgIdList(orgIdList);
            }
        }

        List<Map> list = myCallOutPlanMapper.selectCallPlanRecord4Encrypt(myCallOutPlanQueryEntity);
        return list;

      /*  List<BigInteger> ids = myCallOutPlanMapper.selectCallIdList(myCallOutPlanQueryEntity);
        if(ids!=null && ids.size()>0){
            List<Map> list = callOutPlanMapper.selectCallPlanRecord4Encrypt(ids,callRecordReq.getIsDesensitization());
            return list;
        }else{
            return null;
        }*/
    }

    @Override
    public int countCallRecordList(CallRecordReq callRecordReq) {

        Map map = new HashMap();

        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(callRecordReq.getAuthLevel(),callRecordReq.getOrgId());
        map.put("orgIdList",orgIdList);
        if (callRecordReq.getAuthLevel()!=null && callRecordReq.getAuthLevel()==AuthLevelEnum.USER.getLevel()
                && callRecordReq.getUserId() != null) {
            map.put("customerId", callRecordReq.getUserId());
        }
//        if (callRecordReq.getOrgCode() != null)
//            map.put("orgCode", callRecordReq.getOrgCode());
        if (callRecordReq.getTime() != null) {
            map.put("time", callRecordReq.getTime() - 1);
        }
        if (callRecordReq.getAccurateIntent() != null) {
            map.put("accurateIntent", callRecordReq.getAccurateIntent());
        }
        int i = callOutPlanMapper.countCallRecordList(map);
        return i;
    }

    @Override
    public int callrecordCount(Date startDate, Date endDate, Integer authLevel, String customerId, String orgCode,
                               CallRecordListReq callRecordListReq, Integer orgId) {

        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = prepareQuery(startDate, endDate,authLevel,customerId,callRecordListReq,orgId);
        int result = myCallOutPlanMapper.countCallOutPlan(myCallOutPlanQueryEntity);
        return result;
    }

    @Override
    public MyCallOutPlanQueryEntity prepareQuery(Date startDate, Date endDate, Integer authLevel, String customerId,
                                                 CallRecordListReq callRecordListReq, Integer orgId){
        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = getMyCallOutPlanQueryEntity(callRecordListReq);
        myCallOutPlanQueryEntity.setStartDate(startDate);
        myCallOutPlanQueryEntity.setEndDate(endDate);

        //权限过滤
        if(authLevel == AuthLevelEnum.USER.getLevel()){
            myCallOutPlanQueryEntity.setOrgId(orgId);
            myCallOutPlanQueryEntity.setCustomerId(Integer.valueOf(customerId));
            /*long longCustomerId = Long.valueOf(customerId);
            if(authService.isSeat(longCustomerId)) {//具有人工坐席权限
                AgentExample agentExample = new AgentExample();
                agentExample.createCriteria().andCustomerIdEqualTo(longCustomerId);
                List<Agent> listAgent = agentMapper.selectByExample(agentExample);
                if(listAgent!=null && listAgent.size()>0){
                    myCallOutPlanQueryEntity.setAgentId(String.valueOf(listAgent.get(0).getUserId()));
                }
            }*/
            String agentId = authService.getAgentIdByCustomerId(customerId);
            if(StringUtils.isNotEmpty(agentId)){
                myCallOutPlanQueryEntity.setAgentId(agentId);
            }
        }else if(authLevel == AuthLevelEnum.ORG.getLevel()){
            myCallOutPlanQueryEntity.setOrgId(orgId);
        }else if(authLevel == AuthLevelEnum.ORG_EXT.getLevel()){
            if(callRecordListReq.getOrgIdList()!=null && callRecordListReq.getOrgIdList().size()>0){
                myCallOutPlanQueryEntity.setOrgIdList(callRecordListReq.getOrgIdList());
            }else{ //不允许全部查询，那么就查询他本组织的
                myCallOutPlanQueryEntity.setOrgId(orgId);
            }
        }

        if(callRecordListReq.getCheckAll()!=null &&callRecordListReq.getCheckAll() &&
                callRecordListReq.getExcludeList()!=null && callRecordListReq.getExcludeList().size()>0){
            List<String> list = callRecordListReq.getExcludeList();
            List<BigInteger> notInList = new ArrayList<>();
            for(String callId :list){
                notInList.add(new BigInteger(callId));
            }
            myCallOutPlanQueryEntity.setNotInList(notInList);
        }

        if(callRecordListReq.getCheckAll()==null || !callRecordListReq.getCheckAll()){
            List<String> list = callRecordListReq.getIncludeList();
            if(list!=null && list.size()>0){
                List<BigInteger> inList = new ArrayList<>();
                for(String callId :list){
                    inList.add(new BigInteger(callId));
                }
                myCallOutPlanQueryEntity.setCallIdList(inList);
            }
        }

        return myCallOutPlanQueryEntity;

    }

    @Override
    public List<CallOutPlan4ListSelect> callrecord(Date startDate, Date endDate, Integer authLevel, String customerId, String orgCode,
                                                   int pageSize, int pageNo, CallRecordListReq callRecordListReq, Integer isDesensitization, Integer orgId) {

        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = prepareQuery(startDate, endDate,authLevel,customerId,callRecordListReq,orgId);
        int limitStart = (pageNo - 1) * pageSize;
        myCallOutPlanQueryEntity.setLimitStart(limitStart);
        myCallOutPlanQueryEntity.setLimitEnd(pageSize);
        myCallOutPlanQueryEntity.setIsDesensitization(isDesensitization);

        List<CallOutPlan> list = myCallOutPlanMapper.selectCallOutPlanList(myCallOutPlanQueryEntity);

        List<CallOutPlan4ListSelect> listResult = new ArrayList<CallOutPlan4ListSelect>();

        if (list != null && list.size() > 0) {
            Map<String,String> lineMap = new HashMap();  //本地map，避免反复查询redis
            for (CallOutPlan callOutPlan : list) {
                CallOutPlan4ListSelect callOutPlan4ListSelect = new CallOutPlan4ListSelect();
                BeanUtil.copyProperties(callOutPlan, callOutPlan4ListSelect);

                callOutPlan4ListSelect.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
                callOutPlan4ListSelect.setUserName(cacheManager.getUserName(callOutPlan.getCustomerId()));
                callOutPlan4ListSelect.setCallId(callOutPlan.getCallId().toString());

                String lineKey =callOutPlan.getLineId().toString()+"_"+callOutPlan.getCustomerId().toString();
                if(lineMap.get(lineKey)!=null){
                    callOutPlan4ListSelect.setLineName(lineMap.get(lineKey));
                }else{
                    String lineName =lineNameService.getLineName(callOutPlan.getLineId(),callOutPlan.getCustomerId());
                    lineMap.put(lineKey,lineName);
                    callOutPlan4ListSelect.setLineName(lineName);
                }
                listResult.add(callOutPlan4ListSelect);
            }
        }

        return listResult;
    }

    @Override
    public MyCallOutPlanQueryEntity getMyCallOutPlanQueryEntity(CallRecordListReq callRecordListReq){

        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = new MyCallOutPlanQueryEntity();
//        BeanUtil.copyProperties(callRecordListReq, myCallOutPlanQueryEntity);
        if(StringUtils.isNotBlank(callRecordListReq.getPhoneNum())){
            myCallOutPlanQueryEntity.setPhoneNum(callRecordListReq.getPhoneNum());
        }
        if(StringUtils.isNotBlank(callRecordListReq.getTempId())){
            myCallOutPlanQueryEntity.setTempId(callRecordListReq.getTempId());
        }
        if(callRecordListReq.getBatchId()!=null){
            myCallOutPlanQueryEntity.setBatchId(callRecordListReq.getBatchId());
        }

        if(StringUtils.isNotBlank(callRecordListReq.getDurationMin())){
            myCallOutPlanQueryEntity.setDurationMin(Integer.valueOf(callRecordListReq.getDurationMin()));
        }
        if(StringUtils.isNotBlank(callRecordListReq.getDurationMax())){
            myCallOutPlanQueryEntity.setDurationMax(Integer.valueOf(callRecordListReq.getDurationMax()));
        }
        if(StringUtils.isNotBlank(callRecordListReq.getBillSecMin())){
            myCallOutPlanQueryEntity.setBillSecMin(Integer.valueOf(callRecordListReq.getBillSecMin()));
        }
        if(StringUtils.isNotBlank(callRecordListReq.getBillSecMax())){
            myCallOutPlanQueryEntity.setBillSecMax(Integer.valueOf(callRecordListReq.getBillSecMax()));
        }

        String accurateIntent =callRecordListReq.getAccurateIntent();
        if (StringUtils.isNotBlank(accurateIntent)) {
            if (accurateIntent.contains(",")) {
                String[] arr = accurateIntent.split(",");
                myCallOutPlanQueryEntity.setAccurateIntentList(Arrays.asList(arr));
            } else {
                myCallOutPlanQueryEntity.setAccurateIntent(accurateIntent);
            }
        }
        String freason =callRecordListReq.getFreason();
        if (StringUtils.isNotBlank(freason)) {
            if (freason.contains(",")) {
                String[] arr = freason.split(",");
                myCallOutPlanQueryEntity.setReasonList(Arrays.asList(arr));
            } else {
                myCallOutPlanQueryEntity.setReason(freason);
            }
        }

        if(StringUtils.isNotBlank(callRecordListReq.getCallId())){
            myCallOutPlanQueryEntity.setCallId(new BigInteger(callRecordListReq.getCallId()));
        }
        if(StringUtils.isNotBlank(callRecordListReq.getIsRead())){
            myCallOutPlanQueryEntity.setIsRead(Integer.valueOf(callRecordListReq.getIsRead()));
        }
        if(StringUtils.isNotBlank(callRecordListReq.getCustomerId())){
            myCallOutPlanQueryEntity.setQueryUser(Integer.valueOf(callRecordListReq.getCustomerId()));
        }
        if(StringUtils.isNotBlank(callRecordListReq.getIntervened())){
            myCallOutPlanQueryEntity.setIntervened(Integer.valueOf(callRecordListReq.getIntervened()));
        }
        if(null != callRecordListReq.getLineId()){
            myCallOutPlanQueryEntity.setLineId(callRecordListReq.getLineId());
        }
        if(StringUtils.isNotEmpty(callRecordListReq.getAnswerUser())){
            myCallOutPlanQueryEntity.setAnswerUser(callRecordListReq.getAnswerUser());
        }
        if(StringUtils.isNotEmpty(callRecordListReq.getEnterprise())){
            myCallOutPlanQueryEntity.setEnterprise(callRecordListReq.getEnterprise());
        }

        myCallOutPlanQueryEntity.setIsdel(0);
        myCallOutPlanQueryEntity.setCallStateMin(Constant.CALLSTATE_HANGUP_OK);

        return myCallOutPlanQueryEntity;
    }


    @Override
    public CallPlanDetailRecordVO getCallDetail(BigInteger callId,Integer orgId) {

        CallOutPlanExample examplePlan = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteriaPlan = examplePlan.createCriteria();
        criteriaPlan.andCallIdEqualTo(callId)
                .andOrgIdEqualTo(orgId);
        List<CallOutPlan> callOutPlanList = callOutPlanMapper.selectByExample(examplePlan);
        if(callOutPlanList!=null && callOutPlanList.size()>0){
            CallOutPlan callOutPlan = callOutPlanList.get(0);
            CallOutRecord callOutRecord = callOutRecordMapper.selectByPrimaryKey(callId);
            callOutPlan.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
            if (callOutPlan != null) {

                CallOutDetailExample example = new CallOutDetailExample();
                CallOutDetailExample.Criteria criteria = example.createCriteria();
                criteria.andCallIdEqualTo(callId).andOrgIdEqualTo(orgId);
                example.setOrderByClause("IF(ISNULL(bot_answer_time),IF(ISNULL(agent_answer_time),customer_say_time,agent_answer_time),bot_answer_time),call_detail_id");
                List<CallOutDetail> details = callOutDetailMapper.selectByExample(example);

                CallOutDetailRecordExample exampleRecord = new CallOutDetailRecordExample();
                CallOutDetailRecordExample.Criteria criteriaRecord = exampleRecord.createCriteria();
                criteriaRecord.andCallIdEqualTo(callId);
                List<CallOutDetailRecord> records = callOutDetailRecordMapper.selectByExample(exampleRecord);

                List<CallOutDetailVO> resList = new ArrayList<CallOutDetailVO>();
                int listSize = details.size();
                for (int i=0; i<listSize; i++) { //加上无声音的判断
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

                            boolean novoice = true;
                            for(int m=i+1;m<listSize; m++){
                                Date customerSayTime = details.get(m).getCustomerSayTime(); //这个时间存储到秒
                                if(customerSayTime!=null){ //客户说话不为空
                                    Integer asrDuration = details.get(m).getAsrDuration();  //asr识别用时。到毫秒
                                    long actualStartTime = customerSayTime.getTime()-asrDuration-1000; //实际开始说话时间，防止误差，多减1秒
                                    if(actualStartTime<callOutDetail.getBotAnswerTime().getTime()){ //客户说话在前，表示不需要插入无声音
                                        novoice = false;
                                    }
                                    break;
                                }
                            }

                            if(novoice){
                                CallOutDetailVO callOutDetailVOInsert = new CallOutDetailVO();
                                callOutDetailVOInsert.setCustomerSayText("无声音");
                                callOutDetailVOInsert.setCustomerSayTime(callOutDetailBefore.getBotAnswerTime());
                                callOutDetailVOInsert.setCallId(callId);
                                resList.add(callOutDetailVOInsert);
                            }

                        }
                    }else if(callOutDetail.getAgentAnswerTime()!=null && i>0){
                        CallOutDetail callOutDetailBefore = details.get(i-1);
                        if(callOutDetailBefore.getAgentAnswerTime()!=null){//出现连续2个坐席说话，中间插入一个无声音


                            boolean novoice = true;
                            for(int m=i+1;m<listSize; m++){
                                Date customerSayTime = details.get(m).getCustomerSayTime(); //这个时间存储到秒
                                if(customerSayTime!=null){ //客户说话不为空
                                    Integer asrDuration = details.get(m).getAsrDuration();  //asr识别用时。到毫秒
                                    long actualStartTime = customerSayTime.getTime()-asrDuration-1000; //实际开始说话时间，防止误差，多减1秒
                                    if(actualStartTime<callOutDetail.getAgentAnswerTime().getTime()){ //客户说话在前，表示不需要插入无声音
                                        novoice = false;
                                    }
                                    break;
                                }
                            }

                            if(novoice){
                                CallOutDetailVO callOutDetailVOInsert = new CallOutDetailVO();
                                callOutDetailVOInsert.setCustomerSayText("无声音");
                                callOutDetailVOInsert.setCustomerSayTime(callOutDetailBefore.getAgentAnswerTime());
                                callOutDetailVOInsert.setCallId(callId);
                                resList.add(callOutDetailVOInsert);
                            }

                        }
                    }else if(callOutDetail.getCustomerSayTime()!=null && i<listSize-1){
                        CallOutDetail callOutDetailAfter = details.get(i+1);
                        if(callOutDetailAfter.getBotAnswerTime()!=null){//客户说话之后，下面一个是机器人说话，则取出分词
                            callOutDetailVO.setWordSegmentResult(callOutDetailAfter.getWordSegmentResult());
                            callOutDetailVO.setKeywords(callOutDetailAfter.getKeywords());
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
        }

        return null;
    }

    @Override
    public List<CallPlanDetailRecordVO> getCallPlanDetailRecord(CallPlanUuidQuery callPlanUuidQuery) {

        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(callPlanUuidQuery.getAuthLevel(),callPlanUuidQuery.getOrgId());

        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andPlanUuidIn(callPlanUuidQuery.getCallIds())
                .andOrgIdIn(orgIdList);
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
            exampleDetail.createCriteria().andCallIdIn(callIds).andOrgIdIn(orgIdList);
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
    public String getDialogue(String callId,List<Integer> orgIdList) {
        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdEqualTo(new BigInteger(callId)).andOrgIdIn(orgIdList);
        example.setOrderByClause("IF(ISNULL(bot_answer_time),IF(ISNULL(agent_answer_time),customer_say_time,agent_answer_time),bot_answer_time),call_detail_id");
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
    public List<CallOutPlanRegistration> getCallPlanList(List<BigInteger> idList, Integer isDesensitization,
                                                         MyCallOutPlanQueryEntity myCallOutPlanQueryEntity) {

        List<CallOutPlanRegistration> list = callPlanExportMapper.selectExportCallPlanDetail(idList,isDesensitization,
                myCallOutPlanQueryEntity.getOrgId(),myCallOutPlanQueryEntity.getOrgIdList());

        Map<String,String> tempmap = new HashMap();
        Map<Integer,String> usernameMap = new HashMap();
        if (list != null && list.size() > 0) {
            for (CallOutPlanRegistration callOutPlanRegistration : list) {

                String tempId = callOutPlanRegistration.getTempId();
                if(tempmap.get(tempId)!=null){
                    callOutPlanRegistration.setTempId(tempmap.get(tempId));
                }else{
                    String tempName = cacheManager.getTempName(tempId);
                    tempmap.put(tempId,tempName);
                    callOutPlanRegistration.setTempId(tempName);
                }

                Integer customerId = callOutPlanRegistration.getCustomerId();
                if(usernameMap.get(customerId)!=null){
                    callOutPlanRegistration.setUserName(usernameMap.get(customerId));
                }else{
                    String userName = cacheManager.getUserName(customerId);
                    usernameMap.put(customerId,userName);
                    callOutPlanRegistration.setUserName(userName);
                }

            }
        }
        return list;
    }

    @Override
    public Map<String, String> getDialogues(List<BigInteger> idList,MyCallOutPlanQueryEntity myCallOutPlanQueryEntity) {

        CallOutDetailExample example = new CallOutDetailExample();
        CallOutDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCallIdIn(idList);
        if(myCallOutPlanQueryEntity.getOrgId()!=null){
            criteria.andOrgIdEqualTo(myCallOutPlanQueryEntity.getOrgId());
        };
        if(myCallOutPlanQueryEntity.getOrgIdList()!=null){
            criteria.andOrgIdIn(myCallOutPlanQueryEntity.getOrgIdList());
        };

        example.setOrderByClause("IF(ISNULL(bot_answer_time),IF(ISNULL(agent_answer_time),customer_say_time,agent_answer_time),bot_answer_time),call_detail_id");

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
    public void delRecord(String callIds, List<Integer> orgIdList) {
        String[] callidArr = callIds.split(",");
        List<BigInteger> idList = new ArrayList();
        for(String callId: callidArr){
            idList.add(new BigInteger(callId));
        }
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setIsdel(1);
        CallOutPlanExample example = new CallOutPlanExample();
        example.createCriteria().andCallIdIn(idList).andOrgIdIn(orgIdList);
        callOutPlanMapper.updateByExampleSelective(callOutPlan, example);
    }

    @Override
    public List<String> getFtypes() {
        return errorMatchMapper.selectDistinctErrorName();
    }

    @Override
    @Transactional
    public void updateCallDetailCustomerSayText(CallDetailUpdateReq callDetailUpdateReq,Long userId,List<Integer> orgIdList) {
        BigInteger detailId = new BigInteger(callDetailUpdateReq.getCallDetailId());

        CallOutDetailExample example = new CallOutDetailExample();
        example.createCriteria().andCallDetailIdEqualTo(detailId).andOrgIdIn(orgIdList);
        List<CallOutDetail> callOutDetailList =  callOutDetailMapper.selectByExample(example);
        if(callOutDetailList!=null && callOutDetailList.size()>0){

            CallOutDetail callOutDetail = callOutDetailList.get(0);
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
    }

    @Override
    public List<CallOutPlan> getCallRecordListByPhone(String phone,List<Integer> orgIdList) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneNumEqualTo(phone);
        if(orgIdList!=null){
            if(orgIdList.size()==1){
                criteria.andOrgIdEqualTo(orgIdList.get(0));
            }else if(orgIdList.size()>1){
                criteria.andOrgIdIn(orgIdList);
            }
        }

        return callOutPlanMapper.selectByExample(example);
    }
}
