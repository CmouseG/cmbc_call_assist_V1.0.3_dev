package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceNodeEffect;
import ai.guiji.botsentence.dao.entity.BotSentenceNodeEffectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceNodeEffectMapper {
    int countByExample(BotSentenceNodeEffectExample example);

    int deleteByExample(BotSentenceNodeEffectExample example);

    int deleteByPrimaryKey(String nodeId);

    int insert(BotSentenceNodeEffect record);

    int insertSelective(BotSentenceNodeEffect record);

    List<BotSentenceNodeEffect> selectByExample(BotSentenceNodeEffectExample example);

    BotSentenceNodeEffect selectByPrimaryKey(String nodeId);

    int updateByExampleSelective(@Param("record") BotSentenceNodeEffect record, @Param("example") BotSentenceNodeEffectExample example);

    int updateByExample(@Param("record") BotSentenceNodeEffect record, @Param("example") BotSentenceNodeEffectExample example);

    int updateByPrimaryKeySelective(BotSentenceNodeEffect record);

    int updateByPrimaryKey(BotSentenceNodeEffect record);
}