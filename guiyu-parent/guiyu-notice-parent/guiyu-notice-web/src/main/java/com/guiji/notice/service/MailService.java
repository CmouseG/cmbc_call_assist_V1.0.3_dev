package com.guiji.notice.service;

import java.util.List;
import java.util.Map;

public interface MailService {
    List<Map> queryMailList(Long userId, String isRead,String noticeType, int pageSizeInt, int pageNoInt);

    void deleteMailById(List<Integer> ids);

    Map readMailById(Integer id);

    int countMailList(Long userId, String isRead, String noticeType);
}
