package com.guiji.sysoperalog.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.api.ISysOperaLog;
import com.guiji.sysoperalog.constants.SysOperaLogConstants;
import com.guiji.sysoperalog.service.ISysOperaLogService;
import com.guiji.sysoperalog.vo.ConditionVO;
import com.guiji.sysoperalog.vo.SysUserActionVO;

@RestController
public class SysOpreaLogController implements ISysOperaLog
{
	
	@Autowired
	ISysOperaLogService ISysOperaLogService;
	
	/**
	 * 增
	 * @param sysUserAction
	 * @return
	 */
	@PostMapping(value = "insert")
	public ReturnData<Integer> insert(@RequestBody SysUserActionVO sysUserActionVO)
	{
		Integer num = 0;
		
		try
		{
			if(sysUserActionVO == null){
				return Result.ok(0);
			}
			num = ISysOperaLogService.insertSysUserAction(sysUserActionVO);
			return Result.ok(num);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.error(SysOperaLogConstants.INSERT_ERROR);
		}
		
	}
	
	/**
	 * 获取SysUserAction
	 * @param condition
	 * @return
	 */
	@PostMapping(value = "getSysUserActionByCondition")
	public ReturnData<List<SysUserAction>> getSysUserActionByCondition(@RequestBody ConditionVO condition)
	{
		List<SysUserAction> sysUserActionList = new ArrayList<SysUserAction>();
		
		try
		{
			sysUserActionList = ISysOperaLogService.getSysUserActionByCondition(condition);
			return Result.ok(sysUserActionList);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.error(SysOperaLogConstants.QUERY_ERROR);
		}
		
	}
}
