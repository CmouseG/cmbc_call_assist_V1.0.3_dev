package com.guiji.auth.controller;

import com.guiji.auth.api.IApiLogin;
import com.guiji.auth.service.UserService;
import com.guiji.user.dao.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiLoginController implements IApiLogin {

    @Autowired
    private UserService service;

    @Override
    public SysUser getUserByAccess(String accessKey, String secretKey) {

        SysUser sysUser =  service.getUserByAccess(accessKey, secretKey);
        return sysUser;
    }
}
