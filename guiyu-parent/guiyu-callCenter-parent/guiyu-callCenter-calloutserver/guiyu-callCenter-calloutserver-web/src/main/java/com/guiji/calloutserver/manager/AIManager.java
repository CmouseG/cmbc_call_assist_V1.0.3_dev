package com.guiji.calloutserver.manager;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;

public interface AIManager {
    /**
     *  申请新的ai资源
     *
     post /remote/aiCallNex
     */
    AIResponse applyAi(AIInitRequest aiRequest,String callid) throws Exception;

    /**
     * 检查是否可以打断
     * @param callUuid
     * @param sentence
     * @return
     */
//    boolean isMatch(String callUuid, String sentence, String aiNo, String userId);

    /**
     * 发起ai请求
     * @param aiRequest
     * @return
     * @throws Exception
     *
     *
    post /remote/aiLngKeyMatch

    post /remote/aiCallNex
     */
    void sendAiRequest(AIRequest aiRequest) throws Exception;

    /**
     * 释放被占用的ai资源
     * @param uuid
     */
    void releaseAi(CallOutPlan uuid);
}
