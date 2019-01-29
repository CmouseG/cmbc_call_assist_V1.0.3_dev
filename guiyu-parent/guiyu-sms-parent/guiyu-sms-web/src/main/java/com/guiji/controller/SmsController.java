package com.guiji.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.entity.SmsConstants;
import com.guiji.service.ConfigService;
import com.guiji.service.PlatformService;
import com.guiji.service.SendSmsService;
import com.guiji.service.TaskDetailService;
import com.guiji.service.TaskService;
import com.guiji.service.TunnelService;
import com.guiji.sms.api.ISms;
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

@RestController
public class SmsController implements ISms
{
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	PlatformService platformService;
	@Autowired
	TunnelService tunnelService;
	@Autowired
	ConfigService configService;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskDetailService taskDetailService;
	@Autowired
	SendSmsService sendSmsService;
	
	/**
	 * 获取短信平台列表
	 */
	@PostMapping(value = "getPlatformList")
	public ReturnData<PlatformListRspVO> getPlatformList(@RequestBody PlatformListReqVO platformListReq, @RequestHeader Long userId)
	{
		PlatformListRspVO platformListRsp = new PlatformListRspVO();
		try
		{
			logger.info("获取短信平台列表...");
			platformListRsp = platformService.getPlatformList(platformListReq, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(platformListRsp);
	}

	/**
	 * 新增短信平台
	 */
	@PostMapping(value = "addPlatform")
	public ReturnData<String> addPlatform(@RequestBody PlatformReqVO platformReq, @RequestHeader Long userId)
	{
		try
		{
			logger.info("新增短信平台...");
			platformService.addPlatform(platformReq, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 *获取短信通道列表
	 */
	@PostMapping(value = "getTunnelList")
	public ReturnData<TunnelListRspVO> getTunnelList(@RequestBody TunnelListReqVO tunnelListReq, @RequestHeader Long userId)
	{
		TunnelListRspVO tunnelListRsp = new TunnelListRspVO();
		try
		{
			logger.info("获取短信通道列表...");
			tunnelListRsp = tunnelService.getTunnelList(tunnelListReq, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(tunnelListRsp);
	}

	/**
	 * 获取短信平台名称列表及参数
	 */
	@GetMapping(value = "getPlatformNameListWithParams")
	public ReturnData<List<PlatformRspVO>> getPlatformNameListWithParams()
	{
		List<PlatformRspVO> platformRspList = new ArrayList<>();
		try
		{
			logger.info("获取短信平台名称列表及参数...");
			platformRspList = platformService.getPlatformNameListWithParams();
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(platformRspList);
	}

	/**
	 * 新增短信通道
	 */
	@PostMapping(value = "addTunnel")
	public ReturnData<String> addTunnel(@RequestBody TunnelReqVO tunnelReq, @RequestHeader Long userId)
	{
		try
		{
			logger.info("新增短信通道...");
			tunnelService.addTunnel(tunnelReq,userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 删除短信通道
	 */
	@GetMapping(value = "delTunnel")
	public ReturnData<String> delTunnel(Integer id)
	{
		try
		{
			logger.info("删除短信通道...");
			tunnelService.delTunnel(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 获取短信配置列表
	 */
	@PostMapping(value = "getConfigList")
	public ReturnData<ConfigListRspVO> getConfigList(@RequestBody ConfigListReqVO configListReq, @RequestHeader Long userId)
	{
		ConfigListRspVO configListRsp = new ConfigListRspVO();
		try
		{
			logger.info("获取短信配置列表...");
			configListRsp = configService.getConfigList(configListReq, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(configListRsp);
	}
	
	/**
	 * 获取短信通道名称列表
	 */
	@GetMapping(value = "getTunnelNameList")
	public ReturnData<List<String>> getTunnelNameList(@RequestHeader Long userId)
	{
		List<String> tunnelNameList = new ArrayList<>();
		try
		{
			logger.info("获取短信通道名称列表...");
			tunnelNameList = tunnelService.getTunnelNameList(userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(tunnelNameList);
	}

	/**
	 * 新增短信配置
	 */
	@PostMapping(value = "addConfig")
	public ReturnData<String> addConfig(@RequestBody ConfigReqVO configReq, @RequestHeader Long userId)
	{
		try
		{
			logger.info("新增短信配置...");
			configService.addConfig(configReq,userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 * 删除短信配置
	 */
	@GetMapping(value = "delConfig")
	public ReturnData<String> delConfig(Integer id)
	{
		try
		{
			logger.info("删除短信配置...");
			configService.delConfig(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 编辑短信配置
	 */
	@PostMapping(value = "updateConfig")
	public ReturnData<String> updateConfig(@RequestBody ConfigReqVO configReq, @RequestHeader Long userId)
	{
		try
		{
			logger.info("编辑短信配置...");
			configService.updateConfig(configReq,userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 * 短信配置一键停止
	 */
	@GetMapping(value = "stopConfig")
	public ReturnData<String> stopConfig(Integer id)
	{
		try
		{
			logger.info("短信配置一键停止...");
			configService.stopConfig(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 短信配置审核
	 */
	@GetMapping(value = "auditingConfig")
	public ReturnData<String> auditingConfig(Integer id)
	{
		try
		{
			logger.info("短信配置审核...");
			configService.auditingConfig(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 * 获取短信任务列表
	 */
	@PostMapping(value = "getTaskList")
	public ReturnData<TaskListRspVO> getTaskList(@RequestBody TaskListReqVO taskListReq, @RequestHeader Long userId)
	{
		TaskListRspVO taskListRsp = new TaskListRspVO();
		try
		{
			logger.info("获取短信任务列表...");
			taskListRsp = taskService.getTaskList(taskListReq, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(taskListRsp);
	}
	
	/**
	 * 短信任务一键停止
	 */
	@GetMapping(value = "stopTask")
	public ReturnData<String> stopTask(Integer id)
	{
		try
		{
			logger.info("短信任务一键停止...");
			taskService.stopTask(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 * 删除短信任务
	 */
	@GetMapping(value = "delTask")
	public ReturnData<String> delTask(Integer id)
	{
		try
		{
			logger.info("删除短信任务...");
			taskService.delTask(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 审核短信任务
	 */
	@GetMapping(value = "auditingTask")
	public ReturnData<String> auditingTask(Integer id)
	{
		try
		{
			logger.info("审核短信任务...");
			taskService.auditingTask(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 * 获取短信任务详情列表
	 */
	@PostMapping(value = "getTaskDetailList")
	public ReturnData<TaskDetailListRspVO> getTaskDetailList(@RequestBody TaskDetailListReqVO taskDetailListReq, @RequestHeader Long userId)
	{
		TaskDetailListRspVO taskDetailListRsp = new TaskDetailListRspVO();
		try
		{
			logger.info("获取短信任务详情列表...");
			taskDetailListRsp = taskDetailService.getTaskDetailList(taskDetailListReq, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok(taskDetailListRsp);
	}
	
	/**
	 * 删除短信任务详情
	 */
	@GetMapping(value = "delTaskDetail")
	public ReturnData<String> delTaskDetail(Integer id)
	{
		try
		{
			logger.info("删除短信任务详情...");
			taskDetailService.delTaskDetail(id);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 新增短信任务
	 */
	@PostMapping(value = "addTask")
	public ReturnData<String> addTask(TaskReqVO taskReqVO, @RequestHeader Long userId)
	{
		try
		{
			logger.info("新增短信任务...");
			taskService.addTask(taskReqVO, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}
	
	/**
	 * 编辑短信任务
	 */
	@PostMapping(value = "updateTask")
	public ReturnData<String> updateTask(TaskReqVO taskReqVO, @RequestHeader Long userId)
	{
		try
		{
			logger.info("编辑短信任务...");
			taskService.updateTask(taskReqVO, userId);
			
		} catch (Exception e){
			logger.error("请求失败！", e);
			return Result.error(SmsConstants.Error_Request);
		}
		return Result.ok("SUCCESS");
	}

	/**
	 * 发短信
	 */
	@Override
	@PostMapping(value = "sendMessage")
	public void sendMessage(@RequestBody SendMReqVO sendMReq)
	{
		sendSmsService.pushReqToMQ(sendMReq);
	}
}
