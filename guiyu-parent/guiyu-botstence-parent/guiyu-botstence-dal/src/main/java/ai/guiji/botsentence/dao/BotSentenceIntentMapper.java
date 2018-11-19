package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceIntentMapper {
    int countByExample(BotSentenceIntentExample example);

    int deleteByExample(BotSentenceIntentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BotSentenceIntent record);

    int insertSelective(BotSentenceIntent record);

    List<BotSentenceIntent> selectByExampleWithBLOBs(BotSentenceIntentExample example);

    List<BotSentenceIntent> selectByExample(BotSentenceIntentExample example);

    BotSentenceIntent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BotSentenceIntent record, @Param("example") BotSentenceIntentExample example);

    int updateByExampleWithBLOBs(@Param("record") BotSentenceIntent record, @Param("example") BotSentenceIntentExample example);

    int updateByExample(@Param("record") BotSentenceIntent record, @Param("example") BotSentenceIntentExample example);

    int updateByPrimaryKeySelective(BotSentenceIntent record);

    int updateByPrimaryKeyWithBLOBs(BotSentenceIntent record);

    int updateByPrimaryKey(BotSentenceIntent record);
}