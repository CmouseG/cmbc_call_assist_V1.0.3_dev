package com.guiji.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.entity.SmsExceptionEnum;
import com.guiji.model.TaskReq;
import com.guiji.service.SendSmsService;
import com.guiji.service.TaskService;
import com.guiji.sms.dao.SmsTaskMapper;
import com.guiji.sms.dao.SmsTaskMapperExt;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.dao.entity.SmsTaskExample;
import com.guiji.sms.dao.entity.SmsTaskExample.Criteria;
import com.guiji.sms.vo.TaskListReqVO;
import com.guiji.sms.vo.TaskListRspVO;
import com.guiji.sms.vo.TaskReqVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.ParseFileUtil;
import com.guiji.utils.RedisUtil;

@Service
public class TaskServiceImpl implements TaskService
{
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	
	@Autowired
	IAuth iAuth;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	SendSmsService sendSmsService;
	@Autowired
	SmsTaskMapper taskMapper;
	@Autowired
	SmsTaskMapperExt taskMapperExt;

	/**
	 * 获取短信任务列表
	 * @throws Exception 
	 */
	@Override
	public TaskListRspVO getTaskList(TaskListReqVO taskListReq) throws Exception
	{
		TaskListRspVO taskListRsp = new TaskListRspVO();
		
		SmsTaskExample example = new SmsTaskExample();
		Criteria criteria = example.createCriteria();
		if(taskListReq.getStatus() != null){
			criteria.andSendStatusEqualTo(taskListReq.getStatus()); //任务状态
		}
		if(StringUtils.isNotEmpty(taskListReq.getTaskName())){
			criteria.andTaskNameLike(taskListReq.getTaskName()); //任务名称
		}
		if(StringUtils.isNotEmpty(taskListReq.getSendDate())){
			criteria.andSendDateEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(taskListReq.getSendDate())); //发送时间
		}
		taskListRsp.setTotalCount(taskMapper.selectByExampleWithBLOBs(example).size()); //总条数
		
		example.setLimitStart((taskListReq.getPageNum() - 1) * taskListReq.getPageSize());
		example.setLimitEnd(taskListReq.getPageSize());
		taskListRsp.setSmsTaskList(taskMapper.selectByExampleWithBLOBs(example)); // 分页返回的记录
		
		return taskListRsp;
	}

	/**
	 * 短信任务一键停止
	 */
	@Override
	public void stopTask(Integer id)
	{
		taskMapperExt.updateRunStatusByPrimaryKey(id);
	}

	/**
	 * 删除短信任务
	 */
	@Override
	public void delTask(Integer id)
	{
		taskMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 新增/编辑短信任务
	 */
	@Override
	public String addOrUpdateTask(TaskReqVO taskReqVO, Long userId) throws Exception
	{
		String result = "SUCCESS";
		SmsTask smsTask = new SmsTask();
		setParams(taskReqVO, smsTask, userId); //设置字段值
		
		// 解析excel文件
		List<String> phoneList = new ArrayList<>();
		try
		{
			phoneList = ParseFileUtil.parseExcelFile(taskReqVO.getFile());
		} catch (Exception e){
			logger.error("解析文件失败", e);
			throw new GuiyuException(SmsExceptionEnum.ParseFile_Error);
		}
		smsTask.setPhoneNum(phoneList.size());
		
		if(taskReqVO.getSendType() == 1) //手动发送=立即发送
		{
			if(smsTask.getAuditingStatus() == 0){
				result = "短信内容未审核，暂不能发送！";
				logger.info(result);
			} else {
				//组装发送请求
				TaskReq taskReq = new TaskReq(taskReqVO.getTaskName(), taskReqVO.getSendType(), phoneList, 
						taskReqVO.getTunnelName(), taskReqVO.getSmsTemplateId(), taskReqVO.getSmsContent());
				taskReq.setSendTime(new Date());
				taskReq.setCompanyName(smsTask.getCompanyName());
				taskReq.setUserId(userId);
				sendSmsService.sendMessages(taskReq); // 发送
				smsTask.setSendStatus(2); // 2-已结束
			}
		} 
		else
		{
			smsTask.setSendStatus(0); // 0-未开始
			redisUtil.lSet(taskReqVO.getTaskName(), phoneList); //未发送名单存入Redis
		}
		
		if(taskReqVO.getId() == null){
			taskMapper.insertSelective(smsTask); //新增
		}else{
			taskMapper.updateByPrimaryKeyWithBLOBs(smsTask); //编辑
		}
		
		return result;
	}
	
	/**
	 * 审核短信任务
	 */
	@Override
	public void auditingTask(Integer id)
	{
		taskMapperExt.updateAuditingStatusAndRunStatusByPrimaryKey(id);
	}

	/**
	 * 设置字段值
	 */
	private void setParams(TaskReqVO taskReqVO, SmsTask smsTask, Long userId) throws Exception
	{
		if(taskReqVO.getId() != null) { // 新增id为空，修改id不为空
			smsTask.setId(taskReqVO.getId());
		}
		smsTask.setTaskName(taskReqVO.getTaskName());
		smsTask.setSendType(taskReqVO.getSendType());
		smsTask.setTunnelName(taskReqVO.getTunnelName());
		smsTask.setSmsTemplateId(taskReqVO.getSmsTemplateId());
		smsTask.setSmsContent(taskReqVO.getSmsContent());
		if(taskReqVO.getSendDate() != null){
			smsTask.setSendDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(taskReqVO.getSendDate()));
		} else{
			smsTask.setSendDate(new Date());
		}
		if(taskReqVO.getSmsTemplateId() != null) //短信模版
		{ 
			smsTask.setAuditingStatus(1); // 审核状态：1-已审核
			smsTask.setRunStatus(1); // 运行状态：1-正常运行
		}
		else //自定义短信
		{ 
			smsTask.setAuditingStatus(0); // 0-待审核
			smsTask.setRunStatus(0); // 运行状态：0-停止
		}
		ReturnData<SysOrganization> sysOrganization = iAuth.getOrgByUserId(userId);
		smsTask.setCompanyId(sysOrganization.body.getId().intValue());
		smsTask.setCompanyName(sysOrganization.body.getName());
		smsTask.setCreateId(userId.intValue());
		smsTask.setCreateTime(new Date());
		smsTask.setUpdateId(userId.intValue());
		smsTask.setUpdateTime(new Date());
	}

	/**
	 * 获取定时任务
	 */
	@Override
	public List<SmsTask> getTimeTaskList()
	{
		return taskMapperExt.getTasks();
	}

	/**
	 * 更新发送状态
	 */
	@Override
	public void updateSendStatusById(Integer sendStatus, Integer id)
	{
		taskMapperExt.updateSendStatusById(sendStatus, id);
	}

}
