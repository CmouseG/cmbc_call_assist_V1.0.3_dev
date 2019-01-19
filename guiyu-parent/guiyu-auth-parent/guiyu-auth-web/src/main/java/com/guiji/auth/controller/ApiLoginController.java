package com.guiji.auth.controller;

import com.guiji.auth.api.IApiLogin;
import com.guiji.auth.service.UserService;
import com.guiji.user.dao.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiLoginController implements IApiLogin {

    @Autowired
    private UserService service;

    @Override
    @GetMapping("getUserByAccess")
    public SysUser getUserByAccess(@RequestParam("accessKey")String accessKey, @RequestParam("secretKey")String secretKey) {

        SysUser sysUser =  service.getUserByAccess(accessKey, secretKey);
        return sysUser;
    }
}
