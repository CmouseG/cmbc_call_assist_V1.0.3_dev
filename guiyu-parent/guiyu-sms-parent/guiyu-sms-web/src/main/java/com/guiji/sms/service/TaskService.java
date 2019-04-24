package com.guiji.sms.service;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.TaskListRsp;
import com.guiji.sms.controller.bean.TaskReq;

public interface TaskService
{

	/**
	 * 获取任务列表
	 */
	TaskListRsp queryTaskList(Condition condition, AuthLevelData authLevelData);

	/**
	 * 新增短信群发任务
	 */
	void addTask(TaskReq taskReq, Long userId, String orgCode);

	/**
	 * 编辑短信群发任务
	 */
	void updateTask(TaskReq taskReq, Long userId);
	
	/**
	 * 删除任务
	 */
	void delTask(Integer id);
	
	/**
	 * 审核任务
	 */
	void auditingTask(Integer id, Long userId);

	/**
	 * 任务一键停止
	 */
	void stopTask(Integer id, Long userId);

	/**
	 * 任务一键启动
	 */
	void startTask(Integer id, Long userId);

}
