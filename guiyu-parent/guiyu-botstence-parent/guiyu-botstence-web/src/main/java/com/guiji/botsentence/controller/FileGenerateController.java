package com.guiji.botsentence.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.service.IFileGenerateService;
import com.guiji.botsentence.service.impl.BotSentenceTtsServiceImpl;
import com.guiji.botsentence.service.impl.FileGenerateServiceImpl;
import com.guiji.botsentence.service.impl.FileGenerateServiceImpl2;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.client.util.FtpUploadUtil;
import com.guiji.component.result.ServerResult;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/**
 * 
 * @Description 生成文件
 * @author liyang  
 * @date 2018年8月22日  
 *
 */
@RestController
public class FileGenerateController {
	
	@Autowired
	private FileGenerateServiceImpl fileGenerateService;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private FtpUploadUtil ftpUploadUtil; 
	
	//服务器路径
	@Value("${tts.sftp.path}")
	private String ftpPath;
	
	@Value("${local.upload.dir}")
	private String localUploadDir;
	
	
	
	@RequestMapping(value="fileGenerate")
	@Transactional
	public ServerResult fileGenerate(@JsonParam String processId, @RequestHeader String userId) throws IOException, SftpException, JSchException{
		// 获取流程对象
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();
		String dirName = DateUtil.getCurrentTime2() + "-" + templateId;
	    File file = fileGenerateService.fileGenerate(processId, dirName, null, userId);
	    
	    boolean result = fileGenerateService.autoDeploy(file, dirName, processId, templateId, userId);
	    file.delete();
	    if(result) {
	    	return ServerResult.createBySuccess();
	    }else {
	    	return ServerResult.createByError();
	    }
	    
		
	}

}
