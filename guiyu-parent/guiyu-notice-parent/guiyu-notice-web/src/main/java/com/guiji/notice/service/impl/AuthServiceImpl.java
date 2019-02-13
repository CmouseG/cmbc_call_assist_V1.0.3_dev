package com.guiji.notice.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.notice.service.AuthService;
import com.guiji.user.dao.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    IAuth auth;

    /**
     * 判断是否是企业管理员
     *
     * @param userId
     * @return
     */
    public boolean isCompanyAdmin(Long userId) {
        Result.ReturnData<List<SysRole>> result = auth.getRoleByUserId(userId);
        List<SysRole> list = result.getBody();
        if (list != null && list.size() > 0) {
            for (SysRole sysRole : list) {
                if (sysRole.getId().intValue() == 3) { //企业管理员
                    return true;
                }
            }
        }
        return false;
    }
}
