package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchHour;
import com.guiji.dispatch.dao.entity.DispatchHourExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DispatchHourMapper {
    int countByExample(DispatchHourExample example);

    int deleteByExample(DispatchHourExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DispatchHour record);

    int insertSelective(DispatchHour record);

    List<DispatchHour> selectByExample(DispatchHourExample example);

    DispatchHour selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DispatchHour record, @Param("example") DispatchHourExample example);

    int updateByExample(@Param("record") DispatchHour record, @Param("example") DispatchHourExample example);

    int updateByPrimaryKeySelective(DispatchHour record);

    int updateByPrimaryKey(DispatchHour record);
}