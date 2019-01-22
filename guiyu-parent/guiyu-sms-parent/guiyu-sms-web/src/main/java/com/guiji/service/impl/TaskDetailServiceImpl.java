package com.guiji.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.model.TaskReq;
import com.guiji.service.TaskDetailService;
import com.guiji.sms.dao.SmsTaskDetailMapper;
import com.guiji.sms.dao.entity.SmsTaskDetail;
import com.guiji.sms.dao.entity.SmsTaskDetailExample;
import com.guiji.sms.dao.entity.SmsTaskDetailExample.Criteria;
import com.guiji.sms.vo.TaskDetailListReqVO;
import com.guiji.sms.vo.TaskDetailListRspVO;

@Service
public class TaskDetailServiceImpl implements TaskDetailService
{
	@Autowired
	SmsTaskDetailMapper taskDetailMapper;

	/**
	 * 获取短信任务详情列表
	 * @throws ParseException 
	 */
	@Override
	public TaskDetailListRspVO getTaskDetailList(TaskDetailListReqVO taskDetailListReq) throws ParseException
	{
		TaskDetailListRspVO taskDetailListRsp = new TaskDetailListRspVO();
		
		SmsTaskDetailExample example = new SmsTaskDetailExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(taskDetailListReq.getTaskName())){
			criteria.andTaskNameEqualTo(taskDetailListReq.getTaskName());
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getCompanyName())){
			criteria.andCompanyNameEqualTo(taskDetailListReq.getCompanyName());
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getSendTime())){
			criteria.andSendTimeEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(taskDetailListReq.getSendTime()));
		}
		if(taskDetailListReq.getSendType() != null){
			criteria.andSendTypeEqualTo(taskDetailListReq.getSendType());
		}
		if(taskDetailListReq.getSendStatus() != null){
			criteria.andSendStatusEqualTo(taskDetailListReq.getSendStatus());
		}
		taskDetailListRsp.setTotalCount(taskDetailMapper.selectByExample(example).size()); // 总条数
		
		example.setLimitStart((taskDetailListReq.getPageNum() - 1) * taskDetailListReq.getPageSize());
		example.setLimitEnd(taskDetailListReq.getPageSize());
		taskDetailListRsp.setSmsTaskDetailList(taskDetailMapper.selectByExample(example)); // 分页返回的记录
		
		return taskDetailListRsp;
	}

	/**
	 * 删除短信任务详情
	 */
	@Override
	public void delTaskDetail(Integer id)
	{
		taskDetailMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 保存短信任务详情
	 */
	@Override
	public void saveTaskDetail(String statusCode, TaskReq taskReq, String phone)
	{
		SmsTaskDetail detail = new SmsTaskDetail();
		//组装字段
		detail.setTaskName(taskReq.getTaskName());
		detail.setPhone(phone);
		detail.setSendType(taskReq.getSendType());
		if("0".equals(statusCode)){
			detail.setSendStatus(0); // 发送状态：0-发送成功
		}else{
			detail.setSendStatus(1); // 发送状态：1-发送失败
		}
		detail.setCompanyName("");
		detail.setTunnelName(taskReq.getTunnelName());
		detail.setSendTime(taskReq.getSendTime());
		detail.setUserName("");
		taskDetailMapper.insertSelective(detail);
	}

}
