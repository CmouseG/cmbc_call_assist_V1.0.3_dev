package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.callcenter.dao.entity.LineCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LineCountMapper {
    int countByExample(LineCountExample example);

    int deleteByExample(LineCountExample example);

    int insert(LineCount record);

    int insertSelective(LineCount record);

    List<LineCount> selectByExample(LineCountExample example);

    int updateByExampleSelective(@Param("record") LineCount record, @Param("example") LineCountExample example);

    int updateByExample(@Param("record") LineCount record, @Param("example") LineCountExample example);
}