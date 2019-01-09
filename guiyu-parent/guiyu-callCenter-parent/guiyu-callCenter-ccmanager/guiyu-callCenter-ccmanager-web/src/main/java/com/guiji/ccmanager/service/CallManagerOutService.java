package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.ccmanager.vo.TempIsOkResult;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 13:45
 * @Description:
 */
public interface CallManagerOutService {

    public void startcallplan(String customerId, String tempId, String lineId);

    public CallOutPlan getCallRecordById(BigInteger callId);

//    TempIsOkResult isTempOk(List<String> tempIdList);
}
