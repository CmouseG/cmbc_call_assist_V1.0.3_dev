package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchLog;
import com.guiji.dispatch.dao.entity.DispatchLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DispatchLogMapper {
    int countByExample(DispatchLogExample example);

    int deleteByExample(DispatchLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchLog record);

    int insertSelective(DispatchLog record);

    List<DispatchLog> selectByExampleWithBLOBs(DispatchLogExample example);

    List<DispatchLog> selectByExample(DispatchLogExample example);

    DispatchLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchLog record, @Param("example") DispatchLogExample example);

    int updateByExampleWithBLOBs(@Param("record") DispatchLog record, @Param("example") DispatchLogExample example);

    int updateByExample(@Param("record") DispatchLog record, @Param("example") DispatchLogExample example);

    int updateByPrimaryKeySelective(DispatchLog record);

    int updateByPrimaryKeyWithBLOBs(DispatchLog record);

    int updateByPrimaryKey(DispatchLog record);
}