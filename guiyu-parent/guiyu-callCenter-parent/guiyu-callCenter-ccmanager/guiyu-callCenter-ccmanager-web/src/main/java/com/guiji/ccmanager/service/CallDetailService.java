package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import com.guiji.callcenter.dao.entityext.MyCallOutPlanQueryEntity;
import com.guiji.ccmanager.entity.CallPlanUuidQuery;
import com.guiji.ccmanager.vo.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:32
 * @Description:
 */
public interface CallDetailService {
    MyCallOutPlanQueryEntity prepareQuery(Date startDate, Date endDate, Integer authLevel, String customerId,
                                          CallRecordListReq callRecordListReq, Integer orgId);

    List<CallOutPlan4ListSelect> callrecord(Date startDate, Date endDate, Integer authLevel, String customerId, String orgCode,
                                            int pageSize, int pageNo, CallRecordListReq callRecordListReq, Integer isDesensitization, Integer orgId);

    CallPlanDetailRecordVO getCallDetail(BigInteger callId,Integer orgId);

    MyCallOutPlanQueryEntity getMyCallOutPlanQueryEntity(CallRecordListReq callRecordListReq);

    int callrecordCount(Date start, Date end, Integer authLevel, String customerId, String orgCode, CallRecordListReq callRecordListReq, Integer orgId);

    String getDialogue(String callId,List<Integer> orgIdList);

    Map<String, String> getDialogues(List<BigInteger> callIds,MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);

    String getRecordFileUrl(String callId);

    List<CallOutRecord> getRecords(String callIds);

    void delRecord(String callId, List<Integer> orgIdList);

    List<CallPlanDetailRecordVO> getCallPlanDetailRecord(CallPlanUuidQuery callPlanUuidQuery);

    List<String> getFtypes();

    void updateIsRead(String callId, Integer orgId);

    void updateCallDetailCustomerSayText(CallDetailUpdateReq callDetailUpdateReq, Long userId,List<Integer> orgIdList);

    List<Map> getCallRecordList(CallRecordReq callRecordReq);

    int countCallRecordList(CallRecordReq callRecordReq);

    List<CallOutPlan> getCallRecordListByPhone(String phone,List<Integer> orgIdList);

    List<CallOutPlanRegistration> getCallPlanList(List<BigInteger> callIds, Integer isDesensitization,
                                                  MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);
}