package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceTtsTaskMapper {
    int countByExample(BotSentenceTtsTaskExample example);

    int deleteByExample(BotSentenceTtsTaskExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BotSentenceTtsTask record);

    int insertSelective(BotSentenceTtsTask record);

    List<BotSentenceTtsTask> selectByExample(BotSentenceTtsTaskExample example);

    BotSentenceTtsTask selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BotSentenceTtsTask record, @Param("example") BotSentenceTtsTaskExample example);

    int updateByExample(@Param("record") BotSentenceTtsTask record, @Param("example") BotSentenceTtsTaskExample example);

    int updateByPrimaryKeySelective(BotSentenceTtsTask record);

    int updateByPrimaryKey(BotSentenceTtsTask record);
}