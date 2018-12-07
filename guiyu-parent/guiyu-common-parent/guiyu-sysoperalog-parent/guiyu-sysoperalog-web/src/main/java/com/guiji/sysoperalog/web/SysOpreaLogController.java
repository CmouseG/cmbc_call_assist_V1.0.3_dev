package com.guiji.sysoperalog.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.api.ISysOperaLog;
import com.guiji.sysoperalog.constants.SysOperaLogConstants;
import com.guiji.sysoperalog.service.ISysOperaLogService;
import com.guiji.sysoperalog.vo.ConditionVO;

@RestController
public class SysOpreaLogController implements ISysOperaLog
{
	private static final Logger logger = LoggerFactory.getLogger(SysOpreaLogController.class);
	
	@Autowired
	ISysOperaLogService ISysOperaLogService;
	
	/**
	 * 保存数据
	 * @param sysUserAction
	 * @return
	 */
	@PostMapping(value = "insert")
	public ReturnData<Integer> insert(@RequestBody SysUserAction sysUserAction)
	{
		Integer num  = 0;
		try
		{
			num = ISysOperaLogService.insertSysUserAction(sysUserAction);
			
		} catch (GuiyuException e){
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch(Exception ex){
			logger.error("请求失败！", ex);
			return Result.error(SysOperaLogConstants.INSERT_ERROR);
		}
		
		return Result.ok(num);
	}
	
	/**
	 * 根据条件condition获取SysUserAction列表
	 * @param condition
	 * @return
	 */
	@PostMapping(value = "getSysUserActionByCondition")
	public ReturnData<List<SysUserAction>> getSysUserActionByCondition(@RequestBody ConditionVO condition)
	{
		//结果集
		List<SysUserAction> sysUserActionList = new ArrayList<SysUserAction>();
		try
		{
			sysUserActionList = ISysOperaLogService.getSysUserActionByCondition(condition);

		} catch (GuiyuException e) {
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(SysOperaLogConstants.QUERY_ERROR);
		}
		
		return Result.ok(sysUserActionList);
	}
}
