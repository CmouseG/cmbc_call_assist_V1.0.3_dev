package com.guiji.calloutserver.manager;

import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;

public interface AIManager {
    /**
     *  申请新的ai资源
     *
     post /remote/aiCallNex
     */
    AIResponse applyAi(AIInitRequest aiRequest) throws Exception;

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
    AIResponse sendAiRequest(AIRequest aiRequest) throws Exception;

    /**
     * 释放被占用的ai资源
     * @param uuid
     */
    void releaseAi(String uuid);
}
