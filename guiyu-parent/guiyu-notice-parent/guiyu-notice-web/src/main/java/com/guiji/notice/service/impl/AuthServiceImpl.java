package com.guiji.notice.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.model.UserAuth;
import com.guiji.component.result.Result;
import com.guiji.notice.service.AuthService;
import com.guiji.user.dao.entity.SysOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    IAuth auth;

    @Override
    public String getOrgCode(Long userId) {
        try {
            Result.ReturnData<SysOrganization> returnData = auth.getOrgByUserId(userId);
            if (returnData != null && returnData.success) {
                return returnData.getBody().getCode();
            }
        } catch (Exception e) {
            log.error("getOrgByUserId出现异常", e);
        }
        return null;
    }

    @Override
    public int getAuthLevel(Long userId) {
        try {

            Result.ReturnData<UserAuth> returnData = auth.queryUserDataAuth(userId);
            if (returnData != null && returnData.getBody() != null) {
                UserAuth userAuth = returnData.getBody();
                if (userAuth != null && userAuth.getAuthLevel() != null) {
                    return userAuth.getAuthLevel();
                }
            }
            //什么都没有，给最低的权限
            log.info("queryUserDataAuth没有获取到数据，给最低的权限[{}]", userId);
            return 1;
        } catch (Exception e) {
            log.error("queryUserDataAuth出现异常", e);
            return 1;
        }
    }

}
