package com.guiji.ai.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.guiji.ai.dao.entity.TtsModel;
import com.guiji.ai.dao.entity.TtsModelExample;

public interface TtsModelMapper {
    int countByExample(TtsModelExample example);

    int deleteByExample(TtsModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TtsModel record);

    int insertSelective(TtsModel record);

    List<TtsModel> selectByExample(TtsModelExample example);

    TtsModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TtsModel record, @Param("example") TtsModelExample example);

    int updateByExample(@Param("record") TtsModel record, @Param("example") TtsModelExample example);

    int updateByPrimaryKeySelective(TtsModel record);

    int updateByPrimaryKey(TtsModel record);

}