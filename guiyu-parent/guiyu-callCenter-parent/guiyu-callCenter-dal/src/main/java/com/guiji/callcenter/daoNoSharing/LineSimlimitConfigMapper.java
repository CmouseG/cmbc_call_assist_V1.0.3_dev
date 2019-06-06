package com.guiji.callcenter.daoNoSharing;

import com.guiji.callcenter.dao.entity.LineSimlimitConfig;
import com.guiji.callcenter.dao.entity.LineSimlimitConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LineSimlimitConfigMapper {
    int countByExample(LineSimlimitConfigExample example);

    int deleteByExample(LineSimlimitConfigExample example);

    int deleteByPrimaryKey(Integer lineId);

    int insert(LineSimlimitConfig record);

    int insertSelective(LineSimlimitConfig record);

    List<LineSimlimitConfig> selectByExample(LineSimlimitConfigExample example);

    LineSimlimitConfig selectByPrimaryKey(Integer lineId);

    int updateByExampleSelective(@Param("record") LineSimlimitConfig record, @Param("example") LineSimlimitConfigExample example);

    int updateByExample(@Param("record") LineSimlimitConfig record, @Param("example") LineSimlimitConfigExample example);

    int updateByPrimaryKeySelective(LineSimlimitConfig record);

    int updateByPrimaryKey(LineSimlimitConfig record);
}