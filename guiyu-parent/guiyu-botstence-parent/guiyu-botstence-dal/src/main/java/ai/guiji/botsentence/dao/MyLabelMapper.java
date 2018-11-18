package ai.guiji.botsentence.dao;

import java.util.List;
import java.util.Map;

import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabelScore;

public interface MyLabelMapper {

	List<MyBotLabel> getKeysList(String processId);

	int updateByPrimaryKeySelective(BotSentenceLabel botSentenceLabel);

	
}