package com.guiji.dispatch.service;

import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;

import java.util.List;

public interface GetApiService {

    /**
     * 根据用户ID查询企业组织
     * @param userId
     * @return
     */
    SysOrganization getOrgByUserId(String userId);

    List<Integer> getSubOrgIdByOrgId(Integer orgId);

    SysUser getUserById(String userId);
}
