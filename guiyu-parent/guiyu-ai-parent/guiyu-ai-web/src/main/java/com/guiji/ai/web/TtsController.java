package com.guiji.ai.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.ITts;
import com.guiji.ai.tts.TtsReqQueue;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.ai.vo.TtsStatusReqVO;
import com.guiji.ai.vo.TtsStatusRspVO;
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

	@Override
	@PostMapping(value = "translate")
	public ReturnData<String> translate(@RequestBody TtsReqVO ttsReqVO)
	{
		try
		{
			if (ttsReqVO != null && !ttsReqVO.getContents().isEmpty())
			{
				//入库
				ttsService.saveTtsStatus(ttsReqVO);
				//入队列
				TtsReqQueue.getInstance().produce(ttsReqVO);
			}

			return Result.ok("success");

		} catch (Exception e)
		{
			logger.error("请求失败！", e);
			return Result.error(AiConstants.AI_REQUEST);
		}

	}

	/**
	 * 根据busiId查询TTS处理结果
	 */
	@Override
	public ReturnData<TtsRspVO> getTtsResultByBusId(String busId) 
	{
		TtsRspVO ttsRspVO = null;
		try
		{
			String status = ttsService.getTransferStatusByBusId(busId);

			ttsRspVO = new TtsRspVO();
			ttsRspVO.setBusId(busId);
			ttsRspVO.setStatus(status);

			if ("done".equals(status))
			{
				Map<String, String> radioMap = new HashMap<>();
				List<Map<String, String>> resultMapList = ttsService.getTtsTransferResult(busId);
				for (Map<String, String> restltMap : resultMapList)
				{
					radioMap.put(restltMap.get("content"), restltMap.get("audio_url"));
				}
				ttsRspVO.setAudios(radioMap);
			}

		} catch (Exception e)
		{
			logger.error("查询失败", e);
		}

		return Result.ok(ttsRspVO);
	}

	/**
	 * 查询TTS处理状态
	 */
	@Override
	public ReturnData<List<TtsStatusRspVO>> getTtsStatus(TtsStatusReqVO ttsStatusReqVO)
	{
		List<TtsStatusRspVO> statusRspVOList = new ArrayList<>();

		try
		{
			if (ttsStatusReqVO == null){
				return null;
			}
			logger.info("开始查询tts处理状态...");
			List<Map<String, Object>> restltMapList = ttsService.getTtsStatus(ttsStatusReqVO.getStartTime(),
					ttsStatusReqVO.getEndTime(), ttsStatusReqVO.getModel(), ttsStatusReqVO.getStatus());
			
			for(Map<String, Object> restltMap : restltMapList){
				TtsStatusRspVO statusRspVO = new TtsStatusRspVO();
				statusRspVO.setBusId((String) restltMap.get("busi_id"));
				statusRspVO.setModel((String) restltMap.get("model"));
				statusRspVO.setCount((Integer) restltMap.get("count"));
				statusRspVO.setCreateTime((Date) restltMap.get("createTime"));
				statusRspVOList.add(statusRspVO);
			}
			
			
		} catch (Exception e){
			logger.error("查询失败", e);
		}

		return Result.ok(statusRspVOList);
	}
}
