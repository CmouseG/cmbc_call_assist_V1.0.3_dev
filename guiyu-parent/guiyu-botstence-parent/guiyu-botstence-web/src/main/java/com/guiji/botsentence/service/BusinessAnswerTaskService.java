package com.guiji.botsentence.service;

import java.util.List;
import com.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import com.guiji.botsentence.vo.BusinessAnswerVo;


public interface BusinessAnswerTaskService {

	public List<BusinessAnswerTaskExt> queryBusinessAnswerListByPage(String processId);
	
	public int countBusinessAnswerNum(String processId);
	
	public void addBusinessAnswer(BusinessAnswerVo param, String userId);
	
	public void delBusinessAnswer(String answerId, String userId);
	
	public void updateBusinessAnswer(BusinessAnswerVo record, String userId);
	
	public List<BusinessAnswerTaskExt> queryBusinessAnswerList(String processId);
	
}
