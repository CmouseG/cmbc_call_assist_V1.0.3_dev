package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.ai.tts.JumpTaskQueue;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.IStatusService;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.tts.service.impl.func.TtsServiceProvideFactory;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TtsCallback;
import com.guiji.utils.RedisUtil;

/**
 * Created by ty on 2018/11/13.
 */
@Service
public class TtsServiceImpl implements ITtsService
{
	private static Logger logger = LoggerFactory.getLogger(TtsServiceImpl.class);

	@Autowired
	RedisUtil redisUtil;
	@Autowired
	IRobotRemote iRobotRemote;
	@Autowired
	IStatusService statusService;
	@Autowired
	TtsServiceProvideFactory serviceProvideFactory;

	@Override
	public void translate(TtsReqVO ttsReqVO)
	{
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		// 结果集
		Map<String, String> radioMap = new HashMap<String, String>();
		String tableStatus = AiConstants.FINISHED; //表状态
		String status = "S"; //返回状态
		String errMsg = null;

		try
		{
			List<String> taskList = ttsReqVO.getContents();
			// 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
			CompletableFuture[] cfs = taskList.stream()
					.map(text -> CompletableFuture
							.supplyAsync(() -> calc(ttsReqVO.getBusId(), ttsReqVO.getModel(), text), executorService)
							.whenComplete((s, e) ->
							{
								radioMap.put(text, s);
							}))
					.toArray(CompletableFuture[]::new);

			// 封装后无返回值，必须自己whenComplete()获取
			CompletableFuture.allOf(cfs).join();
			
		} catch (Exception e)
		{
			logger.error("处理失败！", e);
			status = "F";
			errMsg = e.getMessage();
			tableStatus = AiConstants.FAIL;
		}

		statusService.updateStatusByBusId(ttsReqVO.getBusId(), tableStatus);
		
		// 回调机器人接口，返回数据
		TtsCallback ttsCallback = new TtsCallback();
		ttsCallback.setBusiId(ttsReqVO.getBusId());
		ttsCallback.setStatus(status);
		ttsCallback.setErrorMsg(errMsg);
		ttsCallback.setAudios(radioMap);

		List<TtsCallback> resultList = new ArrayList<TtsCallback>();
		resultList.add(ttsCallback);
		iRobotRemote.ttsCallback(resultList);
	}

	private String calc(String busId, String model, String text)
	{
		String audioUrl = null;

		// 判断是否已经合成
		audioUrl = (String) redisUtil.get(model + "_" + text);
		if (audioUrl != null)
		{
			return audioUrl;
		}
		// 合成
		audioUrl = serviceProvideFactory.getTtsServiceProvide(model).transfer(busId, model, text);

		redisUtil.set(model + "_" + text, audioUrl, 60*30); // 返回值url存入redis 失效时间为30分钟

		return audioUrl;
	}

	/**
	 * 任务插队
	 */
	@Override
	public Boolean taskJump(String busId)
	{
		TtsReqVO ttsReqVo = (TtsReqVO) redisUtil.get(AiConstants.TASK + busId);
		if(ttsReqVo == null){
			logger.info("此任务正在执行或已执行结束，不可以插队！");
			return false;
		}
		
		//放入插队队列
		try
		{
			JumpTaskQueue.getInstance().produce(ttsReqVo);
		} catch (InterruptedException e)
		{
			logger.error("插队队列 put错误");
			return false;
		}
		
		return true;
	}

	@Override
	public void saveTask(TtsReqVO ttsReqVO)
	{
		redisUtil.set(AiConstants.TASK + ttsReqVO.getBusId(), ttsReqVO);
	}

}
