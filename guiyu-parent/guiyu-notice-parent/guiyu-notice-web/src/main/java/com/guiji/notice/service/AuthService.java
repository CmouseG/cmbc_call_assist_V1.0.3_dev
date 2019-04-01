package com.guiji.notice.service;

public interface AuthService {

    int getAuthLevel(Long userId);

    String getOrgCode(Long userId);
}
