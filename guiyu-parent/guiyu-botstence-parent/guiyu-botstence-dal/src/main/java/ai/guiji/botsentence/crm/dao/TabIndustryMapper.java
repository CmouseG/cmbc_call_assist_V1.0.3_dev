package ai.guiji.botsentence.crm.dao;

import ai.guiji.botsentence.crm.dao.entity.TabIndustry;
import ai.guiji.botsentence.crm.dao.entity.TabIndustryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabIndustryMapper {
    int countByExample(TabIndustryExample example);

    int deleteByExample(TabIndustryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TabIndustry record);

    int insertSelective(TabIndustry record);

    List<TabIndustry> selectByExample(TabIndustryExample example);

    TabIndustry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TabIndustry record, @Param("example") TabIndustryExample example);

    int updateByExample(@Param("record") TabIndustry record, @Param("example") TabIndustryExample example);

    int updateByPrimaryKeySelective(TabIndustry record);

    int updateByPrimaryKey(TabIndustry record);
}