package com.guiji.notice.dao;

import com.guiji.notice.dao.entity.NoiceSendLog;
import com.guiji.notice.dao.entity.NoiceSendLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoiceSendLogMapper {
    int countByExample(NoiceSendLogExample example);

    int deleteByExample(NoiceSendLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NoiceSendLog record);

    int insertSelective(NoiceSendLog record);

    List<NoiceSendLog> selectByExample(NoiceSendLogExample example);

    NoiceSendLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NoiceSendLog record, @Param("example") NoiceSendLogExample example);

    int updateByExample(@Param("record") NoiceSendLog record, @Param("example") NoiceSendLogExample example);

    int updateByPrimaryKeySelective(NoiceSendLog record);

    int updateByPrimaryKey(NoiceSendLog record);
}