package com.guiji.notice.dao;

import com.guiji.notice.dao.entity.NoticeSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeSettingExtMapper {

    void insertNoticeSettingBatch(List<NoticeSetting> list);

    void addNoticeSettingReceiver(@Param("orgCode") String orgCode,@Param("userId")  String userId);
}