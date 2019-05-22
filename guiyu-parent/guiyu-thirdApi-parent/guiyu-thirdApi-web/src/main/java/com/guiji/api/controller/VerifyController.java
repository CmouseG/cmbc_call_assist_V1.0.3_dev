package com.guiji.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.component.result.Result;
import com.guiji.service.VerifyService;
import com.guiji.third.api.VerifyApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@Slf4j
public class VerifyController implements VerifyApi {

    @Autowired
    VerifyService verifyService;

    /**
     * zuul会调用该接口，进行用户/数据签名等信息的校验
     *
     * @param msg
     * @return
     */
    @Override
    @PostMapping(value = "/thirdApi/check")
    public Result.ReturnData<Boolean> check(@RequestParam("msg") String msg) {
        log.info("接收到参数：{}", JSON.toJSONString(msg));
        JSONObject json = JSON.parseObject(msg);
        try {
            verifyService.verify(json);
        } catch (Exception e) {
            log.info("验签失败：{}", json.get("clientId"));
            return new Result.ReturnData<>(false);
        }
        return new Result.ReturnData<>(true);
    }
}
