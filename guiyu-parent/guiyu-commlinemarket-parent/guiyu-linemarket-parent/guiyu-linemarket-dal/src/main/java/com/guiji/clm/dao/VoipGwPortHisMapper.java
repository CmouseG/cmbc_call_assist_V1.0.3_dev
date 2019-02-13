package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.VoipGwPortHis;
import com.guiji.clm.dao.entity.VoipGwPortHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoipGwPortHisMapper {
    int countByExample(VoipGwPortHisExample example);

    int deleteByExample(VoipGwPortHisExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoipGwPortHis record);

    int insertSelective(VoipGwPortHis record);

    List<VoipGwPortHis> selectByExample(VoipGwPortHisExample example);

    VoipGwPortHis selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoipGwPortHis record, @Param("example") VoipGwPortHisExample example);

    int updateByExample(@Param("record") VoipGwPortHis record, @Param("example") VoipGwPortHisExample example);

    int updateByPrimaryKeySelective(VoipGwPortHis record);

    int updateByPrimaryKey(VoipGwPortHis record);
}