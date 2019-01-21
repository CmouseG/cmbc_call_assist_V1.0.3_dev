package com.guiji.sms.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.sms.vo.ConfigListReqVO;
import com.guiji.sms.vo.ConfigListRspVO;
import com.guiji.sms.vo.ConfigReqVO;
import com.guiji.sms.vo.PlatformListReqVO;
import com.guiji.sms.vo.PlatformListRspVO;
import com.guiji.sms.vo.PlatformReqVO;
import com.guiji.sms.vo.PlatformRspVO;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.sms.vo.TaskDetailListReqVO;
import com.guiji.sms.vo.TaskDetailListRspVO;
import com.guiji.sms.vo.TaskListReqVO;
import com.guiji.sms.vo.TaskListRspVO;
import com.guiji.sms.vo.TaskReqVO;
import com.guiji.sms.vo.TunnelListReqVO;
import com.guiji.sms.vo.TunnelListRspVO;
import com.guiji.sms.vo.TunnelReqVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@FeignClient("guiyu-sms-web")
public interface ISms
{	
	/**
	 * 发短信
	 */
	@ApiOperation(value = "发短信")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "sendMReq", value = "发短信请求对象", required = true) 
	})
	@PostMapping(value = "sendMessage")
    public void sendMessage(@RequestBody SendMReqVO sendMReq);
	
	/**
	 * 获取短信平台列表
	 */
	@ApiOperation(value = "获取短信平台列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "platformListReq", value = "获取短信平台列表请求对象", required = true) 
	})
	@PostMapping(value = "getPlatformList")
    public ReturnData<PlatformListRspVO> getPlatformList(@RequestBody PlatformListReqVO platformListReq);
	
	/**
	 * 新增短信平台
	 */
	@ApiOperation(value = "新增短信平台")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "platformReq", value = "新增短信平台请求对象", required = true) 
	})
	@PostMapping(value = "addPlatform")
	public ReturnData<String> addPlatform(@RequestBody PlatformReqVO platformReq, @RequestHeader Long userId);
	
	/**
	 * 获取短信通道列表
	 */
	@ApiOperation(value = "获取短信通道列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "tunnelListReq", value = "获取短信通道列表请求对象", required = true) 
	})
	@PostMapping(value = "getTunnelList")
    public ReturnData<TunnelListRspVO> getTunnelList(@RequestBody TunnelListReqVO tunnelListReq);
	
	/**
	 * 获取短信平台名称列表及参数
	 */
	@ApiOperation(value = "获取短信平台名称列表及参数")
	@GetMapping(value = "getPlatformNameListWithParams")
	public ReturnData<List<PlatformRspVO>> getPlatformNameListWithParams();
	
	/**
	 * 新增短信通道
	 */
	@ApiOperation(value = "新增短信通道")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "tunnelReq", value = "新增短信通道请求对象", required = true) 
	})
	@PostMapping(value = "addTunnel")
	public ReturnData<String> addTunnel(@RequestBody TunnelReqVO tunnelReq, @RequestHeader Long userId);
	
	/**
	 * 删除短信通道
	 */
	@ApiOperation(value = "删除短信通道")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "delTunnel")
	public ReturnData<String> delTunnel(Integer id);
	
	/**
	 * 获取短信配置列表
	 */
	@ApiOperation(value = "获取短信配置列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "configListReq", value = "获取短信配置列表请求对象", required = true) 
	})
	@PostMapping(value = "getConfigList")
    public ReturnData<ConfigListRspVO> getConfigList(@RequestBody ConfigListReqVO configListReq);
	
	/**
	 * 获取短信通道名称列表
	 */
	@ApiOperation(value = "获取短信通道名称列表")
	@GetMapping(value = "getTunnelNameList")
	public ReturnData<List<String>> getTunnelNameList();
	
	/**
	 * 新增短信配置
	 */
	@ApiOperation(value = "新增短信配置")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "configReq", value = "新增短信配置请求对象", required = true) 
	})
	@PostMapping(value = "addConfig")
	public ReturnData<String> addConfig(@RequestBody ConfigReqVO configReq, @RequestHeader Long userId);
	
	/**
	 * 删除短信配置
	 */
	@ApiOperation(value = "删除短信配置")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "delConfig")
	public ReturnData<String> delConfig(Integer id);
	
	/**
	 * 编辑短信配置
	 */
	@ApiOperation(value = "编辑短信配置")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "configReq", value = "编辑短信配置请求对象", required = true) 
	})
	@PostMapping(value = "updateConfig")
	public ReturnData<String> updateConfig(@RequestBody ConfigReqVO configReq, @RequestHeader Long userId);
	
	/**
	 * 一键停止短信配置
	 */
	@ApiOperation(value = "短信配置一键停止")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "stopConfig")
	public ReturnData<String> stopConfig(Integer id);
	
	/**
	 * 审核短信配置
	 */
	@ApiOperation(value = "短信配置审核")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "auditingConfig")
	public ReturnData<String> auditingConfig(Integer id);
	
	/**
	 * 获取短信任务列表
	 */
	@ApiOperation(value = "获取短信任务列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "taskListReq", value = "获取短信任务列表请求对象", required = true) 
	})
	@PostMapping(value = "getTaskList")
	public ReturnData<TaskListRspVO> getTaskList(@RequestBody TaskListReqVO taskListReq);
	
	/**
	 * 短信任务一键停止
	 */
	@ApiOperation(value = "短信任务一键停止")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "stopTask")
	public ReturnData<String> stopTask(Integer id);
	
	/**
	 * 删除短信任务
	 */
	@ApiOperation(value = "删除短信任务")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "delTask")
	public ReturnData<String> delTask(Integer id);
	
	/**
	 * 新增/编辑短信任务
	 */
	@ApiOperation(value = "新增/编辑短信任务")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "taskReq", value = "新增/编辑短信任务请求对象", required = true) 
	})
	@PostMapping(value = "addOrUpdateTask")
	public ReturnData<String> addOrUpdateTask(TaskReqVO taskReq, @RequestHeader Long userId);
	
	/**
	 * 审核短信任务
	 */
	@ApiOperation(value = "审核短信任务")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "auditingTask")
	public ReturnData<String> auditingTask(Integer id);
	
	/**
	 * 获取短信任务详情列表
	 */
	@ApiOperation(value = "获取短信任务详情列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "taskDetailListReq", value = "获取短信任务详情列表请求对象", required = true) 
	})
	@PostMapping(value = "getTaskDetailList")
	public ReturnData<TaskDetailListRspVO> getTaskDetailList(@RequestBody TaskDetailListReqVO taskDetailListReq);
	
	/**
	 * 删除短信任务详情
	 */
	@ApiOperation(value = "删除短信任务详情")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "id", required = true) 
	})
	@GetMapping(value = "delTaskDetail")
	public ReturnData<String> delTaskDetail(Integer id);
}
