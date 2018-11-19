package ai.guiji.botsentence.crm.dao;

import ai.guiji.botsentence.crm.dao.entity.TabTemp;
import ai.guiji.botsentence.crm.dao.entity.TabTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabTempMapper {
    int countByExample(TabTempExample example);

    int deleteByExample(TabTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TabTemp record);

    int insertSelective(TabTemp record);

    List<TabTemp> selectByExampleWithBLOBs(TabTempExample example);

    List<TabTemp> selectByExample(TabTempExample example);

    TabTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TabTemp record, @Param("example") TabTempExample example);

    int updateByExampleWithBLOBs(@Param("record") TabTemp record, @Param("example") TabTempExample example);

    int updateByExample(@Param("record") TabTemp record, @Param("example") TabTempExample example);

    int updateByPrimaryKeySelective(TabTemp record);

    int updateByPrimaryKeyWithBLOBs(TabTemp record);

    int updateByPrimaryKey(TabTemp record);
}