package com.guiji.calloutserver.manager;


import com.guiji.callcenter.dao.entity.FsBind;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 11:11
 * @Project：guiyu-parent
 * @Description:
 */
public interface FsManager {
    /**
     * 申请FreeSWITCH资源
     * @return
     */
    FsBind applyfs();

    /**
     * 释放FreeSWITCH资源
     */
    void releasefs();
}
