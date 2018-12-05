package com.guiji.sysoperalog.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.vo.ConditionVO;
import com.guiji.sysoperalog.vo.SysUserActionVO;

import io.swagger.annotations.ApiOperation;

/**
 * 系统操作日志对外服务
 * Created by ty on 2018/11/13.
 */
@FeignClient("guiyu-sysoperalog-web")
public interface ISysOperaLog
{

	/**
	 * 添加数据(sys_user_action)
	 * @param sysUserAction
	 * @return
	 */
	@ApiOperation(value="插入数据")
    @PostMapping(value = "insert")
	public ReturnData<Integer> insert(SysUserActionVO sysUserAction);
	
	/**
	 * 根据条件获取数据
	 * @param condition
	 * @return
	 */
	@ApiOperation(value="根据condition获取数据")
    @PostMapping(value = "getSysUserActionByCondition")
	public ReturnData<List<SysUserAction>> getSysUserActionByCondition(ConditionVO condition);
}
