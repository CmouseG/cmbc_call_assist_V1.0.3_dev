package com.guiji.service;

import com.guiji.callcenter.dao.entity.CallOutDetail;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:40
 * @Project：ccserver
 * @Description:
 */
public interface CallOutDetailService {
    void insertAll(List<CallOutDetail> callOutDetails);

    CallOutDetail insert(CallOutDetail detail);

    List<CallOutDetail> findByCallPlanId(String callPlanId,Integer orgId);
}
