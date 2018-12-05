package com.guiji.sysoperalog.service;

import java.util.List;

import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.vo.ConditionVO;
import com.guiji.sysoperalog.vo.SysUserActionVO;

public interface ISysOperaLogService
{
	
	int insertSysUserAction(SysUserActionVO sysUserActionVO) throws Exception;

	List<SysUserAction> getSysUserActionByCondition(ConditionVO condition) throws Exception;

}
