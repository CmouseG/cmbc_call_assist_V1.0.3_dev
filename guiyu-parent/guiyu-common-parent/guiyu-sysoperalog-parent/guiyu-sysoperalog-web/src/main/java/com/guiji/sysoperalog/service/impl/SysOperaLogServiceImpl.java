package com.guiji.sysoperalog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.guiyu.sysoperalog.dao.SysUserActionMapper;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.service.ISysOperaLogService;
import com.guiji.sysoperalog.vo.ConditionVO;
import com.guiji.sysoperalog.vo.SysUserActionVO;

@Service
public class SysOperaLogServiceImpl implements ISysOperaLogService
{

	@Autowired
	SysUserActionMapper sysUserActionMapper;
	
	@Override
	@Transactional
	public int insertSysUserAction(SysUserActionVO sysUserActionVO) throws Exception
	{
		SysUserAction sysUserAction = new SysUserAction();
		sysUserAction = mapped(sysUserActionVO);
		return sysUserActionMapper.insert(sysUserAction);
		
	}

	@Override
	@Transactional
	public List<SysUserAction> getSysUserActionByCondition(ConditionVO condition) throws Exception
	{
		Date startTime = condition.getStartTime();
		Date endTime = condition.getEndTime();
		Integer limitStart = condition.getLimitStart();
		Integer limitEnd = condition.getLimitEnd();
		return sysUserActionMapper.getSysUserActionByCondition(startTime, endTime, limitStart, limitEnd);
	}
	
	
	private SysUserAction mapped(SysUserActionVO sysUserActionVO)
	{
		SysUserAction sysUserAction = new SysUserAction();
		sysUserAction.setActionName(sysUserActionVO.getActionName());;
		sysUserAction.setUserId(sysUserActionVO.getUserId());
		sysUserAction.setOperateTime(sysUserActionVO.getOperateTime());
		sysUserAction.setData(sysUserActionVO.getData());
		sysUserAction.setUrl(sysUserActionVO.getUrl());
		return sysUserAction;
	}

}
