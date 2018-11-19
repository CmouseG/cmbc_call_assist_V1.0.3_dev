package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;

/**
 * 
* @ClassName: IBotSentenceBranchService
* @Description: branch相关服务类
* @author: 张朋
* @date 2018年8月22日 下午4:36:02
* @version V1.0
 */
public interface IBotSentenceBranchService {

	public List<BotSentenceProcess> queryDomainList(String processId, String category); 
	
	public List<BotSentenceBranch> queryBusinessAnswerTaskListBotSentenceBranch(String processId);
}
