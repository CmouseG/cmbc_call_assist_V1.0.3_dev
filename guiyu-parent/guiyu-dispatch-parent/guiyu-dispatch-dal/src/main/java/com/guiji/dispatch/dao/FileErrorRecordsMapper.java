package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileErrorRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileErrorRecordsMapper {
    int countByExample(FileErrorRecordsExample example);

    int deleteByExample(FileErrorRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileErrorRecords record);

    int insertSelective(FileErrorRecords record);

    List<FileErrorRecords> selectByExample(FileErrorRecordsExample example);

    FileErrorRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileErrorRecords record, @Param("example") FileErrorRecordsExample example);

    int updateByExample(@Param("record") FileErrorRecords record, @Param("example") FileErrorRecordsExample example);

    int updateByPrimaryKeySelective(FileErrorRecords record);

    int updateByPrimaryKey(FileErrorRecords record);
}