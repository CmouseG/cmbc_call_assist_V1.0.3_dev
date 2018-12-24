package com.guiji.fsagent.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
import com.guiji.fsagent.config.PathConfig;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.WavLengthVO;
import com.guiji.fsagent.service.TemplateService;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.utils.NasUtil;
import com.guiji.utils.NetFileDownUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    PathConfig pathConfig;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    IRobotRemote iRobotFeign;
    @Override
    public boolean istempexist(String tempId) {
        tempId = tempId.substring(0,tempId.lastIndexOf("_")+1) ;
        String tempPath = pathConfig.getTempPath() + tempId+"en";
        return FileUtil.isExist(tempPath);
    }

    @Override
    public boolean downloadttswav(String tempId, String callId) {
        //下载tts录音
        TtsVoiceReq  ttsVoiceReq = new TtsVoiceReq();
        ttsVoiceReq.setTemplateId(tempId);
        ttsVoiceReq.setSeqid(callId);
        Result.ReturnData<List<TtsVoice>> result = iRobotFeign.ttsCompose(ttsVoiceReq);
        if(!result.getCode().equals("0")||result.getBody()==null){
            logger.warn("下载tts录音，获取录音URL失败:"+result.getCode());
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_TTS_DOWNLOAD);
        }
        List<TtsVoice> list = result.getBody();
        File ttsDir = new File(pathConfig.getTtsPath()+callId);  // 创建tts文件夹
        if (!ttsDir.exists()) {//文件不存在则创建
            ttsDir.mkdir();
        }
        for (TtsVoice ttsVoice:list) {
            File ttsVoiceFile = new File(pathConfig.getTtsPath()+callId+"/"+ttsVoice.getTtsKey()+".wav");
            NetFileDownUtil util = new NetFileDownUtil(ttsVoice.getTtsUrl(),ttsVoiceFile);
            try {
                util.downfile();
            } catch (IOException e) {
                logger.info("下载tts录音失败，失败的文件为：[{}]==》错误的原因为：[{}]",ttsVoice.getTtsUrl(), e);
            }
        }
        return true;
    }

    @Override
    public RecordVO uploadrecord(RecordReqVO recordReqVO) {
        RecordVO record = new RecordVO();
        record.setFileName(recordReqVO.getFileName());
        SysFileReqVO sysFileReqVO = new SysFileReqVO();
        sysFileReqVO.setBusiId(recordReqVO.getBusiId());
        sysFileReqVO.setBusiType(recordReqVO.getBusiType());
        sysFileReqVO.setSysCode(recordReqVO.getSysCode());
        sysFileReqVO.setUserId(recordReqVO.getUserId());
        sysFileReqVO.setThumbImageFlag("0");
        String uploadFile = fsConfig.getHomeDir()+"/recordings/" + record.getFileName();
       if(FileUtil.isExist(uploadFile)) {
           logger.info("上传录音失败,录音文件不存在，文件名为:[{}]",uploadFile);
           throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_RECORDING_NOTEXIST);
       }
       if(FileUtil.getWavDuration(uploadFile)==0){
           logger.info("上传录音失败,录音文件长度为0，文件名为:[{}]",uploadFile);
           throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_RECORDING_NO_LENGTH);
       }
        SysFileRspVO sysFileRspVO = new NasUtil().uploadNas(sysFileReqVO, new File(uploadFile));
        if(sysFileRspVO==null){
            logger.info("上传录音失败,失败的文件为:[{}]",uploadFile);
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_UPLOAD_ERROR);
       }
        record.setFileUrl(sysFileRspVO.getSkUrl());
        return record;
    }

    @Override
    public List<WavLengthVO> getwavlength(String tempId) {
        tempId = tempId.substring(0,tempId.lastIndexOf("_")+1) ;
        String tempPath = pathConfig.getTempPath() + tempId+"en/"+tempId+"rec";
        List<WavLengthVO> list = new ArrayList<WavLengthVO>();
        File tempFile = new File(tempPath);
        if(!tempFile.exists()){
            logger.info("模板录音文件夹不存在[{}]",tempId);
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_TEMP_NOTEXIST);
        }
        File[] fs = tempFile.listFiles();
        for (File f : fs) {
            WavLengthVO wavLengthVO =new WavLengthVO();
            String filename = f.getName();
            wavLengthVO.setFileName(filename);
            wavLengthVO.setLength(FileUtil.getWavDuration(tempPath+"/"+filename));
            list.add(wavLengthVO);
        }
        return list;
    }

}

