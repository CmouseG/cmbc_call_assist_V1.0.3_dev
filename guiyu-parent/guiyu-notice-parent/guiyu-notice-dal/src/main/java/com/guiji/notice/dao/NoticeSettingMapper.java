package com.guiji.notice.dao;

import com.guiji.notice.dao.entity.NoticeSetting;
import com.guiji.notice.dao.entity.NoticeSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeSettingMapper {
    int countByExample(NoticeSettingExample example);

    int deleteByExample(NoticeSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoticeSetting record);

    int insertSelective(NoticeSetting record);

    List<NoticeSetting> selectByExample(NoticeSettingExample example);

    NoticeSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoticeSetting record, @Param("example") NoticeSettingExample example);

    int updateByExample(@Param("record") NoticeSetting record, @Param("example") NoticeSettingExample example);

    int updateByPrimaryKeySelective(NoticeSetting record);

    int updateByPrimaryKey(NoticeSetting record);
}