package com.guiji.sms.dao;

import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.dao.entity.SmsTunnelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsTunnelMapper {
    long countByExample(SmsTunnelExample example);

    int deleteByExample(SmsTunnelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsTunnel record);

    int insertSelective(SmsTunnel record);

    List<SmsTunnel> selectByExampleWithBLOBs(SmsTunnelExample example);

    List<SmsTunnel> selectByExample(SmsTunnelExample example);

    SmsTunnel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsTunnel record, @Param("example") SmsTunnelExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsTunnel record, @Param("example") SmsTunnelExample example);

    int updateByExample(@Param("record") SmsTunnel record, @Param("example") SmsTunnelExample example);

    int updateByPrimaryKeySelective(SmsTunnel record);

    int updateByPrimaryKeyWithBLOBs(SmsTunnel record);

    int updateByPrimaryKey(SmsTunnel record);
}