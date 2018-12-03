package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsResultMapper;
import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.tts.handler.SaveTtsStatusHandler;
import com.guiji.ai.tts.service.ITtsService;
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
	private RedisUtil redisUtil;
	@Autowired
	IRobotRemote iRobotRemote;
	@Autowired
	TtsStatusMapper ttsStatusMapper;
	@Autowired
	TtsResultMapper ttsResultMapper;
	@Autowired
	TtsServiceProvideFactory serviceProvideFactory;

	@Override
	public void translate(TtsReqVO ttsReqVO)
	{
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		// 结果集
		Map<String, String> radioMap = new HashMap<String, String>();
		String status = "S";
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
			status = "F";
			errMsg = e.getMessage();
		}

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
		try
		{
			// 判断是否已经合成
			audioUrl = (String) redisUtil.get(model + "_" + text);
			if (audioUrl != null)
			{
				return audioUrl;
			}
			// 合成
			audioUrl = serviceProvideFactory.getTtsServiceProvide(model).transfer(busId, model, text);

			redisUtil.set(model + "_" + text, audioUrl); // 返回值url存入redis

		} catch (Exception e)
		{
			logger.error("转换错误!", e);
		}
		return audioUrl;
	}

	@Override
	@Transactional
	public String getTransferStatusByBusId(String busId) throws Exception
	{
		String status = ttsStatusMapper.getReqStatusByBusId(busId);
		switch (status)
		{
		case "2":
			return "done";
		case "1":
			return "doing";
		default:
			return "undo";
		}
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getTtsStatus(Date startTime, Date endTime, String model, String status) throws Exception
	{
		List<Map<String, Object>> restltMapList = new ArrayList<>();
		
		restltMapList = ttsStatusMapper.getTtsStatus(startTime, endTime, model, status);
		
		return restltMapList;
	}

	@Override
	@Transactional
	public List<Map<String, String>> getTtsTransferResult(String busId)
	{
		List<Map<String, String>> restltMapList = new ArrayList<>();
		
		restltMapList = ttsResultMapper.getTtsTransferResult(busId);
		
		return restltMapList;
	}

	@Override
	public void saveTtsStatus(TtsReqVO ttsReqVO)
	{
		TtsStatus ttsStatus = new TtsStatus();
		ttsStatus.setBusId(ttsReqVO.getBusId());
		ttsStatus.setModel(ttsReqVO.getModel());
		ttsStatus.setStatus("0");
		ttsStatus.setCreateTime(new Date());
		ttsStatus.setText_count(ttsReqVO.getContents().size());
		SaveTtsStatusHandler.getInstance().add(ttsStatus, ttsStatusMapper);
		
	}
}
