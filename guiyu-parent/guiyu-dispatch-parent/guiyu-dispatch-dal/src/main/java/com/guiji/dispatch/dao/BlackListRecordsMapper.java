package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.BlackListRecords;
import com.guiji.dispatch.dao.entity.BlackListRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlackListRecordsMapper {
    int countByExample(BlackListRecordsExample example);

    int deleteByExample(BlackListRecordsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlackListRecords record);

    int insertSelective(BlackListRecords record);

    List<BlackListRecords> selectByExample(BlackListRecordsExample example);

    BlackListRecords selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlackListRecords record, @Param("example") BlackListRecordsExample example);

    int updateByExample(@Param("record") BlackListRecords record, @Param("example") BlackListRecordsExample example);

    int updateByPrimaryKeySelective(BlackListRecords record);

    int updateByPrimaryKey(BlackListRecords record);
}