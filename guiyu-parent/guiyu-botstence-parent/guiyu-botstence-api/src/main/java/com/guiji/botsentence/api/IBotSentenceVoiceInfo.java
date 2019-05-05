package com.guiji.botsentence.api;

import com.guiji.botsentence.api.entity.ServerResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-botstence-web")
public interface IBotSentenceVoiceInfo {

    @RequestMapping(value="/botSentence/voiceInfo/getTtsParamsCountByTemplateId")
    ServerResult<Integer> getTtsParamsCountByTemplateId(@RequestParam("templateId") String templateId);
}
