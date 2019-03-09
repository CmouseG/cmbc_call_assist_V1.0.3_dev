package com.guiji.calloutserver.manager;


import java.util.List;

/**
 * 对通话线路的管理
 */
public interface SimCallManager {

    void addSimCall(String callId, Boolean simCall);

    boolean isSimCall(String callId);
}
