package com.guiji.ai.api;

import java.io.File;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.guiji.ai.vo.AsynPostReqVO;
import com.guiji.ai.vo.SynPostReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.component.result.Result.ReturnData;

/**
 * AI对外服务
 */
@FeignClient("guiyu-ai-web")
public interface IAi {
	
	/**
     * 语音合成（同步）
     */
	@PostMapping(value = "synPost")
	public ReturnData<File> synPost(@RequestBody SynPostReqVO postVO);
	
	/**
     * 语音合成（异步）
     */
	@PostMapping(value = "asynPost")
	public ReturnData<String> asynPost(@RequestBody AsynPostReqVO postVO);
	
	/**
	 *回调接口
	 */
	@PostMapping(value = "callback")
	public ReturnData<TtsRspVO> callback(@RequestBody TtsRspVO ttsRsp);
	
	/**
	 * 查询接口
	 */
	@GetMapping(value = "getResult")
	public ReturnData<TtsRspVO> getResult(String busId);
}
