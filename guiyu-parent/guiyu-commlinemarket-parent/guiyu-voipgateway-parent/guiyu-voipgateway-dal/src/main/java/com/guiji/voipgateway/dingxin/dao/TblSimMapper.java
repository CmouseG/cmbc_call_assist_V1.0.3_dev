package com.guiji.voipgateway.dingxin.dao;

import com.guiji.voipgateway.dingxin.dao.entity.TblSim;
import com.guiji.voipgateway.dingxin.dao.entity.TblSimExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblSimMapper {
    int countByExample(TblSimExample example);

    int deleteByExample(TblSimExample example);

    int deleteByPrimaryKey(Integer uuid);

    int insert(TblSim record);

    int insertSelective(TblSim record);

    List<TblSim> selectByExample(TblSimExample example);

    TblSim selectByPrimaryKey(Integer uuid);

    int updateByExampleSelective(@Param("record") TblSim record, @Param("example") TblSimExample example);

    int updateByExample(@Param("record") TblSim record, @Param("example") TblSimExample example);

    int updateByPrimaryKeySelective(TblSim record);

    int updateByPrimaryKey(TblSim record);
}