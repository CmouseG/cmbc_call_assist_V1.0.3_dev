package com.guiji.voipgateway.dingxin.dao;

import com.guiji.voipgateway.dingxin.dao.entity.TblGw;
import com.guiji.voipgateway.dingxin.dao.entity.TblGwExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblGwMapper {
    int countByExample(TblGwExample example);

    int deleteByExample(TblGwExample example);

    int deleteByPrimaryKey(Integer uuid);

    int insert(TblGw record);

    int insertSelective(TblGw record);

    List<TblGw> selectByExample(TblGwExample example);

    TblGw selectByPrimaryKey(Integer uuid);

    int updateByExampleSelective(@Param("record") TblGw record, @Param("example") TblGwExample example);

    int updateByExample(@Param("record") TblGw record, @Param("example") TblGwExample example);

    int updateByPrimaryKeySelective(TblGw record);

    int updateByPrimaryKey(TblGw record);
}