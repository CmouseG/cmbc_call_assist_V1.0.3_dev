package com.guiji.notice.service;

public interface AuthService {
    boolean isCompanyAdmin(Long userId);

    boolean isCompanyOprater(Long userId);
}
