package com.guiji.service;

import java.util.List;

public interface AuthService {


    List<Integer> getAllOrgIds(Integer orgId);

    List<Integer> getAllOrgIds();

    /**
     * 获取相关的组织list
     * @param authLevel
     * @param orgId
     * @return
     */
    List<Integer> getOrgIdsByAuthlevel(Integer authLevel, Integer orgId);
}
