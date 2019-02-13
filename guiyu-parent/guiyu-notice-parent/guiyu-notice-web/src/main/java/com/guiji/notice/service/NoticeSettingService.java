package com.guiji.notice.service;

import com.guiji.notice.dao.entity.NoticeSetting;
import com.guiji.notice.entity.NoticeSettingUpdateReq;
import com.guiji.notice.entity.SettingIntent;
import com.guiji.notice.entity.User;

import java.util.List;

public interface NoticeSettingService {
    List<NoticeSetting> getNoticeSettingList(String orgCode);

    void addNoticeSetting(String orgCode);

    void restoreDefaultSettings(String orgCode);

    void setReceivers(String receiverIds, Integer integer);

    List<User> getUserList4Receive(String orgCode);

    void updateNoticeSetting(List<NoticeSettingUpdateReq> NoticeSettingUpdateReqs);

    void addNoticeSettingReceiver(String orgCode, Long userId);

    void addWeixinNoticeSettingReceiver(String orgCode, Long userId);

    List<SettingIntent> queryNoticeIntent(Long userId, Integer id);

    void updateNoticeIntent(String labels, Integer integer);
}
