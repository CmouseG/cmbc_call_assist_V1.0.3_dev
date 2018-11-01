package com.guiji.cache.controller;

import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "module-oauth模块,用户Demo Controller")
@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @Autowired
    private RedisUtil redisUtil;
    @ApiOperation(value="module模块,Test方法", notes="Test")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        // redis操作
        redisUtil.set("hi","hello");
        redisUtil.expire("hi",10);
        return "OK";
    }
}
