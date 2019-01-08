package com.guiji.ccmanager.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    IAuth iAuth;

    /**
     * 是否是代理商
     * @param userId
     * @return
     */
    @Override
    public boolean isAgent(Long userId){
        Result.ReturnData<List<SysRole>> result =  iAuth.getRoleByUserId(userId);
        List<SysRole> listRole = result.getBody();
        if(listRole!=null && listRole.size()>0){
            for(SysRole sysRole:listRole){
                if(sysRole.getId()==2){
                    return true;
                }
            }
        }
        return  false;
    }

    @Override
    public boolean isCompanyAdmin(Long userId) {
        Result.ReturnData<List<SysRole>> result =  iAuth.getRoleByUserId(userId);
        List<SysRole> listRole = result.getBody();
        if(listRole!=null && listRole.size()>0){
            for(SysRole sysRole:listRole){
                if(sysRole.getId()==3){
                    return true;
                }
            }
        }
        return  false;
    }
}
