package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.IntentionTask;
import ai.guiji.botsentence.dao.entity.IntentionTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntentionTaskMapper {
    int countByExample(IntentionTaskExample example);

    int deleteByExample(IntentionTaskExample example);

    int deleteByPrimaryKey(String intentionId);

    int insert(IntentionTask record);

    int insertSelective(IntentionTask record);

    List<IntentionTask> selectByExample(IntentionTaskExample example);

    IntentionTask selectByPrimaryKey(String intentionId);

    int updateByExampleSelective(@Param("record") IntentionTask record, @Param("example") IntentionTaskExample example);

    int updateByExample(@Param("record") IntentionTask record, @Param("example") IntentionTaskExample example);

    int updateByPrimaryKeySelective(IntentionTask record);

    int updateByPrimaryKey(IntentionTask record);
}