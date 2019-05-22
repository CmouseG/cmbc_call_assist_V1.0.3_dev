package com.guiji.third.api;

import io.swagger.annotations.Api;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.guiji.component.result.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * @Description: 第三方api对外服务
 */
@Api(tags = "第三方api")
@FeignClient(value = "guiyu-thirdApi-web")
public interface VerifyApi {

    @PostMapping(value = "/thirdApi/check")
    Result.ReturnData<Boolean> check(@RequestParam("msg") String msg);

}
