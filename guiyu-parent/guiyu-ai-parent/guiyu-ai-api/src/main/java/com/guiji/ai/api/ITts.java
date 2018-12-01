package com.guiji.ai.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.ai.vo.TtsReqVO;
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
}
