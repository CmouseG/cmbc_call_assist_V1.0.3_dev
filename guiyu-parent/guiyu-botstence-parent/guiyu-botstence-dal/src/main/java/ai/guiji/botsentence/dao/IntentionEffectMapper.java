package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.IntentionEffect;
import ai.guiji.botsentence.dao.entity.IntentionEffectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntentionEffectMapper {
    int countByExample(IntentionEffectExample example);

    int deleteByExample(IntentionEffectExample example);

    int deleteByPrimaryKey(String intentionId);

    int insert(IntentionEffect record);

    int insertSelective(IntentionEffect record);

    List<IntentionEffect> selectByExample(IntentionEffectExample example);

    IntentionEffect selectByPrimaryKey(String intentionId);

    int updateByExampleSelective(@Param("record") IntentionEffect record, @Param("example") IntentionEffectExample example);

    int updateByExample(@Param("record") IntentionEffect record, @Param("example") IntentionEffectExample example);

    int updateByPrimaryKeySelective(IntentionEffect record);

    int updateByPrimaryKey(IntentionEffect record);
}