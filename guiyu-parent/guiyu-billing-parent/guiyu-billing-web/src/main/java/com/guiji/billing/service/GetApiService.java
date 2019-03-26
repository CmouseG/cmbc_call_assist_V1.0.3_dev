package com.guiji.billing.service;

import com.guiji.user.dao.entity.SysOrganization;

public interface GetApiService {

    /**
     * 根据用户ID查询企业组织
     * @param userId
     * @return
     */
    SysOrganization getOrgByUserId(String userId);


}
