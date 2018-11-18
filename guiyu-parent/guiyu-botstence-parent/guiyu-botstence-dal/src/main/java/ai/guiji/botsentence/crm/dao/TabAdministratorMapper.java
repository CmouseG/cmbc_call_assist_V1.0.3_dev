package ai.guiji.botsentence.crm.dao;

import ai.guiji.botsentence.crm.dao.entity.TabAdministrator;
import ai.guiji.botsentence.crm.dao.entity.TabAdministratorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabAdministratorMapper {
    int countByExample(TabAdministratorExample example);

    int deleteByExample(TabAdministratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TabAdministrator record);

    int insertSelective(TabAdministrator record);

    List<TabAdministrator> selectByExampleWithBLOBs(TabAdministratorExample example);

    List<TabAdministrator> selectByExample(TabAdministratorExample example);

    TabAdministrator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TabAdministrator record, @Param("example") TabAdministratorExample example);

    int updateByExampleWithBLOBs(@Param("record") TabAdministrator record, @Param("example") TabAdministratorExample example);

    int updateByExample(@Param("record") TabAdministrator record, @Param("example") TabAdministratorExample example);

    int updateByPrimaryKeySelective(TabAdministrator record);

    int updateByPrimaryKeyWithBLOBs(TabAdministrator record);

    int updateByPrimaryKey(TabAdministrator record);
}