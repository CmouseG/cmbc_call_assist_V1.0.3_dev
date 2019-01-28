package com.guiji.service;

import com.guiji.callcenter.dao.entity.CallInDetail;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:40
 * @Project：ccserver
 * @Description:
 */
public interface CallInDetailService {
    void saveAll(List<CallInDetail> callInDetails);

    List<CallInDetail> findByCallPlanId(String callPlanId);

    CallInDetail insert(CallInDetail callInDetail);
}
