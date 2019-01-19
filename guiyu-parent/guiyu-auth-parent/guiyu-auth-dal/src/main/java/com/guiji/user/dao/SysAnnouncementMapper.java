package com.guiji.user.dao;

import com.guiji.user.dao.entity.SysAnnouncement;
import com.guiji.user.dao.entity.SysAnnouncementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAnnouncementMapper {
    int countByExample(SysAnnouncementExample example);

    int deleteByExample(SysAnnouncementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAnnouncement record);

    int insertSelective(SysAnnouncement record);

    List<SysAnnouncement> selectByExample(SysAnnouncementExample example);

    SysAnnouncement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAnnouncement record, @Param("example") SysAnnouncementExample example);

    int updateByExample(@Param("record") SysAnnouncement record, @Param("example") SysAnnouncementExample example);

    int updateByPrimaryKeySelective(SysAnnouncement record);

    int updateByPrimaryKey(SysAnnouncement record);
}