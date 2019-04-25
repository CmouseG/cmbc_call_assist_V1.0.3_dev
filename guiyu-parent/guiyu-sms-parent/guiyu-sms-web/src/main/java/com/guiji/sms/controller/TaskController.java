package com.guiji.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.TaskListRsp;
import com.guiji.sms.controller.bean.TaskReq;
import com.guiji.sms.service.TaskService;

@RestController
public class TaskController
{
	@Autowired
	TaskService taskService;
	
	/**
	 * 获取任务列表
	 */
	@PostMapping("queryTaskList")
	public ReturnData<TaskListRsp> queryTaskList(@RequestBody Condition condition,
											 @RequestHeader Long userId, 
											 @RequestHeader Integer authLevel, 
											 @RequestHeader String orgCode)
	{
		AuthLevelData authLevelData = new AuthLevelData(userId, authLevel, orgCode);
		return Result.ok(taskService.queryTaskList(condition,authLevelData));
	}
	
	/**
	 * 新增短信群发任务
	 */
	@Jurisdiction("smsCenter_smsGroupTask_add")
	@PostMapping("addTask")
	public void addTask(TaskReq taskReq,
					@RequestHeader Long userId,
					@RequestHeader String orgCode)
	{
		taskService.addTask(taskReq,userId,orgCode);
	}
	
	/**
	 * 编辑短信群发任务（只有未发送的任务可以编辑）
	 */
	@Jurisdiction("smsCenter_smsGroupTask_edit")
	@PostMapping("updateTask")
	public void updateTask(TaskReq taskReq,
						@RequestHeader Long userId)
	{
		taskService.updateTask(taskReq,userId);
	}
	
	/**
	 * 删除任务
	 */
	@Jurisdiction("smsCenter_smsGroupTask_delete")
	@GetMapping("delTask")
	public void delTask(Integer id)
	{
		taskService.delTask(id);
	}
	
	/**
	 * 审核任务
	 */
	@Jurisdiction("smsCenter_smsGroupTask_auditing")
	@GetMapping("auditingTask")
	public void auditingTask(Integer id,
					@RequestHeader Long userId)
	{
		taskService.auditingTask(id,userId);
	}
	
	/**
	 * 任务一键停止（只有未发送的任务可以停止）
	 */
	@Jurisdiction("smsCenter_smsGroupTask_onKeyStopTask")
	@GetMapping("stopTask")
	public void stopTask(Integer id,
					@RequestHeader Long userId)
	{
		taskService.stopTask(id,userId);
	}
	
	/**
	 * 任务一键启动
	 * 立即发送的任务，启动后发送
	 * 定时任务若超时，则不再发送，若未超时，则正常扫描
	 */
	@GetMapping("startTask")
	public void startTask(Integer id,
					@RequestHeader Long userId)
	{
		taskService.startTask(id,userId);
	}
	
}
