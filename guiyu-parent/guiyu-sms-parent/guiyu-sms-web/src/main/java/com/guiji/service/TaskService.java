package com.guiji.service;

import java.util.List;

import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.vo.TaskListReqVO;
import com.guiji.sms.vo.TaskListRspVO;
import com.guiji.sms.vo.TaskReqVO;

public interface TaskService
{
	/**
	 * 获取短信任务列表
	 * @throws Exception 
	 */
	TaskListRspVO getTaskList(TaskListReqVO taskListReq) throws Exception;

	/**
	 * 短信任务一键停止
	 */
	void stopTask(Integer id);

	/**
	 * 删除短信任务
	 */
	void delTask(Integer id);

	/**
	 * 新增/编辑短信任务
	 * @param userId 
	 * @throws Exception 
	 */
	void addTask(TaskReqVO taskReq, Long userId) throws Exception;

	/**
	 * 审核短信任务
	 */
	void auditingTask(Integer id);

	/**
	 * 获取定时任务
	 */
	List<SmsTask> getTimeTaskList();

	/**
	 * 更新发送状态
	 */
	void updateSendStatusById(Integer sendStatus, Integer id);

	/**
	 * 编辑短信任务
	 */
	void updateTask(TaskReqVO taskReqVO, Long userId) throws Exception;
}
