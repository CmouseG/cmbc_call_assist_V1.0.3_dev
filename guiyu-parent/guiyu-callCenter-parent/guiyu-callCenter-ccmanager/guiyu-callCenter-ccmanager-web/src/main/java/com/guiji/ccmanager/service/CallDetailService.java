package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.ccmanager.vo.CallOutPlanVO;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:32
 * @Description:
 */
public interface CallDetailService {
    List<CallOutPlan> callrecord(Date startDate, Date endDate, String customerId, int pageSize, int pageNo, String phoneNum,String durationMin, String durationMax,
                                 String accurateIntent, String freason,String callId, String tempId );

    CallOutPlanVO getCallDetail(String callId);

    int callrecordCount(Date start, Date end, String customerId, String phoneNum,String durationMin,String durationMax,
                        String accurateIntent, String freason,String callId, String tempId);

    String getDialogue(String callId);

}
