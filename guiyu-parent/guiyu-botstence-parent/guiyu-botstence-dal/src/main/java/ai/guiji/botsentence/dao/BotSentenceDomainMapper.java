package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceDomainMapper {
    int countByExample(BotSentenceDomainExample example);

    int deleteByExample(BotSentenceDomainExample example);

    int deleteByPrimaryKey(String domainId);

    int insert(BotSentenceDomain record);

    int insertSelective(BotSentenceDomain record);

    List<BotSentenceDomain> selectByExample(BotSentenceDomainExample example);

    BotSentenceDomain selectByPrimaryKey(String domainId);

    int updateByExampleSelective(@Param("record") BotSentenceDomain record, @Param("example") BotSentenceDomainExample example);

    int updateByExample(@Param("record") BotSentenceDomain record, @Param("example") BotSentenceDomainExample example);

    int updateByPrimaryKeySelective(BotSentenceDomain record);

    int updateByPrimaryKey(BotSentenceDomain record);
}