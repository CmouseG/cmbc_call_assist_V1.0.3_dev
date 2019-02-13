package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.VoipGwInfo;
import com.guiji.clm.dao.entity.VoipGwInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoipGwInfoMapper {
    int countByExample(VoipGwInfoExample example);

    int deleteByExample(VoipGwInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoipGwInfo record);

    int insertSelective(VoipGwInfo record);

    List<VoipGwInfo> selectByExample(VoipGwInfoExample example);

    VoipGwInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoipGwInfo record, @Param("example") VoipGwInfoExample example);

    int updateByExample(@Param("record") VoipGwInfo record, @Param("example") VoipGwInfoExample example);

    int updateByPrimaryKeySelective(VoipGwInfo record);

    int updateByPrimaryKey(VoipGwInfo record);
}