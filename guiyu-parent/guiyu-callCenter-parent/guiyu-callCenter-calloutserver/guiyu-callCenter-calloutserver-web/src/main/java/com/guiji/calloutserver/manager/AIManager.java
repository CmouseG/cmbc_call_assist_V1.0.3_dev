package com.guiji.calloutserver.manager;

import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;

public interface AIManager {
    /**
     *  申请新的ai资源
     */
    AIResponse applyAi(AIInitRequest aiRequest);

    /**
     * 发起ai请求
     * @param aiRequest
     * @return
     * @throws Exception
     */
    AIResponse sendAiRequest(AIRequest aiRequest);

    /**
     * 释放被占用的ai资源
     * @param uuid
     */
    void releaseAi(String uuid);
}
