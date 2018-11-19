package ai.guiji.botsentence.crm.dao;

import ai.guiji.botsentence.crm.dao.entity.TabCustomer;
import ai.guiji.botsentence.crm.dao.entity.TabCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabCustomerMapper {
    int countByExample(TabCustomerExample example);

    int deleteByExample(TabCustomerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TabCustomer record);

    int insertSelective(TabCustomer record);

    List<TabCustomer> selectByExampleWithBLOBs(TabCustomerExample example);

    List<TabCustomer> selectByExample(TabCustomerExample example);

    TabCustomer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TabCustomer record, @Param("example") TabCustomerExample example);

    int updateByExampleWithBLOBs(@Param("record") TabCustomer record, @Param("example") TabCustomerExample example);

    int updateByExample(@Param("record") TabCustomer record, @Param("example") TabCustomerExample example);

    int updateByPrimaryKeySelective(TabCustomer record);

    int updateByPrimaryKeyWithBLOBs(TabCustomer record);

    int updateByPrimaryKey(TabCustomer record);
}