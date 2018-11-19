package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceTtsContent;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceTtsContentMapper {
    int countByExample(BotSentenceTtsContentExample example);

    int deleteByExample(BotSentenceTtsContentExample example);

    int deleteByPrimaryKey(String contentId);

    int insert(BotSentenceTtsContent record);

    int insertSelective(BotSentenceTtsContent record);

    List<BotSentenceTtsContent> selectByExample(BotSentenceTtsContentExample example);

    BotSentenceTtsContent selectByPrimaryKey(String contentId);

    int updateByExampleSelective(@Param("record") BotSentenceTtsContent record, @Param("example") BotSentenceTtsContentExample example);

    int updateByExample(@Param("record") BotSentenceTtsContent record, @Param("example") BotSentenceTtsContentExample example);

    int updateByPrimaryKeySelective(BotSentenceTtsContent record);

    int updateByPrimaryKey(BotSentenceTtsContent record);
}