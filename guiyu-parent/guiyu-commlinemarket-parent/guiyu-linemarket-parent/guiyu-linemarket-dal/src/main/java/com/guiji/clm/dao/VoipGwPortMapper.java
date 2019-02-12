package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.dao.entity.VoipGwPortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoipGwPortMapper {
    int countByExample(VoipGwPortExample example);

    int deleteByExample(VoipGwPortExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoipGwPort record);

    int insertSelective(VoipGwPort record);

    List<VoipGwPort> selectByExample(VoipGwPortExample example);

    VoipGwPort selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoipGwPort record, @Param("example") VoipGwPortExample example);

    int updateByExample(@Param("record") VoipGwPort record, @Param("example") VoipGwPortExample example);

    int updateByPrimaryKeySelective(VoipGwPort record);

    int updateByPrimaryKey(VoipGwPort record);
}