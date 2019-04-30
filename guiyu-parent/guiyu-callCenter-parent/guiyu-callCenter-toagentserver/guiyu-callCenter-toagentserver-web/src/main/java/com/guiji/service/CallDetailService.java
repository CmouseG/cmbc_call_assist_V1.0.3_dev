package com.guiji.service;

import com.guiji.entity.CallDetail;
import com.guiji.entity.ECallDirection;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:40
 * @Project：ccserver
 * @Description:
 */
public interface CallDetailService {
    void insert(CallDetail callDetail, ECallDirection callDirection);

    List<CallDetail> findByCallPlanId(String callPlanId, ECallDirection callDirection, Integer orgId);

   void getExportCalldetails(String callPlanId,HttpServletResponse response,Integer orgId);
}
