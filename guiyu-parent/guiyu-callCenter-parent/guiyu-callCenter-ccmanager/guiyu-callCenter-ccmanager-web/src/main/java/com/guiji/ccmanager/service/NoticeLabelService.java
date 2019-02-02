package com.guiji.ccmanager.service;

public interface NoticeLabelService {
    String queryNoticeIntent(String orgCode);

    void updateNoticeIntent(String labels, String orgCode);
}
