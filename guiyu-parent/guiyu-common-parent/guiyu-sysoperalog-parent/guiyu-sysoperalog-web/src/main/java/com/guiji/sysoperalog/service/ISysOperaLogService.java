package com.guiji.sysoperalog.service;

import java.util.List;

import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.vo.ConditionVO;

public interface ISysOperaLogService
{
	/**
	 * 保存数据
	 * @param sysUserAction
	 * @return
	 */
	int insertSysUserAction(SysUserAction sysUserAction);

	/**
	 * 根据条件查询SysUserAction列表
	 * @param condition
	 * @return
	 */
	List<SysUserAction> getSysUserActionByCondition(ConditionVO condition);

}
