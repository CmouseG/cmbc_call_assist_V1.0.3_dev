package ai.guiji.botsentence.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.guiji.botsentence.dao.entity.NormalDialogueTask;
import ai.guiji.botsentence.service.INormalDialogueTaskService;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.exception.CommonException;
import ai.guiji.component.model.Page;
import ai.guiji.component.model.ServerResult;

/**
 * 
 * @Description:通用对话（过程）
 * @author liyang  
 * @date 2018年8月16日  
 *
 */
@RestController
@RequestMapping(value="normalDialogue")
public class NormalDialogueTaskController {
	
	
	@Autowired
	INormalDialogueTaskService normalDialogueTaskService;
	
	/**
	 *  初始化通用对话列表
	 */
	@RequestMapping(value="queryNormalDialogueListByPage")
	public ServerResult<Page<NormalDialogueTask>> queryNormalDialogueListByPage(@JsonParam int pageSize,
			@JsonParam int pageNo,@JsonParam String processId) {

		if(StringUtils.isBlank(processId)) {
			throw new CommonException("缺少参数！");
		}
		
		Page<NormalDialogueTask> page = new Page<NormalDialogueTask>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<NormalDialogueTask> results = normalDialogueTaskService.queryNormalDialogueListByPage(pageSize, pageNo, processId);
		
		int totalNum = normalDialogueTaskService.countNormalDialogue(processId);
		if(null != results) {
			page.setRecords(results);
			page.setTotal(totalNum);
		}
		return ServerResult.createBySuccess(page);
	}
	
	
	/**
	 * 修改通用对话信息
	 * @param
	 * @return
	 */
	@RequestMapping(value="/updateNormalDialogue")
	public ServerResult<Object> updateContentById(@JsonParam String dialogueId,@JsonParam String content){
		
		if(StringUtils.isBlank(dialogueId) || StringUtils.isBlank(content)) {
			throw new CommonException("缺少参数！");
		}
		
		NormalDialogueTask normalDialogueTask = new NormalDialogueTask();
		normalDialogueTask.setDialogueId(dialogueId);
		normalDialogueTask.setContent(content);
		
		normalDialogueTaskService.updateNormalDialogueTask(normalDialogueTask);
		
		return ServerResult.createBySuccess();
	}
	
}
