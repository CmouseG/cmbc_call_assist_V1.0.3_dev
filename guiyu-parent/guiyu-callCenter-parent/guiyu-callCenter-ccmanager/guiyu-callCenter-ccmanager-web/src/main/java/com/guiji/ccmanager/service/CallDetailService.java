package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.ccmanager.vo.CallOutPlanVO;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:32
 * @Description:
 */
public interface CallDetailService {
    public List<CallOutPlan> callrecord(Date startDate, Date endDate, String customerId, int pageSize, int pageNo );

    public CallOutPlanVO getCallDetail(String callId);

    public int callrecordCount(Date start, Date end, String customerId);
}
