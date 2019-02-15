package com.guiji.ai.dao;

import com.guiji.ai.dao.entity.AiModelFactory;
import com.guiji.ai.dao.entity.AiModelFactoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AiModelFactoryMapper {
    long countByExample(AiModelFactoryExample example);

    int deleteByExample(AiModelFactoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AiModelFactory record);

    int insertSelective(AiModelFactory record);

    List<AiModelFactory> selectByExample(AiModelFactoryExample example);

    AiModelFactory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AiModelFactory record, @Param("example") AiModelFactoryExample example);

    int updateByExample(@Param("record") AiModelFactory record, @Param("example") AiModelFactoryExample example);

    int updateByPrimaryKeySelective(AiModelFactory record);

    int updateByPrimaryKey(AiModelFactory record);
}