package com.guiji.calloutserver.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.calloutserver.constant.AuthLevelEnum;
import com.guiji.calloutserver.service.AuthService;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    IAuth iAuth;
    @Autowired
    IOrg iorg;

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

    @Override
    public List<Integer> getAllOrgIds(Integer orgId){

        List<Integer> list = new ArrayList<>();
        try {
            Result.ReturnData<List<Integer>> returnData =  iorg.getSubOrgIdByOrgId(orgId);
            if (returnData != null && returnData.getBody() != null) {
                list = returnData.getBody();
            }
        }catch (Exception e){
            log.error("调用接口getSubOrgIdByOrgId出现异常",e);
        }
        if(!list.contains(orgId)){
            list.add(orgId);
        }
        return  list;
    }

    @Override
    public List<Integer> getAllOrgIds()
    {
        Result.ReturnData<List<Integer>> resp = iorg.getAllOrgId();
        List<Integer> result = null;
        if (resp != null && resp.getBody() != null) {
            result = resp.getBody();
        }

        if(result == null)
        {
            result = new ArrayList<>();
        }

        return  result;
    }

    @Override
    public List<Integer> getOrgIdsByAuthlevel(Integer authLevel, Integer orgId)
    {
        if(authLevel == AuthLevelEnum.USER.getLevel() || authLevel == AuthLevelEnum.ORG.getLevel()){

            List<Integer> orgIdList = new ArrayList<>();
            orgIdList.add(orgId);
            return orgIdList;

        }else{
            return   getAllOrgIds(orgId);
        }
    }
}
