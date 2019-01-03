package com.guiji.process.server.dao;

import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysProcessMapper {
    int countByExample(SysProcessExample example);

    int deleteByExample(SysProcessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysProcess record);

    int insertSelective(SysProcess record);

    List<SysProcess> selectByExample(SysProcessExample example);

    SysProcess selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysProcess record, @Param("example") SysProcessExample example);

    int updateByExample(@Param("record") SysProcess record, @Param("example") SysProcessExample example);

    int updateByPrimaryKeySelective(SysProcess record);

    int updateByPrimaryKey(SysProcess record);
}