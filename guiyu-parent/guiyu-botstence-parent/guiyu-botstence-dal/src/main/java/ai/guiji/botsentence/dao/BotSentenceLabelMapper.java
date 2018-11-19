package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.BotSentenceLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceLabelMapper {
    int countByExample(BotSentenceLabelExample example);

    int deleteByExample(BotSentenceLabelExample example);

    int deleteByPrimaryKey(String labelId);

    int insert(BotSentenceLabel record);

    int insertSelective(BotSentenceLabel record);

    List<BotSentenceLabel> selectByExample(BotSentenceLabelExample example);

    BotSentenceLabel selectByPrimaryKey(String labelId);

    int updateByExampleSelective(@Param("record") BotSentenceLabel record, @Param("example") BotSentenceLabelExample example);

    int updateByExample(@Param("record") BotSentenceLabel record, @Param("example") BotSentenceLabelExample example);

    int updateByPrimaryKeySelective(BotSentenceLabel record);

    int updateByPrimaryKey(BotSentenceLabel record);
}