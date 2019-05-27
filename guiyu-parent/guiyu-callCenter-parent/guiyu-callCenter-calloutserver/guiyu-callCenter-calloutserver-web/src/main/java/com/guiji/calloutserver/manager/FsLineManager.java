package com.guiji.calloutserver.manager;

import com.guiji.fsmanager.entity.FsLineInfoVO;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:55
 * @Project：guiyu-parent
 * @Description:
 */
public interface FsLineManager {
    FsLineInfoVO getFsLine(Integer lineId) throws Exception;
}
