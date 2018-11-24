package com.guiji.process.server.dao;

import com.guiji.process.server.dao.entity.SysProcessLog;
import com.guiji.process.server.dao.entity.SysProcessLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysProcessLogMapper {
    int countByExample(SysProcessLogExample example);

    int deleteByExample(SysProcessLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysProcessLog record);

    int insertSelective(SysProcessLog record);

    List<SysProcessLog> selectByExample(SysProcessLogExample example);

    SysProcessLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysProcessLog record, @Param("example") SysProcessLogExample example);

    int updateByExample(@Param("record") SysProcessLog record, @Param("example") SysProcessLogExample example);

    int updateByPrimaryKeySelective(SysProcessLog record);

    int updateByPrimaryKey(SysProcessLog record);
}