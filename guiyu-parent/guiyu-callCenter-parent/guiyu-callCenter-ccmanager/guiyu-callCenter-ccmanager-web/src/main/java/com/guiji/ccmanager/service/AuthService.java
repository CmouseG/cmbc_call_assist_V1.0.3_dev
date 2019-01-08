package com.guiji.ccmanager.service;

public interface AuthService {

    /**
     * 是否是代理商
     * @param userId
     * @return
     */
    boolean isAgent(Long userId);

    /**
     * 是否是企业管理员
     * @param userId
     * @return
     */
    boolean isCompanyAdmin(Long userId);

    /**
     * 代理商或者企业管理员
     * @param userId
     * @return
     */
    boolean isAgentOrCompanyAdmin(Long userId);
}
