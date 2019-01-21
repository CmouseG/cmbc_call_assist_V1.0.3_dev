package com.guiji.wxapi.controller;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.wxapi.constant.Constant;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class AuthController {

    @Autowired
    IAuth iAuth;

    @ApiOperation(value = "修改用户密码")
    @GetMapping("updatePassword")
    public Result.ReturnData updatePassWord(@RequestParam("new1") @NotEmpty(message = "密码不能为空") String new1,
                                            @RequestParam("new2") @NotEmpty(message = "确认密码不能为空") String new2,
                                            @RequestParam("old") @NotEmpty(message = "原密码不能为空")  String old, @RequestHeader Long userId) {

        if(!new1.equals(new2)){
            return Result.error(Constant.ERROR_PASSWORD);
        }
        try {
            return iAuth.apiUpdatePassword(new1,old,userId);
        } catch (Exception e) {
            return Result.error(Constant.FAILED_UPDATE_PASSWORD);
        }

    }
}
