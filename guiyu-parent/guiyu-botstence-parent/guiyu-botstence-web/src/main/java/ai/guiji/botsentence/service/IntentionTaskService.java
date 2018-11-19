package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.IntentionTask;
import ai.guiji.component.client.config.JsonParam;

/**
 * 
* @ClassName: IntentionTaskService
* @Description: 意向标签后台逻辑服务类
* @author: 张朋
* @date 2018年8月14日 下午4:36:02 
* @version V1.0
 */
public interface IntentionTaskService {

	public List<IntentionTask> queryIntentionList(String processId);
	
	public List<IntentionTask> queryIntentionListByPage(String processId, int pageSize, int pageNo);
	
	public int countIntentionNum(String processId);
	
	public void updateIntention(String intentionId, String dialogue_times, String keyWords,Long userId);
		
}
