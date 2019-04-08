package com.guiji.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.model.TaskReq;
import com.guiji.service.TaskDetailService;
import com.guiji.sms.dao.SmsTaskDetailMapper;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.sms.dao.entity.SmsTaskDetail;
import com.guiji.sms.dao.entity.SmsTaskDetailExample;
import com.guiji.sms.dao.entity.SmsTaskDetailExample.Criteria;
import com.guiji.sms.vo.MsgResultVO;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.sms.vo.TaskDetailListReqVO;
import com.guiji.sms.vo.TaskDetailListRspVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.LocalCacheUtil;
import com.guiji.utils.RedisUtil;

@Service
public class TaskDetailServiceImpl implements TaskDetailService
{
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
	public TaskDetailListRspVO getTaskDetailList(TaskDetailListReqVO taskDetailListReq, Long userId, Integer authLevel, String orgCode) throws ParseException
	{
		TaskDetailListRspVO taskDetailListRsp = new TaskDetailListRspVO();
		
		SmsTaskDetailExample example = new SmsTaskDetailExample();
		Criteria criteria = example.createCriteria();
		if(authLevel == 1) {
			criteria.andCreateIdEqualTo(userId.intValue());
		} else if(authLevel == 2) {
			criteria.andOrgCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			criteria.andOrgCodeLike(orgCode + "%");
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getTaskName())){
			criteria.andTaskNameEqualTo(taskDetailListReq.getTaskName());
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getCompanyName())){
			criteria.andCompanyNameEqualTo(taskDetailListReq.getCompanyName());
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getStartDate())){
			criteria.andSendTimeGreaterThanOrEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
													.parse(taskDetailListReq.getStartDate()+" 00:00:00"));
		}
		if(StringUtils.isNotEmpty(taskDetailListReq.getEndDate())){
			criteria.andSendTimeLessThanOrEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
													.parse(taskDetailListReq.getEndDate()+" 23:59:59"));
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
		example.setOrderByClause("id desc");
		taskDetailListRsp.setSmsTaskDetailList(taskDetailMapper.selectByExampleWithBLOBs(example)); // 分页返回的记录
		
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
		if(taskReq.getSmsTemplateId() != null) {
			detail.setSmsContent("【短信模版】" + taskReq.getSmsTemplateId());
		} else {
			detail.setSmsContent("【短信内容】" + taskReq.getSmsContent());
		}
		detail.setCreateId(taskReq.getUserId().intValue());
		detail.setUserName(getUserName(String.valueOf(taskReq.getUserId())));
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(taskReq.getUserId());
		detail.setOrgCode(sysOrganization.body.getCode());
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
	
	/**
	 * 保存短信发送详情
	 */
	@Override
	public void saveTaskDetail(SmsRecord record, SmsConfig config, SendMReqVO sendMReq)
	{
		SmsTaskDetail detail = new SmsTaskDetail();
		//组装字段
		detail.setPlanuuid(sendMReq.getPlanuuid());
		detail.setTaskName("挂机短信");
		detail.setSendType(2); // 调用发送
		detail.setCompanyName(config.getCompanyName());
		detail.setTunnelName(config.getTunnelName());
		detail.setSendTime(new Date());
		if(config.getSmsTemplateId() != null) {
			detail.setSmsContent("【短信模版】" + config.getSmsTemplateId());
		} else {
			detail.setSmsContent("【短信内容】" + config.getSmsContent());
		}
		detail.setCreateId(sendMReq.getUserId());
		detail.setUserName(getUserName(String.valueOf(sendMReq.getUserId())));
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(sendMReq.getUserId().longValue());
		detail.setOrgCode(sysOrganization.body.getCode());
		detail.setPhone(record.getPhone());
		if(record.getSendStatus() == 0){
			detail.setSendStatus(0); // 发送状态：0-发送失败
		}else{
			detail.setSendStatus(1); // 发送状态：1-发送成功
		}
		taskDetailMapper.insertSelective(detail);
	}

	@Override
	public MsgResultVO getTaskDetail(String planuuid)
	{
		MsgResultVO msgResult = null;
		SmsTaskDetailExample example = new SmsTaskDetailExample();
		example.createCriteria().andPlanuuidEqualTo(planuuid);
		SmsTaskDetail detail = taskDetailMapper.selectByExampleWithBLOBs(example).get(0);
		msgResult = setParams(detail);
		return msgResult;
	}

	private MsgResultVO setParams(SmsTaskDetail detail)
	{
		MsgResultVO msgResult = new MsgResultVO();
		msgResult.setPlanuuid(detail.getPlanuuid());
		msgResult.setPhone(detail.getPhone());
		msgResult.setSmsContent(detail.getSmsContent());
		msgResult.setSendStatus(detail.getSendStatus()==1?"发送成功":"发送失败");
		msgResult.setCompanyName(detail.getCompanyName());
		msgResult.setTunnelName(detail.getTunnelName());
		msgResult.setSendTime(detail.getSendTime());
		return msgResult;
	}
	
	private String getUserName(String userId) {
        String cacheName = LocalCacheUtil.getT("USERNAME_"+userId);
        if (cacheName != null) {
            return cacheName;
        } else {
            try {
                Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
                if(result!=null && result.getBody()!=null) {
                    String userName = result.getBody().getUsername();
                    if (userName != null) {
                    	LocalCacheUtil.set(userId, userName, LocalCacheUtil.HARF_HOUR);
                        return userName;
                    }
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return "";
    }
}
