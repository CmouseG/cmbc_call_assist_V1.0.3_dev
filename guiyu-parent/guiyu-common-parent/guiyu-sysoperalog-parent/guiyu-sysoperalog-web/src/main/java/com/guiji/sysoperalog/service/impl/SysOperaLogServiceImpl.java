package com.guiji.sysoperalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.guiyu.sysoperalog.dao.SysUserActionMapper;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserActionExample;
import com.guiji.sysoperalog.service.ISysOperaLogService;
import com.guiji.sysoperalog.vo.ConditionVO;

@Service
public class SysOperaLogServiceImpl implements ISysOperaLogService
{

	@Autowired
	SysUserActionMapper sysUserActionMapper;
	
	@Override
	@Transactional
	public int insertSysUserAction(SysUserAction sysUserAction)
	{
		return sysUserActionMapper.insertSelective(sysUserAction);	
	}

	@Override
	public List<SysUserAction> getSysUserActionByCondition(ConditionVO condition)
	{
		SysUserActionExample example = new SysUserActionExample();
		example.setLimitStart(condition.getLimitStart());
		example.setLimitEnd(condition.getLimitEnd());
		example.createCriteria().andOperateTimeGreaterThanOrEqualTo(condition.getStartTime())
								.andOperateTimeLessThanOrEqualTo(condition.getEndTime());
		
		return sysUserActionMapper.selectByExampleWithBLOBs(example);
	}

}
