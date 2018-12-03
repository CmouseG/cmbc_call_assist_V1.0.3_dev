package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.TtsCallbackHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TtsCallbackHisMapper {
    int countByExample(TtsCallbackHisExample example);

    int deleteByExample(TtsCallbackHisExample example);

    int deleteByPrimaryKey(String id);

    int insert(TtsCallbackHis record);

    int insertSelective(TtsCallbackHis record);

    List<TtsCallbackHis> selectByExampleWithBLOBs(TtsCallbackHisExample example);

    List<TtsCallbackHis> selectByExample(TtsCallbackHisExample example);

    TtsCallbackHis selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TtsCallbackHis record, @Param("example") TtsCallbackHisExample example);

    int updateByExampleWithBLOBs(@Param("record") TtsCallbackHis record, @Param("example") TtsCallbackHisExample example);

    int updateByExample(@Param("record") TtsCallbackHis record, @Param("example") TtsCallbackHisExample example);

    int updateByPrimaryKeySelective(TtsCallbackHis record);

    int updateByPrimaryKeyWithBLOBs(TtsCallbackHis record);

    int updateByPrimaryKey(TtsCallbackHis record);
}