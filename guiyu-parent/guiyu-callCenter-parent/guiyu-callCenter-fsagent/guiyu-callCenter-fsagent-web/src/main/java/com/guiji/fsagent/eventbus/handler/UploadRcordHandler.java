package com.guiji.fsagent.eventbus.handler;

import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.NasUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class UploadRcordHandler {
//    @Autowired
//    SimpleEventSender simpleEventSender;
    @Autowired
    FsConfig fsConfig;

    @Autowired
    private QueueSender queueSender;

/*    @PostConstruct
    public void init() {
        simpleEventSender.register(this);
    }*/

//    @Subscribe
    @Async
    public void handleUploadRecord(RecordReqVO recordReqVO) {
        log.info("收到上传录音的事件[{}]", recordReqVO);
        RecordVO record = new RecordVO();
        BeanUtils.copyProperties(recordReqVO, record);
        SysFileReqVO sysFileReqVO = new SysFileReqVO();
        sysFileReqVO.setBusiId(recordReqVO.getBusiId());
        sysFileReqVO.setBusiType(recordReqVO.getBusiType());
        sysFileReqVO.setSysCode(recordReqVO.getSysCode());
        sysFileReqVO.setUserId(recordReqVO.getUserId());
        sysFileReqVO.setThumbImageFlag("0");
        String uploadOriginalFile = fsConfig.getHomeDir() + "/recordings/" + record.getFileName();//原文件路径
        String uploadTempFile = fsConfig.getHomeDir() + "/recordings/temp_" + record.getFileName();//截取后的文件路径
        String uploadOFile; //要上传的文件
        if (FileUtil.isExist(uploadTempFile)) { //如果截取后的文件存在，则上传截取后的文件
            log.info("上传截取后的录音文件：[{}]", uploadTempFile);
            uploadOFile = uploadTempFile;
        } else {
            if (!FileUtil.isExist(uploadOriginalFile)) {//如果源文件也不存在，直接抛出异常
                log.info("上传录音失败,录音文件不存在，文件名为:[{}]", uploadOriginalFile);
                throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_RECORDING_NOTEXIST);
            }
            //如果截取文件不存在，源文件存在，则上传源文件
            log.info("上传原录音文件：[{}]", uploadOriginalFile);
            uploadOFile = uploadOriginalFile;
        }
        SysFileRspVO sysFileRspVO;
        //第一次上传
        sysFileRspVO = new NasUtil().uploadNas(sysFileReqVO, new File(uploadOFile));
        if (sysFileRspVO == null) {
            log.info("第一次尝试上传录音到NAS失败,继续第二次尝,文件为:[{}]", uploadOFile);
            sysFileRspVO =  new NasUtil().uploadNas(sysFileReqVO, new File(uploadOFile));
            if (sysFileRspVO == null) {
                log.info("第二次尝试上传录音到NAS失败,结束尝试失败的文件为:[{}]", uploadOFile);
                throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_UPLOAD_ERROR);
            }
        }
        FileUtil.delete(uploadOFile);
        record.setFileUrl(sysFileRspVO.getSkUrl());
        //record.setFileUrl("http://123.34.22.11");
        queueSender.send("callCenter.record_update_mq", JsonUtils.bean2Json(record));
    }

}
