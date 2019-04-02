package com.guiji.ccmanager.service;

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
}
