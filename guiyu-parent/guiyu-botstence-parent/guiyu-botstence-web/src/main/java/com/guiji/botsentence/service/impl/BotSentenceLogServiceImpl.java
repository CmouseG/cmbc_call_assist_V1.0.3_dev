package com.guiji.botsentence.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.botsentence.dao.BotSentenceLogMapper;
import com.guiji.botsentence.dao.entity.BotSentenceLog;
import com.guiji.botsentence.service.IBotSentenceLogService;

@Service
public class BotSentenceLogServiceImpl implements IBotSentenceLogService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceLogServiceImpl.class);
	
	@Autowired
	private BotSentenceLogMapper botSentenceLogMapper;
	
	@Override
	public void saveLog(String operator, String userId) {
		logger.info("保存操作日志: " + operator);
		if(StringUtils.isNotBlank(operator)) {
			BotSentenceLog log = new BotSentenceLog();
			log.setCrtUser(userId);
			log.setCrtTime(new Date(System.currentTimeMillis()));
			log.setOperator(operator);
			botSentenceLogMapper.insert(log);
		}
	}

}
