package ai.guiji.botsentence.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.guiji.botsentence.dao.IntentionTaskMapper;
import ai.guiji.botsentence.dao.entity.IntentionTask;
import ai.guiji.botsentence.dao.entity.IntentionTaskExample;
import ai.guiji.botsentence.service.IntentionTaskService;
import ai.guiji.component.exception.CommonException;

@Service
public class IntentionTaskServiceImpl implements IntentionTaskService {

	@Autowired
	private IntentionTaskMapper intentionTaskMapper;
	
	/**
	 * 根据话术流程编号查询当前话术流程的意向标签信息
	 */
	@Override
	public List<IntentionTask> queryIntentionList(String processId) {
		IntentionTaskExample example = new IntentionTaskExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		return intentionTaskMapper.selectByExample(example);
	}

	@Override
	public int countIntentionNum(String processId) {
		IntentionTaskExample example = new IntentionTaskExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		return intentionTaskMapper.countByExample(example);
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<IntentionTask> queryIntentionListByPage(String processId, int pageSize, int pageNo) {
		IntentionTaskExample example = new IntentionTaskExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		
		return intentionTaskMapper.selectByExample(example);
	}

	@Override
	public void updateIntention(String intentionId, String dialogueTimes, String keyWords,Long userId) {
		if(StringUtils.isBlank(intentionId) || StringUtils.isBlank(dialogueTimes) || StringUtils.isBlank(keyWords)) {
			throw new CommonException("请求参数为空!");
		}
		IntentionTask intentionTask = intentionTaskMapper.selectByPrimaryKey(intentionId);
		if(null == intentionTask) {
			throw new CommonException("当前意向信息不存在!");
		}
		intentionTask.setDialogueTimes(new Integer(dialogueTimes));
		intentionTask.setKeyWords(keyWords);
		intentionTask.setLstUpdateTime(new Date(System.currentTimeMillis()));
		intentionTask.setLstUpdateUser(userId.toString());
		intentionTaskMapper.updateByPrimaryKey(intentionTask);
	}

}
