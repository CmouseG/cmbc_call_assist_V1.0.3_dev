package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.LineConfig;
import com.guiji.callcenter.dao.entity.LineConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LineConfigMapper {
    int countByExample(LineConfigExample example);

    int deleteByExample(LineConfigExample example);

    int insert(LineConfig record);

    int insertSelective(LineConfig record);

    List<LineConfig> selectByExample(LineConfigExample example);

    int updateByExampleSelective(@Param("record") LineConfig record, @Param("example") LineConfigExample example);

    int updateByExample(@Param("record") LineConfig record, @Param("example") LineConfigExample example);
}