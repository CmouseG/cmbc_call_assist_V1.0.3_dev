package com.guiji.sms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.TaskListRsp;
import com.guiji.sms.controller.bean.TaskReq;
import com.guiji.sms.dao.SmsTaskMapper;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.dao.entity.SmsTaskExample;
import com.guiji.sms.dao.entity.SmsTaskExample.Criteria;
import com.guiji.sms.rabbitmq.DirectSender;
import com.guiji.sms.service.TaskService;
import com.guiji.sms.utils.AuthUtil;
import com.guiji.sms.utils.FileUtil;
import com.guiji.sms.utils.JsonUtil;
import com.guiji.utils.RedisUtil;

@Service
public class TaskServiceImpl implements TaskService
{
	@Autowired
	SmsTaskMapper taskMapper;
	@Autowired
	DirectSender sender;
	@Autowired
	AuthUtil authUtil;
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 获取任务列表
	 */
	@Override
	public TaskListRsp queryTaskList(Condition condition, AuthLevelData authLevelData)
	{
		TaskListRsp rsp = new TaskListRsp();
		SmsTaskExample example = new SmsTaskExample();
		Criteria criteria = example.createCriteria();
		Integer authLevel = authLevelData.getAuthLevel();
		if(authLevel == 1) {
			criteria.andCreateIdEqualTo(authLevelData.getUserId().intValue());
		} else if(authLevel == 2) {
			criteria.andOrgCodeEqualTo(authLevelData.getOrgCode());
		}else if(authLevel == 3) {
			criteria.andOrgCodeLike(authLevelData.getOrgCode() + "%");
		}
		if(StringUtils.isNotEmpty(condition.getTaskName())){
			criteria.andTaskNameLike(condition.getTaskName()+"%");
		}
		if(condition.getTaskStatus() != null){
			criteria.andSendStatusEqualTo(condition.getTaskStatus());
		}
		if(StringUtils.isNotEmpty(condition.getStartDate())){
			criteria.andSendTimeGreaterThanOrEqualTo(condition.getStartDate());
		}
		if(StringUtils.isNotEmpty(condition.getEndDate())){
			criteria.andSendTimeLessThanOrEqualTo(condition.getEndDate());
		}
		rsp.setTotalNum(taskMapper.countByExample(example));
		example.setLimitStart((condition.getPageNum()-1)*condition.getPageSize());
		example.setLimitEnd(condition.getPageSize());
		example.setOrderByClause("id desc");
		rsp.setRecords(taskMapper.selectByExampleWithBLOBs(example));
		return rsp;
	}

	/**
	 * 新增短信群发任务
	 */
	@Override
	public void addTask(TaskReq taskReq, Long userId, String orgCode)
	{
		SmsTask smsTask = new SmsTask();
		setTaskParams(smsTask, taskReq);
		List<String> phoneList = FileUtil.parseExcelFile(taskReq.getFile());
		smsTask.setPhoneNum(phoneList.size());
		smsTask.setOrgCode(orgCode);
		smsTask.setOrgName(authUtil.getOrgNameByOrgCode(orgCode));
		smsTask.setCreateId(userId.intValue());
		smsTask.setCreateName(authUtil.getUserNameByUserId(userId));
		smsTask.setCreateTime(new Date());
		taskMapper.insertSelective(smsTask);
		redisUtil.set("TASK_PHONES_"+smsTask.getId(), phoneList);
	}
	
	private void setTaskParams(SmsTask smsTask, TaskReq taskReq)
	{
		Integer sendType = taskReq.getSendType();
		smsTask.setTaskName(taskReq.getTaskName());
		smsTask.setTunnelName(taskReq.getTunnelName());
		smsTask.setSendType(sendType);
		smsTask.setContentType(taskReq.getContentType());
		smsTask.setSmsContent(taskReq.getSmsContent());
		smsTask.setAuditingStatus(0); // 编辑后需要重新审核
		if(sendType == 1){ // 立即发送，没有发送时间（发送时间即审核时间），发送时间不展示，把原值覆盖
			smsTask.setSendTime(null);
		}else{ // 定时发送，发送时间展示
			smsTask.setSendTime(taskReq.getSendTime());
		}
	}

	/**
	 * 编辑短信群发任务
	 */
	@Override
	public void updateTask(TaskReq taskReq, Long userId)
	{
		SmsTask smsTask = taskMapper.selectByPrimaryKey(taskReq.getId());
		setTaskParams(smsTask, taskReq);
		smsTask.setUpdateId(userId.intValue());
		smsTask.setUpdateName(authUtil.getUserNameByUserId(userId));
		smsTask.setUpdateTime(new Date());
		taskMapper.updateByPrimaryKeyWithBLOBs(smsTask);
	}
	
	/**
	 * 删除任务
	 */
	@Override
	public void delTask(Integer id)
	{
		taskMapper.deleteByPrimaryKey(id);
		redisUtil.del("TASK_PHONES_"+id);
	}
	
	/**
	 * 审核任务
	 */
	@Override
	public void auditingTask(Integer id, Long userId)
	{
		SmsTask smsTask = taskMapper.selectByPrimaryKey(id);
		smsTask.setAuditingStatus(1);
		smsTask.setUpdateId(userId.intValue());
		smsTask.setUpdateName(authUtil.getUserNameByUserId(userId));
		smsTask.setUpdateTime(new Date());
		taskMapper.updateByPrimaryKey(smsTask);
		// 立即发送（定时发送的任务等待扫描） && 停止的任务不发送
		if(smsTask.getSendType() == 1 && smsTask.getRunStatus() == 1)
		{
			// 发送到任务队列
			sender.send("SendMessageTaskMQ.direct.Sms", JsonUtil.bean2JsonStr(smsTask));
		}
		
	}
	
	/**
	 * 任务一键停止
	 */
	@Override
	public void stopTask(Integer id, Long userId)
	{
		SmsTask smsTask = taskMapper.selectByPrimaryKey(id);
		smsTask.setRunStatus(0);
		smsTask.setUpdateId(userId.intValue());
		smsTask.setUpdateName(authUtil.getUserNameByUserId(userId));
		smsTask.setUpdateTime(new Date());
		taskMapper.updateByPrimaryKey(smsTask);
	}

	/**
	 * 任务一键启动
	 */
	@Override
	public void startTask(Integer id, Long userId)
	{
		SmsTask smsTask = taskMapper.selectByPrimaryKey(id);
		smsTask.setRunStatus(1);
		smsTask.setUpdateId(userId.intValue());
		smsTask.setUpdateName(authUtil.getUserNameByUserId(userId));
		smsTask.setUpdateTime(new Date());
		taskMapper.updateByPrimaryKey(smsTask);
		// 立即发送（定时发送的任务等待扫描） && 未审核的任务不发送
		if(smsTask.getSendType() == 1 && smsTask.getAuditingStatus() == 1)
		{
			// 发送到任务队列
			sender.send("SendMessageTaskMQ.direct.Sms", JsonUtil.bean2JsonStr(smsTask));
		}
	}

}
