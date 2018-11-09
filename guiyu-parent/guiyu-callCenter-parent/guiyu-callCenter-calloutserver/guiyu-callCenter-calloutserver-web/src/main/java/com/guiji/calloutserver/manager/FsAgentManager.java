package com.guiji.calloutserver.manager;

import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsmanager.entity.FsBindVO;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/8 15:02
 * @Project：guiyu-parent
 * @Description:
 */
public interface FsAgentManager {
    /**
     * 初始化与本外呼服务绑定在一起的FsAgent信息
     * @param fsBindVO
     */
    void init(FsBindVO fsBindVO);

     /**
      * 上传录音文件
      * @param fileId   文件id
      * @param fileName  文件名称
      * @param busiType  业务类型
      * @return
      */
     RecordVO uploadRecord(String fileId, String fileName, String busiType);

}
