package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceTtsParam;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceTtsParamMapper {
    int countByExample(BotSentenceTtsParamExample example);

    int deleteByExample(BotSentenceTtsParamExample example);

    int deleteByPrimaryKey(String paramId);

    int insert(BotSentenceTtsParam record);

    int insertSelective(BotSentenceTtsParam record);

    List<BotSentenceTtsParam> selectByExample(BotSentenceTtsParamExample example);

    BotSentenceTtsParam selectByPrimaryKey(String paramId);

    int updateByExampleSelective(@Param("record") BotSentenceTtsParam record, @Param("example") BotSentenceTtsParamExample example);

    int updateByExample(@Param("record") BotSentenceTtsParam record, @Param("example") BotSentenceTtsParamExample example);

    int updateByPrimaryKeySelective(BotSentenceTtsParam record);

    int updateByPrimaryKey(BotSentenceTtsParam record);
}