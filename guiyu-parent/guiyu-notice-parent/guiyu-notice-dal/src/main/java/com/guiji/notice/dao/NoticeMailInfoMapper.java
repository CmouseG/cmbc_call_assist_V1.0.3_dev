package com.guiji.notice.dao;

import com.guiji.notice.dao.entity.NoticeMailInfo;
import com.guiji.notice.dao.entity.NoticeMailInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeMailInfoMapper {
    int countByExample(NoticeMailInfoExample example);

    int deleteByExample(NoticeMailInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoticeMailInfo record);

    int insertSelective(NoticeMailInfo record);

    List<NoticeMailInfo> selectByExample(NoticeMailInfoExample example);

    NoticeMailInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoticeMailInfo record, @Param("example") NoticeMailInfoExample example);

    int updateByExample(@Param("record") NoticeMailInfo record, @Param("example") NoticeMailInfoExample example);

    int updateByPrimaryKeySelective(NoticeMailInfo record);

    int updateByPrimaryKey(NoticeMailInfo record);
}