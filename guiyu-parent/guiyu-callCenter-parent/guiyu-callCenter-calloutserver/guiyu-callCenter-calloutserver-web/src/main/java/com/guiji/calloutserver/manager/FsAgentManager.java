package com.guiji.calloutserver.manager;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsmanager.entity.FsBindVO;

import java.util.Map;

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
    RecordVO uploadRecord(String callId, String fileName, String busiType, Long userId);

    /**
     * 模板是否存在
     * @param tempId
     * @return
     */
     Result.ReturnData<Boolean> istempexist(String tempId);

    /**
     * 初始化模板录音时长缓存
     * @param tempId
     * @return
     */
    Map<String, Double> getwavlength(String tempId);

    /**
     * 获取缓存的录音文件时长
     * @return
     */
    Double getWavDruation(String tempId, String filename);
}
