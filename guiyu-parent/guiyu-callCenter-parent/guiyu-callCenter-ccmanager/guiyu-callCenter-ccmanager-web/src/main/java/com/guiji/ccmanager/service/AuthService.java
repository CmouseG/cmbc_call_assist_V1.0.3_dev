package com.guiji.ccmanager.service;

public interface AuthService {

    boolean isAgent(Long userId);
    boolean isCompanyAdmin(Long userId);
}
