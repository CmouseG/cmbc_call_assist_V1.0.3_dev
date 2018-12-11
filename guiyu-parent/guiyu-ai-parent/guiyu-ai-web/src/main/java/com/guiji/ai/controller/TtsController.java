package com.guiji.ai.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.ITts;
import com.guiji.ai.tts.TtsReqQueue;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.IModelService;
import com.guiji.ai.tts.service.IResultService;
import com.guiji.ai.tts.service.IStatusService;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.vo.TaskListReqVO;
import com.guiji.ai.vo.TaskListRspVO;
import com.guiji.ai.vo.TtsGpuReqVO;
import com.guiji.ai.vo.TtsGpuRspVO;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.ai.vo.TtsStatusReqVO;
import com.guiji.ai.vo.TtsStatusRspVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;

/**
 * 语音合成 Created by ty on 2018/11/13.
 */
@RestController
public class TtsController implements ITts
{
	private static Logger logger = LoggerFactory.getLogger(TtsController.class);

	@Autowired
	ITtsService ttsService;
	@Autowired
	IStatusService statusService;
	@Autowired
	IResultService resultService;
	@Autowired
	IModelService modelService;

	@Override
	@PostMapping(value = "translate")
	public ReturnData<String> translate(@RequestBody TtsReqVO ttsReqVO) {
		try
		{
			if (ttsReqVO != null && !ttsReqVO.getContents().isEmpty()) {
				//入库
				statusService.saveTtsStatus(ttsReqVO);
				//入redis
				ttsService.saveTask(ttsReqVO);
				//入队列
				TtsReqQueue.getInstance().produce(ttsReqVO);
			}
		} catch (GuiyuException e){
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch(Exception ex){
			logger.error("请求失败！", ex);
			return Result.error(AiConstants.AI_REQUEST_FAIL);
		}
		return Result.ok("success");
	}

	/**
	 * 根据busiId查询TTS处理结果
	 */
	@Override
	@PostMapping(value = "getTtsResultByBusId")
	public ReturnData<TtsRspVO> getTtsResultByBusId(@RequestParam(value="busId",required=true) String busId) {
		TtsRspVO ttsRspVO = new TtsRspVO();
		try
		{
			String status = statusService.getTransferStatusByBusId(busId);
			ttsRspVO.setBusId(busId);
			ttsRspVO.setStatus(status);
			
			if (AiConstants.FINISHED.equals(status)) {
				Map<String, String> radioMap = new HashMap<>();
				List<Map<String, String>> resultMapList = resultService.getTtsTransferResultByBusId(busId);
				for (Map<String, String> restltMap : resultMapList) {
					radioMap.put(restltMap.get("content"), restltMap.get("audio_url"));
				}
				ttsRspVO.setAudios(radioMap);
			}
		} catch (GuiyuException e) {
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch(Exception ex) {
			logger.error("请求失败！", ex);
			return Result.error(AiConstants.AI_REQUEST_FAIL);
		}
		return Result.ok(ttsRspVO);
	}

	/**
	 * 查询TTS处理状态列表
	 */
	@Override
	@PostMapping(value = "getTtsStatus")
	public ReturnData<List<TtsStatusRspVO>> getTtsStatusList(@RequestBody(required = false) TtsStatusReqVO ttsStatusReqVO) 
	{
		//结果集
		List<TtsStatusRspVO> statusRspVOList = new ArrayList<>();
		try
		{
			logger.info("开始查询tts处理状态...");
			statusRspVOList = statusService.getTtsStatusList(ttsStatusReqVO);
				
		} catch (GuiyuException e){
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch (Exception ex){
			logger.error("请求失败！", ex);
			return Result.error(AiConstants.AI_REQUEST_FAIL);
		}
		return Result.ok(statusRspVOList);
	}

	/**
	 * 获取GPU模型列表
	 */
	@Override
	@PostMapping(value = "getAllGpuByPage")
	public ReturnData<TtsGpuRspVO> getGpuList(@RequestBody(required = false) TtsGpuReqVO ttsGpuReqVO)
	{
		TtsGpuRspVO ttsGpuRsp = new TtsGpuRspVO();
		try
		{
			logger.info("获取GPU模型列表...");
			ttsGpuRsp = modelService.getGpuList(ttsGpuReqVO);
			
		} catch (GuiyuException e){
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch (Exception ex){
			logger.error("请求失败！", ex);
			return Result.error(AiConstants.AI_REQUEST_FAIL);
		}
		return  Result.ok(ttsGpuRsp);
	}

	/**
	 * 获取任务列表
	 */
	@Override
	@PostMapping(value = "getTaskList")
	public ReturnData<TaskListRspVO> getTaskList(@RequestBody(required = false) TaskListReqVO taskListReqVO)
	{
		TaskListRspVO taskListRspVO = new TaskListRspVO();
		try
		{
			logger.info("获取任务列表...");
			taskListRspVO = statusService.getTaskList(taskListReqVO);
			
		} catch (GuiyuException e){
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch (Exception ex){
			logger.error("请求失败！", ex);
			return Result.error(AiConstants.AI_REQUEST_FAIL);
		}
		return Result.ok(taskListRspVO);
	}

	/**
	 * 任务插队
	 */
	@Override
	@PostMapping(value = "jumpQueue")
	public ReturnData<Boolean> jumpQueue(@RequestParam(value="busId",required=true) String busId)
	{
		try
		{
			logger.info("执行任务插队...");
			return Result.ok(ttsService.taskJump(busId));
			
		} catch (GuiyuException e){
			logger.error("请求失败！", e);
			return Result.error(e.getErrorCode());
		} catch (Exception ex){
			logger.error("请求失败！", ex);
			return Result.error(AiConstants.AI_REQUEST_FAIL);
		}
	}
	
}
