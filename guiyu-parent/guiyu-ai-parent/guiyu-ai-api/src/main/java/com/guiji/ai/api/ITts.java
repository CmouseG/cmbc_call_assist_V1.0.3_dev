package com.guiji.ai.api;

import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.component.result.Result.ReturnData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

/**
 * TTS对外服务
 * Created by ty on 2018/11/13.
 */
@FeignClient("tts")
public interface ITts {

    /**
     * 语音合成服务
     * @param ttsReqVO
     * @return
     */
    @ApiOperation(value="语音合成")
    @PostMapping(value = "translate")
    public ReturnData<TtsRspVO> translate(TtsReqVO ttsReqVO);
}
