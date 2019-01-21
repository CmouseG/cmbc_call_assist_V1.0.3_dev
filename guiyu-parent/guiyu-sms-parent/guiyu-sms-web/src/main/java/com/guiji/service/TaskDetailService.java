package com.guiji.service;

import java.text.ParseException;

import com.guiji.model.TaskReq;
import com.guiji.sms.vo.TaskDetailListReqVO;
import com.guiji.sms.vo.TaskDetailListRspVO;

public interface TaskDetailService
{

	/**
	 * 获取短信任务详情列表
	 * @throws ParseException 
	 */
	TaskDetailListRspVO getTaskDetailList(TaskDetailListReqVO taskDetailListReq) throws ParseException;

	/**
	 * 删除短信任务详情
	 */
	void delTaskDetail(Integer id);

	/**
	 * 保存短信任务详情
	 */
	void saveTaskDetail(String statusCode, TaskReq taskReq, String phone);

}
