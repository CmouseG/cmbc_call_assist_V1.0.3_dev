package com.guiji.botsentence.service.impl;

import com.google.common.base.Strings;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.manager.IAIManager;
import com.guiji.botsentence.service.ISelfTestService;
import com.guiji.botsentence.util.CommonUtil;
import com.guiji.botsentence.vo.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/9 15:30
 * @Project：guiji-parent
 * @Description:
 */
@Slf4j
@Service
public class ISelfTestServiceImpl implements ISelfTestService {
    @Autowired
    IAIManager iaiManager;
    
    @Autowired
    BotSentenceLogServiceImpl botSentenceLogService;
    
    @Autowired
    VoliceServiceImpl voliceService;
    
    @Autowired
    BotSentenceProcessMapper botSentenceProcessMapper;
    
    @Autowired
    BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
    
    @Autowired
    BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;

    @Override
    public ResponseSelfTestVO makeTest(SelfTestVO request, String userId) throws Exception {
        ResponseSelfTestVO responseSelfTestVO = new ResponseSelfTestVO();
        log.info("开始发起自测[{}]", request);
        AIResponseVO responseVO;

        if(StringUtils.isNotBlank(request.getChannel())) {
        	if(Constant.CHANNEL_WECHAT.equals(request.getChannel())) {
        		botSentenceLogService.saveLog(Constant.OPERATOR_PHONE_TEST_BY_WECHAT, userId);
        	}else if(Constant.CHANNEL_WEB.equals(request.getChannel())) {
        		botSentenceLogService.saveLog(Constant.OPERATOR_PHONE_TEST_BY_WEB, userId);
        	}
        }else {
        	botSentenceLogService.saveLog(Constant.OPERATOR_PHONE_TEST, userId);
        }
        
        
        String uuid = request.getUuid();
        if(Strings.isNullOrEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            log.info("这个是第一次发起的自测请求，生成的uuid是[{}]", uuid);
            //开始发起restore请求
            responseVO = iaiManager.restore(new AIRestoreRequestVO(uuid, request.getTempId(), CommonUtil.getRandPhoneNum()));
        }else{
            if(!iaiManager.isUuidExist(uuid)){
                log.info("当前收到的uuid在系统中并不存在[{}], 按照restore来处理", uuid);
                responseVO = iaiManager.restore(new AIRestoreRequestVO(uuid, request.getTempId(), CommonUtil.getRandPhoneNum()));
            }else{
                //发起hello请求
                responseVO = iaiManager.hello(new AIHelloRequestVO(uuid, request.getSentence(), ""));
            }
        }

        BeanUtils.copyProperties(responseVO, responseSelfTestVO, CommonUtil.getNullPropertyNames(responseVO));
        responseSelfTestVO.setAnswerTxt(responseVO.getAnswer());
        responseSelfTestVO.setUuid(uuid);
        responseSelfTestVO.setWavFileUrl(responseVO.getWav_filename());
        responseSelfTestVO.setWavDuration(0);
        
        //根据音频文件名获取相应的URL
        String processId = request.getProcessId();
        int index = responseVO.getWav_filename().lastIndexOf("/");
		String wavName = responseVO.getWav_filename().substring(index+1, responseVO.getWav_filename().length());
		wavName = wavName.replace(".wav", "");
        if(StringUtils.isNotBlank(wavName) && wavName.indexOf("_") > 0) {
        	if(responseVO.getWav_filename().indexOf("_backup") > 0) {
        		log.info("当前是备用话术");
        		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
				backupExample.createCriteria().andProcessIdEqualTo(processId).andWavNameEqualTo(wavName);
				List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
				if(null != backupList && backupList.size() > 0) {
					if(StringUtils.isNotBlank(backupList.get(0).getUrl())) {
						if(null != backupList.get(0).getTimes() && backupList.get(0).getTimes() > 0) {
							responseSelfTestVO.setWavDuration(backupList.get(0).getTimes());
						}
						
						if(null == backupList.get(0).getTimes() || backupList.get(0).getTimes() == 0) {
	            			responseSelfTestVO.setWavDuration(backupList.get(0).getContent().length()/5);
	            		}
						if(responseSelfTestVO.getWavDuration() == 0) {
	            			responseSelfTestVO.setWavDuration(1);
	            		}
					}
					responseSelfTestVO.setHttpFileUrl(backupList.get(0).getUrl());
				}
        	}else {
        		log.info("当前是tts断句");
        		BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
				ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andWavNameEqualTo(wavName);
				List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
				if(null != taskList && taskList.size() > 0) {
					if(StringUtils.isNotBlank(taskList.get(0).getVoliceUrl())) {
						if(null != taskList.get(0).getTimes() && taskList.get(0).getTimes() > 0) {
							responseSelfTestVO.setWavDuration(taskList.get(0).getTimes());
						}
						
						if(null == taskList.get(0).getTimes() || taskList.get(0).getTimes() == 0) {
	            			responseSelfTestVO.setWavDuration(taskList.get(0).getContent().length()/5);
	            		}
						if(responseSelfTestVO.getWavDuration() == 0) {
	            			responseSelfTestVO.setWavDuration(1);
	            		}
					}
					responseSelfTestVO.setHttpFileUrl(taskList.get(0).getVoliceUrl());
				}
        	}
        }else {
        	VoliceInfo volice = voliceService.getVoliceByWavName(request.getTempId(), responseVO.getWav_filename());
            if(null != volice && StringUtils.isNotBlank(volice.getVoliceUrl())) {
            	if(null == volice.getTimes()) {
            		if(StringUtils.isBlank(volice.getContent())) {
            			responseSelfTestVO.setWavDuration(1);
            		}else {
            			responseSelfTestVO.setWavDuration(volice.getContent().length()/5);
            		}
            	}else {
            		responseSelfTestVO.setWavDuration(volice.getTimes());
            	}
            	responseSelfTestVO.setHttpFileUrl(volice.getVoliceUrl());
            }
        }
        
        if(responseSelfTestVO.getEnd() > 0) {
        	log.info("sellbot返回挂断");
        	log.info(responseSelfTestVO.getEnd() + "");
        	this.endTest(uuid);
        }

        return responseSelfTestVO;
    }

    @Override
    public void endTest(String uuid){
        log.info("开始结束会话测试，seqid[{}]",uuid);
        iaiManager.end(uuid);
    }
    
    public static void main(String[] args) {
		System.out.println(59/5);
	}
}
