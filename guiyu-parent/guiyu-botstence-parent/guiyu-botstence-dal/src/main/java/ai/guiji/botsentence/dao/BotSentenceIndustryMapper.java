package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceIndustry;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceIndustryMapper {
    int countByExample(BotSentenceIndustryExample example);

    int deleteByExample(BotSentenceIndustryExample example);

    int deleteByPrimaryKey(Integer industryId);

    int insert(BotSentenceIndustry record);

    int insertSelective(BotSentenceIndustry record);

    List<BotSentenceIndustry> selectByExample(BotSentenceIndustryExample example);

    BotSentenceIndustry selectByPrimaryKey(Integer industryId);

    int updateByExampleSelective(@Param("record") BotSentenceIndustry record, @Param("example") BotSentenceIndustryExample example);

    int updateByExample(@Param("record") BotSentenceIndustry record, @Param("example") BotSentenceIndustryExample example);

    int updateByPrimaryKeySelective(BotSentenceIndustry record);

    int updateByPrimaryKey(BotSentenceIndustry record);
}