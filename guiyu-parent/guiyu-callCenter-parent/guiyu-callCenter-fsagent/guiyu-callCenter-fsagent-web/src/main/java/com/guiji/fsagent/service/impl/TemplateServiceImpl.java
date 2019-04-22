package com.guiji.fsagent.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
import com.guiji.fsagent.config.PathConfig;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.TtsWav;
import com.guiji.fsagent.entity.WavLengthVO;
import com.guiji.fsagent.eventbus.SimpleEventSender;
import com.guiji.fsagent.service.TemplateService;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.utils.NasUtil;
import com.guiji.utils.NetFileDownUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    PathConfig pathConfig;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    IRobotRemote iRobotFeign;

    @Autowired
    SimpleEventSender simpleEventSender;


    @Override
    public boolean istempexist(String tempId) {
        tempId = tempId.substring(0,tempId.lastIndexOf("_")+1) ;
        String tempPath = pathConfig.getTempPath() + tempId+"en";
        return FileUtil.isExist(tempPath);
    }

    @Override
    public List<TtsWav> downloadttswav(String tempId, String planUuid, String callId) {
        String tempRec = tempId.substring(0,tempId.length()-3)+"_rec";
        //下载tts录音
        TtsVoiceReq  ttsVoiceReq = new TtsVoiceReq();
        ttsVoiceReq.setTemplateId(tempId);
        ttsVoiceReq.setSeqid(planUuid);
        Result.ReturnData<TtsComposeCheckRsp> result = iRobotFeign.ttsCompose(ttsVoiceReq);
        logger.info("ttsCompose返回结果 result[{}]",result);
        if(!result.getCode().equals("0")){
            logger.warn("下载tts录音，获取录音URL失败:"+result.getCode());
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_TTS_DOWNLOAD);
        }
        TtsComposeCheckRsp ttsComposeCheckRsp = result.getBody();
        List<TtsVoice> list = ttsComposeCheckRsp.getTtsVoiceList();
        if(list!=null && list.size()>0){
            File ttsDir = new File(pathConfig.getTempPath()+tempId+"/"+tempRec+"/tts/"+callId);  // 创建tts文件夹
            if (!ttsDir.exists()) {//文件不存在则创建
                ttsDir.mkdirs();
            }
            List<TtsWav> returnList = new ArrayList();
            for (TtsVoice ttsVoice:list) {
                String filePath = pathConfig.getTempPath()+tempId+"/"+tempRec+"/tts/"+callId+"/"+ttsVoice.getTtsKey()+".wav";
                File ttsVoiceFile = new File(filePath);
                NetFileDownUtil util = new NetFileDownUtil(ttsVoice.getTtsUrl(),ttsVoiceFile);
                try {
                    util.downfile();
                    //获取文件时长
                    Double fileDuration = FileUtil.getWavDuration(filePath);
                    if(fileDuration!=null){
                        TtsWav ttsWav = new TtsWav();
                        ttsWav.setFileDuration(fileDuration);
                        ttsWav.setFileName(ttsVoice.getTtsKey()+".wav");
                        returnList.add(ttsWav);
                    }
                } catch (IOException e) {
                    logger.info("下载tts录音失败，失败的文件为：[{}]==》错误的原因为：[{}]",ttsVoice.getTtsUrl(), e);
                }
            }
            return returnList;
        }
        return null;
    }

//    @Override
//    public RecordVO uploadrecord(RecordReqVO recordReqVO) {
//        RecordVO record = new RecordVO();
//        record.setFileName(recordReqVO.getFileName());
//        SysFileReqVO sysFileReqVO = new SysFileReqVO();
//        sysFileReqVO.setBusiId(recordReqVO.getBusiId());
//        sysFileReqVO.setBusiType(recordReqVO.getBusiType());
//        sysFileReqVO.setSysCode(recordReqVO.getSysCode());
//        sysFileReqVO.setUserId(recordReqVO.getUserId());
//        sysFileReqVO.setThumbImageFlag("0");
//        String uploadOriginalFile = fsConfig.getHomeDir()+"/recordings/" + record.getFileName();//原文件路径
//        String uploadTempFile =  fsConfig.getHomeDir()+"/recordings/temp_" + record.getFileName();//截取后的文件路径
//        String uploadOFile; //要上传的文件
//        if(FileUtil.isExist(uploadTempFile)) { //如果截取后的文件存在，则上传截取后的文件
//            logger.info("上传截取后的录音文件：[{}]",uploadTempFile);
//            uploadOFile = uploadTempFile;
//        }else{
//            if(!FileUtil.isExist(uploadOriginalFile)) {//如果源文件也不存在，直接抛出异常
//                logger.info("上传录音失败,录音文件不存在，文件名为:[{}]",uploadOriginalFile);
//                throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_RECORDING_NOTEXIST);
//            }
//            //如果截取文件不存在，源文件存在，则上传源文件
//            logger.info("上传原录音文件：[{}]",uploadOriginalFile);
//            uploadOFile = uploadOriginalFile;
//        }
//        SysFileRspVO sysFileRspVO = new NasUtil().uploadNas(sysFileReqVO, new File(uploadOFile));
//        if(sysFileRspVO==null){
//            logger.info("上传录音失败,失败的文件为:[{}]",uploadOFile);
//            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_UPLOAD_ERROR);
//       }
//        FileUtil.delete(uploadOFile);
//        record.setFileUrl(sysFileRspVO.getSkUrl());
//        return record;
//    }

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
            if(FileUtil.getWavDuration(tempPath+"/"+filename)!=null){
                wavLengthVO.setLength(FileUtil.getWavDuration(tempPath+"/"+filename));
            }else{
                wavLengthVO.setLength(0);
            }
            list.add(wavLengthVO);
        }
        return list;
    }

/*
    @Override
    public void uploadrecord2(RecordReqVO recordReqVO) {
        simpleEventSender.sendEvent(recordReqVO);
    }
*/


}

