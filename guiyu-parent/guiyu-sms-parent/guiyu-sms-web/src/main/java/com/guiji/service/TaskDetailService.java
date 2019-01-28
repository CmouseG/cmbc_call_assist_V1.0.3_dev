package com.guiji.service;

import java.text.ParseException;
import java.util.List;

import com.guiji.model.TaskReq;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.sms.vo.TaskDetailListReqVO;
import com.guiji.sms.vo.TaskDetailListRspVO;

public interface TaskDetailService
{

	/**
	 * 获取短信任务详情列表
	 * @throws ParseException 
	 */
	TaskDetailListRspVO getTaskDetailList(TaskDetailListReqVO taskDetailListReq, Long userId) throws ParseException;

	/**
	 * 删除短信任务详情
	 */
	void delTaskDetail(Integer id);

	/**
	 * 保存短信任务详情
	 */
	void saveTaskDetail(List<SmsRecord> records, TaskReq taskReq);

}
