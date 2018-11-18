package ai.guiji.botsentence.dao.ext;

import ai.guiji.botsentence.dao.entity.BotSentenceProcess;

public interface ImportProcessMapper {

	String getProcessId();

	int insertSelective(BotSentenceProcess botSentenceProcess);

	
}