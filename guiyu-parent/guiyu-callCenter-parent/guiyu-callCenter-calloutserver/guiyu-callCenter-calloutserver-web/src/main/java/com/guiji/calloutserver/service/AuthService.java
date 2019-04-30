package com.guiji.calloutserver.service;

import java.util.List;

public interface AuthService {


    /**
     * 坐席，企业客服
     * @param userId
     * @return
     */
    boolean isSeat(Long userId);


    /**
     * 获取用户名
     * @param userId
     * @return
     */
    String getUserName(Long userId);

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
