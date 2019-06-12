package com.guiji.calloutserver.manager;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:18
 * @Project：guiyu-parent
 * @Description:
 */
public interface DispatchManager {

    /**
     * 回掉调度中心，直接推到mq
     * @param callId
     * @param phoneNo
     * @param intent
     */
    void successSchedule(String callId, String phoneNo, String intent, Integer userId,Integer lineId, String tempId, Boolean isNeedPlan);

    void successScheduleSim(String callId, String phoneNo, String intent, Integer userId,Integer lineId, String tempId,
                            Boolean isNeedPlan, Boolean simLineIsOk, Boolean simLimitFlag);
}
