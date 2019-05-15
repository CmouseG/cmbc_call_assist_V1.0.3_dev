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

    /**
     * 获取上级组织ID
     * @param orgId
     * @return
     */
    List<Integer> getSubOrgIdByOrgId(Integer orgId);

    /**
     * 获取用户
     * @param userId
     * @return
     */
    SysUser getUserById(String userId);

    /**
     * 根据用户获取orgId
     * @param userId
     * @return
     */
    Integer getOrgIdByUser(String userId);

    /**
     * 获取所有企业ID
     * @return
     */
    List<Integer> getAllOrgId();
}
