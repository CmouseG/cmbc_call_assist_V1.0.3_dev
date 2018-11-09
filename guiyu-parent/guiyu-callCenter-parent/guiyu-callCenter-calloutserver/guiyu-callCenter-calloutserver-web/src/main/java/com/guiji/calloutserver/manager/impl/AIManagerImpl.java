package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.entity.AIInitRequest;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.manager.AIManager;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/9 17:38
 * @Project：guiyu-parent
 * @Description:
 */
@Service
public class AIManagerImpl implements AIManager {
    @Override
    public AIResponse applyAi(AIInitRequest aiRequest) {
        return null;
    }

    @Override
    public AIResponse sendAiRequest(AIRequest aiRequest) {
        return null;
    }

    @Override
    public void releaseAi(String uuid) {

    }
}
