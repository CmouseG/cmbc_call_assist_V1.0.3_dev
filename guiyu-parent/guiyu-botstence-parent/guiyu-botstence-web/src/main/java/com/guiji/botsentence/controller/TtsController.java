package com.guiji.botsentence.controller;

import com.guiji.botsentence.service.ITtsService;
import com.guiji.botsentence.vo.TtsVoiceDetailVO;
import com.guiji.botsentence.vo.TtsVoiceReqVO;
import com.guiji.botsentence.vo.TtsVoiceVO;
import com.guiji.common.exception.CommonException;
import com.guiji.component.result.ServerResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("tts")
public class TtsController {

    private Logger logger = LoggerFactory.getLogger(TtsController.class);

    @Resource
    private ITtsService iTtsService;

    @PostMapping("queryTtsVoiceList")
    public ServerResult<List<TtsVoiceVO>> queryTtsVoiceInfoVOList(@RequestParam("processId") String processId){
        if(StringUtils.isBlank(processId)){
            throw new CommonException("参数不完整！");
        }
        return ServerResult.createBySuccess(iTtsService.queryTtsVoiceInfoVOList(processId));
    }

    @PostMapping("queryTtsVoiceDetailById")
    public ServerResult<TtsVoiceDetailVO> queryTtsVoiceDetailByVoiceId(@RequestParam("voliceId") Long voliceId){
        if(null == voliceId){
            throw new CommonException("参数不完整！");
        }

        return ServerResult.createBySuccess(iTtsService.queryTtsVoiceDetailByVoiceId(voliceId));
    }

    @PostMapping("updateTtsVoice")
    public ServerResult<String> updateTtsVoice(@RequestBody TtsVoiceReqVO ttsVoiceReqVO, @RequestHeader String userId){
        if(StringUtils.isBlank(userId)){
            throw new CommonException("参数不完整！userId为空！");
        }

        try {
            iTtsService.updateTtsVoice(ttsVoiceReqVO, userId);
        }catch (CommonException ce){
            throw ce;
        }catch (Exception e){
            logger.error("save tts voice failed", e);
            return ServerResult.createByError();
        }
        return ServerResult.createBySuccess("保存成功！");
    }


}
