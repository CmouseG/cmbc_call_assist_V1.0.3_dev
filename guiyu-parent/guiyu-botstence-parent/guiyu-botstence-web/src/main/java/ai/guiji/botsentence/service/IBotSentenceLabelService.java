package ai.guiji.botsentence.service;

import java.util.List;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabelScore;

public interface IBotSentenceLabelService {

	List<MyBotLabel> getKeysList(String processId);

	int updateBotLabelVO(BotSentenceLabel botSentenceLabel);

	
}
