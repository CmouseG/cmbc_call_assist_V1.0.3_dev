package com.guiji.process.server.dao;

import com.guiji.process.server.dao.entity.SysProcessTask;
import com.guiji.process.server.dao.entity.SysProcessTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysProcessTaskMapper {
    int countByExample(SysProcessTaskExample example);

    int deleteByExample(SysProcessTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysProcessTask record);

    int insertSelective(SysProcessTask record);

    List<SysProcessTask> selectByExample(SysProcessTaskExample example);

    SysProcessTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysProcessTask record, @Param("example") SysProcessTaskExample example);

    int updateByExample(@Param("record") SysProcessTask record, @Param("example") SysProcessTaskExample example);

    int updateByPrimaryKeySelective(SysProcessTask record);

    int updateByPrimaryKey(SysProcessTask record);
}