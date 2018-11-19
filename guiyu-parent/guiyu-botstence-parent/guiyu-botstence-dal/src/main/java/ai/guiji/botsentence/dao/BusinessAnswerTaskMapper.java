package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BusinessAnswerTask;
import ai.guiji.botsentence.dao.entity.BusinessAnswerTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessAnswerTaskMapper {
    int countByExample(BusinessAnswerTaskExample example);

    int deleteByExample(BusinessAnswerTaskExample example);

    int deleteByPrimaryKey(String answerId);

    int insert(BusinessAnswerTask record);

    int insertSelective(BusinessAnswerTask record);

    List<BusinessAnswerTask> selectByExample(BusinessAnswerTaskExample example);

    BusinessAnswerTask selectByPrimaryKey(String answerId);

    int updateByExampleSelective(@Param("record") BusinessAnswerTask record, @Param("example") BusinessAnswerTaskExample example);

    int updateByExample(@Param("record") BusinessAnswerTask record, @Param("example") BusinessAnswerTaskExample example);

    int updateByPrimaryKeySelective(BusinessAnswerTask record);

    int updateByPrimaryKey(BusinessAnswerTask record);
}