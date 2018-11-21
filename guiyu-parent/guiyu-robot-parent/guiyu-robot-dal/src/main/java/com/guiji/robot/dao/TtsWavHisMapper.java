package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.dao.entity.TtsWavHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TtsWavHisMapper {
    int countByExample(TtsWavHisExample example);

    int deleteByExample(TtsWavHisExample example);

    int deleteByPrimaryKey(String id);

    int insert(TtsWavHis record);

    int insertSelective(TtsWavHis record);

    List<TtsWavHis> selectByExampleWithBLOBs(TtsWavHisExample example);

    List<TtsWavHis> selectByExample(TtsWavHisExample example);

    TtsWavHis selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TtsWavHis record, @Param("example") TtsWavHisExample example);

    int updateByExampleWithBLOBs(@Param("record") TtsWavHis record, @Param("example") TtsWavHisExample example);

    int updateByExample(@Param("record") TtsWavHis record, @Param("example") TtsWavHisExample example);

    int updateByPrimaryKeySelective(TtsWavHis record);

    int updateByPrimaryKeyWithBLOBs(TtsWavHis record);

    int updateByPrimaryKey(TtsWavHis record);
}