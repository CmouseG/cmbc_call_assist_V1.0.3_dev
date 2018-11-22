package com.guiji.fsagent.service.impl;

import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.PathConfig;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.WavLengthVO;
import com.guiji.fsagent.service.TemplateService;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.nas.api.INas;
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
    public Boolean istempexist(String tempId) {
        String tempPath = pathConfig.getTempPath() + tempId;
        return FileUtil.isExist(tempPath);
    }

//    @Override
//    public Boolean downloadbotwav(String tempId) {
//        String tempRecordPath = pathConfig.getTempRecordPath() + tempId;
//        //判断模板录音是否已存在，存在则返回
//        if (FileUtil.isExist(tempRecordPath)) {
//            return true;
//        }
//        ;
//        //下载模板录音
//        // iRobotFeign.getVoiceResource();
//        //FileUtil.unzip();
//
//        return true;
//    }

    @Override
    public Boolean downloadttswav(String tempId, String callId) {
        //下载tts录音
        TtsVoiceReq  ttsVoiceReq = new TtsVoiceReq();
        ttsVoiceReq.setTemplateId(tempId);
        ttsVoiceReq.setSeqid(callId);
        List<TtsVoice> list = (List<TtsVoice>) iRobotFeign.ttsCompose(ttsVoiceReq);

        File ttsDir = new File(pathConfig.getTtsPath()+callId);  // 创建tts文件夹
        if (ttsDir.isDirectory() && !ttsDir.exists()) {//文件不存在则创建
            ttsDir.mkdir();
        }
        for (TtsVoice ttsVoice:list) {
            File ttsVoiceFile = new File(pathConfig.getTtsPath()+callId+"/"+ttsVoice.getTtsKey());
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
        SysFileRspVO sysFileRspVO = new NasUtil().uploadNas(sysFileReqVO, new File(fsConfig.getHomeDir()+"/recordings/" + record.getFileName()));
        if(sysFileRspVO==null){
           return null;
       }
        record.setFileUrl(sysFileRspVO.getSkUrl());
        return record;
    }

    @Override
    public Result.ReturnData<List<WavLengthVO>> getwavlength(String tempId) {
        String tempPath = pathConfig.getTempPath() + tempId;
        List<WavLengthVO> list = new ArrayList<WavLengthVO>();
        File tempFile = new File(tempPath);
        if(!tempFile.exists()){
            logger.info("模板录音文件夹不存在[{}]",tempId);
            return Result.error(Constant.ERROR_CODE_NO_TEMP);
        }
        File[] fs = tempFile.listFiles();
        for (File f : fs) {
            WavLengthVO wavLengthVO =new WavLengthVO();
            String filename = f.getName();
            wavLengthVO.setFileName(filename);
            wavLengthVO.setLength(FileUtil.getWavDuration(tempPath+"/"+filename));
            list.add(wavLengthVO);
        }
        return Result.ok(list);
    }

}

