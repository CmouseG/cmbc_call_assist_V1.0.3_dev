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

    /**
     * 坐席，企业客服
     * @param userId
     * @return
     */
    boolean isSeat(Long userId);

    /**
     *  企业客服或者是代理商
     * @param userId
     * @return
     */
    boolean isSeatOrAgent(Long userId);

    /**
     * 获取用户名
     * @param userId
     * @return
     */
    String getUserName(Long userId);
}
