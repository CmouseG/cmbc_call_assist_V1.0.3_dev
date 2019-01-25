package com.guiji.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.model.TaskReq;
import com.guiji.service.TaskDetailService;
import com.guiji.sms.dao.SmsTaskDetailMapper;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.sms.dao.entity.SmsTaskDetail;
import com.guiji.sms.dao.entity.SmsTaskDetailExample;
import com.guiji.sms.dao.entity.SmsTaskDetailExample.Criteria;
import com.guiji.sms.vo.TaskDetailListReqVO;
import com.guiji.sms.vo.TaskDetailListRspVO;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;

@Service
public class TaskDetailServiceImpl implements TaskDetailService
{
	private static final Logger logger = LoggerFactory.getLogger(TaskDetailServiceImpl.class);
	
	@Autowired
	IAuth auth;
	@Autowired
	RedisUtil redisUtil;
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
		if(StringUtils.isNotEmpty(taskDetailListReq.getStartDate())){
			criteria.andSendTimeGreaterThanOrEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(taskDetailListReq.getStartDate()));
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getEndDate())){
			criteria.andSendTimeLessThanOrEqualTo(new SimpleDateFormat("yyyy-MM-dd").parse(taskDetailListReq.getEndDate()));
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
	public void saveTaskDetail(List<SmsRecord> records, TaskReq taskReq)
	{
		SmsTaskDetail detail = new SmsTaskDetail();
		//组装字段
		detail.setTaskName(taskReq.getTaskName());
		detail.setSendType(taskReq.getSendType());
		detail.setCompanyName(taskReq.getCompanyName());
		detail.setTunnelName(taskReq.getTunnelName());
		detail.setSendTime(taskReq.getSendTime());
		detail.setUserName(getUserName(String.valueOf(taskReq.getUserId())));
		for(SmsRecord record : records)
		{
			detail.setPhone(record.getPhone());
			if(record.getSendStatus() == 0){
				detail.setSendStatus(0); // 发送状态：0-发送失败
			}else{
				detail.setSendStatus(1); // 发送状态：1-发送成功
			}
			taskDetailMapper.insertSelective(detail);
		}
	}
	
	public String getUserName(String userId) {
        String cacheName = (String) redisUtil.get(userId);
        if (cacheName != null) {
            return cacheName;
        } else {
            try {
                Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
                if(result!=null && result.getBody()!=null) {
                    String userName = result.getBody().getUsername();
                    if (userName != null) {
                    	redisUtil.set(userId, userName);
                        return userName;
                    }
                }
            } catch (Exception e) {
            	logger.error(" auth.getUserName error :" + e);
            }
        }
        return "";
    }
}
