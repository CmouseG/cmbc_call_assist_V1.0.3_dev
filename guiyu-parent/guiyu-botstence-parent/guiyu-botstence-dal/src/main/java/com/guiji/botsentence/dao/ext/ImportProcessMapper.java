package com.guiji.botsentence.dao.ext;

import com.guiji.botsentence.dao.entity.BotSentenceProcess;

public interface ImportProcessMapper {

	String getProcessId();

	int insertSelective(BotSentenceProcess botSentenceProcess);

	
}