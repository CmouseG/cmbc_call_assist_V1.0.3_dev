package com.guiji.ai.dao;

import com.guiji.ai.dao.entity.TtsResult;
import com.guiji.ai.dao.entity.TtsResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TtsResultMapper {
    int countByExample(TtsResultExample example);

    int deleteByExample(TtsResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TtsResult record);

    int insertSelective(TtsResult record);

    List<TtsResult> selectByExample(TtsResultExample example);

    TtsResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TtsResult record, @Param("example") TtsResultExample example);

    int updateByExample(@Param("record") TtsResult record, @Param("example") TtsResultExample example);

    int updateByPrimaryKeySelective(TtsResult record);

    int updateByPrimaryKey(TtsResult record);
}