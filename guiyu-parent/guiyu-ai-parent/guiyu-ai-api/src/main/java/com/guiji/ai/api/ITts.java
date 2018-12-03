package com.guiji.ai.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.ai.vo.TtsStatusReqVO;
import com.guiji.ai.vo.TtsStatusRspVO;
import com.guiji.component.result.Result.ReturnData;

import io.swagger.annotations.ApiOperation;

/**
 * TTS对外服务
 * Created by ty on 2018/11/13.
 */
@FeignClient("guiyu-ai-web")
public interface ITts {

    /**
     * 语音合成服务
     * @param ttsReqVO
     * @return
     */
    @ApiOperation(value="语音合成")
    @PostMapping(value = "translate")
    public ReturnData<String> translate(TtsReqVO ttsReqVO);
    
    /**
     * 根据busiId查询TTS处理结果
     * @param busiId
     * @return
     */
    @ApiOperation(value="根据busiId查询TTS处理结果")
    @PostMapping(value = "queryTtsResultByBusId")
    public ReturnData<TtsRspVO> getTtsResultByBusId(String busId);
    
    /**
     * 查询TTS处理状态
     * @param busiId
     * @return
     */
    @ApiOperation(value="查询TTS处理状态")
    @PostMapping(value = "queryTtsStatus")
    public ReturnData<List<TtsStatusRspVO>> getTtsStatus(TtsStatusReqVO ttsStatusReqVO);  
}
