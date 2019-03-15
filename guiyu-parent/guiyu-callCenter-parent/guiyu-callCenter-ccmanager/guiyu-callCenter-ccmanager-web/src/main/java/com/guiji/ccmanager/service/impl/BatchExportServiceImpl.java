package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.AgentExample;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.entity.CallOutPlanQueryEntity;
import com.guiji.ccmanager.manager.CacheManager;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.BatchExportService;
import com.guiji.ccmanager.vo.CallRecordListReq;
import com.guiji.utils.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * author:liyang
 * Date:2019/3/15 10:49
 * Description:
 */
@Service
public class BatchExportServiceImpl implements BatchExportService {

    @Autowired
    CallOutPlanMapper callOutPlanMapper;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    AuthService authService;
    @Autowired
    AgentMapper agentMapper;



    @Override
    public List<BigInteger> callrecord(Date startDate, Date endDate, Boolean isSuperAdmin, String customerId, String orgCode,
                                       CallRecordListReq callRecordListReq, Integer isDesensitization) {

        CallOutPlanQueryEntity callOutPlanQueryEntity = new CallOutPlanQueryEntity();
        BeanUtil.copyProperties(callRecordListReq, callOutPlanQueryEntity);
        callOutPlanQueryEntity.setStartDate(startDate);
        callOutPlanQueryEntity.setEndDate(endDate);
        callOutPlanQueryEntity.setCustomerId(customerId);
        callOutPlanQueryEntity.setOrgCode(orgCode);
        callOutPlanQueryEntity.setIsSuperAdmin(isSuperAdmin);
        CallOutPlanExample example = getExample(callOutPlanQueryEntity,callRecordListReq.getCustomerId());

        int limitStart =0;
        String startCount = callRecordListReq.getStartCount();
        if(StringUtils.isNotBlank(startCount)){
            int startInt = Integer.valueOf(startCount);
            limitStart = startInt>0 ? startInt-1:startInt;
        }
        String endCount = callRecordListReq.getEndCount();
        int pageSize = Integer.valueOf(endCount);

        example.setLimitStart(limitStart);
        example.setLimitEnd(pageSize);
        example.setOrderByClause("call_id desc");
        example.setIsDesensitization(isDesensitization);

//        List<CallOutPlan> list = null;
        List<BigInteger> listIds= callOutPlanMapper.selectCallIds4Encrypt(example);
//        if(listIds!=null && listIds.size()>0){
//            CallOutPlanExample exampleIds = new CallOutPlanExample();
//            exampleIds.createCriteria().andCallIdIn(listIds);
//            exampleIds.setOrderByClause("call_id desc");
//
//            list = callOutPlanMapper.selectByExample4Encrypt(exampleIds);
//        }
//
//        List<CallOutPlan4ListSelect> listResult = new ArrayList<CallOutPlan4ListSelect>();
//
//        if (list != null && list.size() > 0) {
//            for (CallOutPlan callOutPlan : list) {
//                CallOutPlan4ListSelect callOutPlan4ListSelect = new CallOutPlan4ListSelect();
//                BeanUtil.copyProperties(callOutPlan, callOutPlan4ListSelect);
//
//                callOutPlan4ListSelect.setTempId(cacheManager.getTempName(callOutPlan.getTempId()));
//                callOutPlan4ListSelect.setUserName(cacheManager.getUserName(callOutPlan.getCustomerId()));
//                callOutPlan4ListSelect.setCallId(callOutPlan.getCallId().toString());
//                listResult.add(callOutPlan4ListSelect);
//            }
//        }

        return listIds;
    }


    public CallOutPlanExample getExample(CallOutPlanQueryEntity callOutPlanQueryEntity,String queryUser) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        if (callOutPlanQueryEntity.getStartDate() != null) {
            criteria.andCreateTimeGreaterThan(callOutPlanQueryEntity.getStartDate());
        }
        if (callOutPlanQueryEntity.getEndDate() != null) {
            criteria.andCreateTimeLessThan(callOutPlanQueryEntity.getEndDate());
        }
        if(StringUtils.isNotBlank(queryUser)){
            criteria.andCustomerIdEqualTo(Integer.valueOf(queryUser));
        }
        if(!callOutPlanQueryEntity.getIsSuperAdmin()){//不是管理员
            long userId = Long.valueOf(callOutPlanQueryEntity.getCustomerId());
            if (authService.isAgentOrCompanyAdmin(userId) ) {//代理商 或者企业管理员
                criteria.andOrgCodeLike(callOutPlanQueryEntity.getOrgCode()+"%");
            } else if(authService.isSeat(userId)){//客服
                String userName = authService.getUserName(userId);
                AgentExample agentExample = new AgentExample();
                agentExample.createCriteria().andCrmLoginIdEqualTo(userName);
                List<Agent> listAgent = agentMapper.selectByExample(agentExample);
                Long agentId = listAgent.get(0).getUserId();
                criteria.andAgentIdEqualTo(String.valueOf(agentId));
            }else {
                criteria.andCustomerIdEqualTo(Integer.valueOf(callOutPlanQueryEntity.getCustomerId()));
            }
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
        criteria.andIsdelEqualTo(0);
        criteria.andCallStateGreaterThanOrEqualTo(Constant.CALLSTATE_HANGUP_OK);
        return example;
    }


}
