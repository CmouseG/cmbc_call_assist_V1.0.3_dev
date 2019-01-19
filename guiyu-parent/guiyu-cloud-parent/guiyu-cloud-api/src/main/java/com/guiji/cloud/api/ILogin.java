package com.guiji.cloud.api;

import com.guiji.component.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-cloud-zuul")
public interface ILogin {

    @GetMapping("login")
    Result.ReturnData login(@RequestParam("username") String username, @RequestParam("password") String password);


    @GetMapping("apiLogin")
    Result.ReturnData apiLogin (@RequestParam("accessKey") String accessKey,@RequestParam("secretKey") String secretKey);

}
