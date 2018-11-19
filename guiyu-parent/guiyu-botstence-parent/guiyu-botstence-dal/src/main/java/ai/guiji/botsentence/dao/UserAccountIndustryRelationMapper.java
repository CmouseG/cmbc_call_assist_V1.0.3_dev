package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelation;
import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAccountIndustryRelationMapper {
    int countByExample(UserAccountIndustryRelationExample example);

    int deleteByExample(UserAccountIndustryRelationExample example);

    int deleteByPrimaryKey(String relationId);

    int insert(UserAccountIndustryRelation record);

    int insertSelective(UserAccountIndustryRelation record);

    List<UserAccountIndustryRelation> selectByExample(UserAccountIndustryRelationExample example);

    UserAccountIndustryRelation selectByPrimaryKey(String relationId);

    int updateByExampleSelective(@Param("record") UserAccountIndustryRelation record, @Param("example") UserAccountIndustryRelationExample example);

    int updateByExample(@Param("record") UserAccountIndustryRelation record, @Param("example") UserAccountIndustryRelationExample example);

    int updateByPrimaryKeySelective(UserAccountIndustryRelation record);

    int updateByPrimaryKey(UserAccountIndustryRelation record);
}