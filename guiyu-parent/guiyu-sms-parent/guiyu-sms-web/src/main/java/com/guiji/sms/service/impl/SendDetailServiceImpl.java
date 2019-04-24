package com.guiji.sms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.SendDetailListRsp;
import com.guiji.sms.dao.SmsSendDetailMapper;
import com.guiji.sms.dao.entity.SmsSendDetailExample;
import com.guiji.sms.service.SendDetailService;
import com.guiji.sms.utils.DateUtil;

@Service
public class SendDetailServiceImpl implements SendDetailService
{
	@Autowired
	SmsSendDetailMapper sendDetailMapper;

	/**
	 * 获取短信发送详情列表
	 */
	@Override
	public SendDetailListRsp querySendDetailList(Condition condition, AuthLevelData authLevelData)
	{
		SendDetailListRsp rsp = new SendDetailListRsp();
		SmsSendDetailExample example = new SmsSendDetailExample();
		Integer authLevel = authLevelData.getAuthLevel();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(authLevelData.getUserId().intValue());
		} else if(authLevel == 2) {
			example.createCriteria().andOrgCodeEqualTo(authLevelData.getOrgCode());
		}else if(authLevel == 3) {
			example.createCriteria().andOrgCodeLike(authLevelData.getOrgCode() + "%");
		}
		if(StringUtils.isNotEmpty(condition.getTaskName())){
			example.createCriteria().andTaskNameLike(condition.getTaskName()+"%");
		}
		if(StringUtils.isNotEmpty(condition.getOrgName())){
			example.createCriteria().andOrgNameLike(condition.getOrgName()+"%");
		}
		if(condition.getSendStatus() != null){
			example.createCriteria().andSendStatusEqualTo(condition.getSendStatus());
		}
		try{
			if(StringUtils.isNotEmpty(condition.getStartDate())){
				example.createCriteria().andSendTimeGreaterThanOrEqualTo(DateUtil.parse(condition.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotEmpty(condition.getEndDate())){
				example.createCriteria().andSendTimeLessThanOrEqualTo(DateUtil.parse(condition.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
			}
		}catch (Exception e){
			throw new SmsException(ExceptionEnum.ERROR_PARSE_DATE);
		}
		rsp.setTotalNum(sendDetailMapper.countByExample(example));
		example.setLimitStart((condition.getPageNum()-1)*condition.getPageSize());
		example.setLimitEnd(condition.getPageSize());
		example.setOrderByClause("id desc");
		rsp.setRecords(sendDetailMapper.selectByExampleWithBLOBs(example));
		return rsp;
	}

	/**
	 * 删除短信发送详情
	 */
	@Override
	public void delSendDetail(Integer id)
	{
		sendDetailMapper.deleteByPrimaryKey(id);
	}
	
}
