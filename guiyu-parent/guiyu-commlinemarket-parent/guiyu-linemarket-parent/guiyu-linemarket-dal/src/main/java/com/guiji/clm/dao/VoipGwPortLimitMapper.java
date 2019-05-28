package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.VoipGwPortLimit;
import com.guiji.clm.dao.entity.VoipGwPortLimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoipGwPortLimitMapper {
    int countByExample(VoipGwPortLimitExample example);

    int deleteByExample(VoipGwPortLimitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoipGwPortLimit record);

    int insertSelective(VoipGwPortLimit record);

    List<VoipGwPortLimit> selectByExample(VoipGwPortLimitExample example);

    VoipGwPortLimit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoipGwPortLimit record, @Param("example") VoipGwPortLimitExample example);

    int updateByExample(@Param("record") VoipGwPortLimit record, @Param("example") VoipGwPortLimitExample example);

    int updateByPrimaryKeySelective(VoipGwPortLimit record);

    int updateByPrimaryKey(VoipGwPortLimit record);
}