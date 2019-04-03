package com.guiji.calloutserver.service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/23 16:06
 * @Project：guiyu-parent
 * @Description:
 */

import com.guiji.callcenter.dao.entity.ErrorMatch;

/**
 * 用于F类识别
 */
public interface ErrorMatchService {
    /**
     * 根据线路返回的应答信息，确定F类
     * @param responseMsg
     * @return
     */
    ErrorMatch findError(String responseMsg);
}
