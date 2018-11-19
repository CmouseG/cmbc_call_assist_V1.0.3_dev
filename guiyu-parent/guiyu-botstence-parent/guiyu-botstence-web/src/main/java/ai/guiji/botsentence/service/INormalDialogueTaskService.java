package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.NormalDialogueTask;

/**
 * 
 * 通用对话
 *
 */
public interface INormalDialogueTaskService {

	/**
	 * 查询通用对话列表
	 * @param pageSize
	 * @param pageNo
	 * @param processId
	 * @return
	 */
	List<NormalDialogueTask> queryNormalDialogueListByPage(int pageSize, int pageNo, String processId);

	/**
	 * 查询总数
	 * @param processId
	 * @return
	 */
	int countNormalDialogue(String processId);


	int updateNormalDialogueTask(NormalDialogueTask normalDialogueTask);


}
