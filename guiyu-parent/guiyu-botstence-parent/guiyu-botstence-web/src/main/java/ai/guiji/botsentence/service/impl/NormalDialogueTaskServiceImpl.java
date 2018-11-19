package ai.guiji.botsentence.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.guiji.botsentence.dao.entity.NormalDialogueTask;
import ai.guiji.botsentence.dao.entity.NormalDialogueTaskExample;
import ai.guiji.botsentence.service.INormalDialogueTaskService;
import ai.guiji.botsentence.dao.NormalDialogueTaskMapper;
import ai.guiji.botsentence.dao.entity.NormalDialogueTaskExample.Criteria;


@Service
public class NormalDialogueTaskServiceImpl implements INormalDialogueTaskService{

	@Autowired
	private NormalDialogueTaskMapper NormalDialogueTaskMapper;
	
	@Override
	public List<NormalDialogueTask> queryNormalDialogueListByPage(int pageSize, int pageNo, String processId) {
		
		NormalDialogueTaskExample example = new NormalDialogueTaskExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		
		List<NormalDialogueTask> list = NormalDialogueTaskMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public int countNormalDialogue(String processId) {
		
		NormalDialogueTaskExample example = new NormalDialogueTaskExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(processId)) {
			criteria.andProcessIdEqualTo(processId);
		}
		return NormalDialogueTaskMapper.countByExample(example);
	}

	@Override
	public int updateNormalDialogueTask(NormalDialogueTask normalDialogueTask) {
		
		return NormalDialogueTaskMapper.updateByPrimaryKeySelective(normalDialogueTask);
	}

}
