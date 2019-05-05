package com.guiji.botsentence.controller;

import com.guiji.botsentence.api.IBotSentenceVoiceInfo;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.botsentence.service.IVoiceInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/botSentence/voiceInfo")
public class VoiceInfoController implements IBotSentenceVoiceInfo {

    @Resource
    private IVoiceInfoService iVoiceInfoService;

    @Override
    @RequestMapping(value="getTtsParamsCountByTemplateId")
    public ServerResult<Integer> getTtsParamsCountByTemplateId(@RequestParam("templateId") String templateId) {

        return ServerResult.createBySuccess(iVoiceInfoService.getTtsParamsCountByTemplateId(templateId));
    }
}
