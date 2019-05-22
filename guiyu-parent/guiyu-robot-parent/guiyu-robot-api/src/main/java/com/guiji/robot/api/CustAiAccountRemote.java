package com.guiji.robot.api;

import com.guiji.component.result.Result;
import com.guiji.robot.model.CustTemplateVo;
import io.swagger.annotations.Api;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="机器人能力中心")
@FeignClient("guiyu-robot-web")
public interface CustAiAccountRemote {

    @RequestMapping(value = "/account/queryUserTemplateInfo", method = RequestMethod.POST)
    Result.ReturnData<CustTemplateVo> queryUserTemplateInfo(@RequestParam(value = "userId", required = false) Integer userId,
                                                             @RequestParam(value = "templateId", required = true)String templateId);

    @PostMapping(value = "/account/queryCustTemplateList")
    Result.ReturnData<List<CustTemplateVo>> queryCustTemplateList(@RequestParam("userId") Long userId);

}
