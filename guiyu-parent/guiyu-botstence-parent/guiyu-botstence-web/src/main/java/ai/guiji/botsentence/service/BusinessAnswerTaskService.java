package ai.guiji.botsentence.service;

import java.util.List;

import ai.guiji.botsentence.dao.entity.BusinessAnswerTask;
import ai.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import ai.guiji.botsentence.vo.BusinessAnswerVo;


public interface BusinessAnswerTaskService {

	public List<BusinessAnswerTaskExt> queryBusinessAnswerListByPage(String processId);
	
	public int countBusinessAnswerNum(String processId);
	
	public void addBusinessAnswer(BusinessAnswerVo param,Long userId);
	
	public void delBusinessAnswer(String answerId,Long userId);
	
	public void updateBusinessAnswer(BusinessAnswerVo record,Long userId);
}
