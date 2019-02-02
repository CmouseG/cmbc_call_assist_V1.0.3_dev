package com.guiji.calloutserver.manager;


import java.util.List;

/**
 * 对通话线路的管理
 */
public interface LineListManager {

    void addLineList(String callId, List<Integer> lineList);


    /**
     * 获取下一个线路
     * @param uuid
     */
    Integer popNewLine(String uuid);


    /**
     * 线路是否都打完了
     *
     * @param uuid
     * @return
     */
    boolean isEnd(String uuid);
}
