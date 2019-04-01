package com.guiji.ccmanager.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    IAuth iAuth;


    @Override
    public boolean isSeat(Long userId) {
        try {
            Result.ReturnData<Boolean> returnData = iAuth.isAgentUser(userId.intValue());

            if (returnData != null && returnData.getBody() != null) {
                return returnData.getBody();
            }
        }catch (Exception e){
            log.error("调用接口isAgentUser出现异常",e);
        }
        return  false;
    }


    @Override
    public String getUserName(Long userId) {

        Result.ReturnData<SysUser> returnData = iAuth.getUserById(userId);
        return returnData.getBody().getUsername();
    }
}
