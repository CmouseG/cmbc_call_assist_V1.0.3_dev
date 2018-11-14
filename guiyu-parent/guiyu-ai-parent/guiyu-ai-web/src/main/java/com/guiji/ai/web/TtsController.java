package com.guiji.ai.web;

import com.guiji.ai.api.ITts;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.component.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 语音合成
 * Created by ty on 2018/11/13.
 */
@RestController
public class TtsController implements ITts {
    @Override
    @PostMapping(value = "translate")
    public Result.ReturnData<TtsRspVO> translate(TtsReqVO ttsReqVO) {
        return null;
    }
}
