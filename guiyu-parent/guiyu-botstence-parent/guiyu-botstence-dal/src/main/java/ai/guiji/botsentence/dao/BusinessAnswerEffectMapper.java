package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BusinessAnswerEffect;
import ai.guiji.botsentence.dao.entity.BusinessAnswerEffectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessAnswerEffectMapper {
    int countByExample(BusinessAnswerEffectExample example);

    int deleteByExample(BusinessAnswerEffectExample example);

    int deleteByPrimaryKey(String answerId);

    int insert(BusinessAnswerEffect record);

    int insertSelective(BusinessAnswerEffect record);

    List<BusinessAnswerEffect> selectByExample(BusinessAnswerEffectExample example);

    BusinessAnswerEffect selectByPrimaryKey(String answerId);

    int updateByExampleSelective(@Param("record") BusinessAnswerEffect record, @Param("example") BusinessAnswerEffectExample example);

    int updateByExample(@Param("record") BusinessAnswerEffect record, @Param("example") BusinessAnswerEffectExample example);

    int updateByPrimaryKeySelective(BusinessAnswerEffect record);

    int updateByPrimaryKey(BusinessAnswerEffect record);
}