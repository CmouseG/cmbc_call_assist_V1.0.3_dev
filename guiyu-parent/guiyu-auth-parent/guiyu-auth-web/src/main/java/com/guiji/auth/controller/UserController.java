package com.guiji.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ty on 2018/10/22.
 */
@RestController
@Api(tags = "用户中心", description = "用户相关接口")
public class UserController{

    @RequestMapping("/test")
    @ApiOperation(value = "测试")
    public String test(String userName) {
        return "hello" + userName;
    }
}
