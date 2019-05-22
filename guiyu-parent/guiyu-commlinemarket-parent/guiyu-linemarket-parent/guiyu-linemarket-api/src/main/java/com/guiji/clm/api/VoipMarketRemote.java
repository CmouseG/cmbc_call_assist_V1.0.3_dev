package com.guiji.clm.api;

import com.guiji.clm.model.SimLineVo;
import com.guiji.component.result.Result;
import io.swagger.annotations.Api;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "线路市场服务")
@FeignClient(value = "guiyu-linemarket-web")
public interface VoipMarketRemote {

    @RequestMapping(value = "/voip/querySimLineInfo", method = RequestMethod.POST)
    Result.ReturnData<List<SimLineVo>> querySimLineInfo(@RequestParam("userId") Long userId);
}
