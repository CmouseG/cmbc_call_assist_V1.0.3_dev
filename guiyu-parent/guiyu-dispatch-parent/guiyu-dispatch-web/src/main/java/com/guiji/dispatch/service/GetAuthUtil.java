package com.guiji.dispatch.service;

import com.guiji.component.result.Result;
import com.guiji.dispatch.constant.AuthConstant;
import com.guiji.dispatch.enums.AuthLevelEnum;
import com.guiji.user.dao.entity.SysOrganization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetAuthUtil {

    @Autowired
    private GetApiService getApiService;

    /**
     * 根据权限等级，如果不是本人，则返回null
     * @param authLevel
     * @param userId
     * @return
     */
    public String getUserIdByAuthLevel(Integer authLevel, String userId){
        if(AuthLevelEnum.USER.getLevel() == authLevel){
        //    return null != userId ? userId : AuthConstant.superUserId;
            return userId;
        }else{
            return null;
        }
    }

    /**
     * 根据权限等级，不是本组织或者本组织以下，则返回null
     * @param authLevel
     * @param orgCode
     * @return
     */
    public String getOrgCodeByAuthLevel(Integer authLevel, String orgCode){
        if(AuthLevelEnum.ORG.getLevel() == authLevel || AuthLevelEnum.ORG_EXT.getLevel() == authLevel){
            /*if(StringUtils.isEmpty(orgCode)){
                //获取用户ID
                userId = null != userId ? userId : AuthConstant.superUserId;
                //获取企业组织
                SysOrganization org = getApiService.getOrgByUserId(userId);
                return null != org ? org.getCode() : AuthConstant.superOrgCode;
            }else{
                return orgCode;
            }*/
            return orgCode;
        }else{
            return null;
        }
    }

    public List<Integer> getOrgIdsByAuthLevel(Integer authLevel, Integer orgId){
        List<Integer> orgIds = new ArrayList<Integer>();
        if(AuthLevelEnum.ORG.getLevel() == authLevel){//本组织
            orgIds.add(orgId);
        }else if(AuthLevelEnum.ORG_EXT.getLevel() == authLevel){//本组织或本组织及以下组织
            orgIds = getApiService.getSubOrgIdByOrgId(orgId);
        }else{
            orgIds.add(orgId);
        }
        return orgIds;
    }

}
