package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.ModularLogs;
import com.guiji.dispatch.dao.entity.ModularLogsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ModularLogsMapper {
    int countByExample(ModularLogsExample example);

    int deleteByExample(ModularLogsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ModularLogs record);

    int insertSelective(ModularLogs record);

    List<ModularLogs> selectByExample(ModularLogsExample example);

    ModularLogs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ModularLogs record, @Param("example") ModularLogsExample example);

    int updateByExample(@Param("record") ModularLogs record, @Param("example") ModularLogsExample example);

    int updateByPrimaryKeySelective(ModularLogs record);

    int updateByPrimaryKey(ModularLogs record);
}