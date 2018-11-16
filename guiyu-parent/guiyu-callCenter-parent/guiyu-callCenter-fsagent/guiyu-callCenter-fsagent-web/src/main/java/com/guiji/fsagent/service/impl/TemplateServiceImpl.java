package com.guiji.fsagent.service.impl;

import com.guiji.component.result.Result;
import com.guiji.fsagent.config.PathConfig;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.service.TemplateService;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.nas.api.INas;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;
import com.guiji.robot.api.IRobotRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    PathConfig pathConfig;
//    @Autowired
//    INas iNasFeign;
//    @Autowired
//    IRobotRemote iRobotFeign;
    @Override
    public Boolean istempexist(String tempId) {
       String tempPath =  pathConfig.getTempPath()+tempId;
        return FileUtil.isExist(tempPath);
    }

    @Override
    public Boolean downloadbotwav(String tempId) {
      String tempRecordPath = pathConfig.getTempRecordPath()+tempId;
        //判断模板录音是否已存在，存在则返回
        if(FileUtil.isExist(tempRecordPath)){
            return true;
        };
        //下载模板录音
       // iRobotFeign.getVoiceResource();
        return true;
    }

    @Override
    public Result.ReturnData<Boolean> downloadttswav(String tempId, String callId) {
        //下载tts录音
       // iRobotFeign.();
        return null;
    }

    @Override
    public Result.ReturnData<RecordVO> uploadrecord(RecordReqVO recordReqVO) {
        RecordVO record = new RecordVO();
        record.setFileName(recordReqVO.getFileName());
        SysFileReqVO sysFileReqVO = new SysFileReqVO();
        sysFileReqVO.setBusiId(recordReqVO.getBusiId());
        sysFileReqVO.setBusiType(recordReqVO.getBusiType());
        sysFileReqVO.setSysCode(recordReqVO.getSysCode());
        sysFileReqVO.setThumbImageFlag("0");
        FileUtil util = new FileUtil();
//        Result.ReturnData<SysFileRspVO> result = null;
//        try {
//            result = iNasFeign.uploadFile(sysFileReqVO,util.toMultipartFile(pathConfig.getTempRecordPath()+record.getFileName()),1L);
//        } catch (IOException e) {
//            logger.info("上传录音失败，file转MultipartFile出错",e);
//            return Result.error("");
//        }
//
//        SysFileRspVO sysFileRspVO = result.getBody();
//        if( sysFileRspVO==null){
//        }
//        record.setFileUrl(sysFileRspVO.getSkUrl());
        record.setFileUrl("http://198.12.23.12:8080");
       return  Result.ok(record);
    }
}
