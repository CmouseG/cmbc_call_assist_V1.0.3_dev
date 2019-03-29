package com.guiji.voipgateway.dingxin.dao;

import com.guiji.voipgateway.dingxin.dao.entity.TblGwp;
import com.guiji.voipgateway.dingxin.dao.entity.TblGwpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblGwpMapper {
    int countByExample(TblGwpExample example);

    int deleteByExample(TblGwpExample example);

    int deleteByPrimaryKey(Integer uuid);

    int insert(TblGwp record);

    int insertSelective(TblGwp record);

    List<TblGwp> selectByExample(TblGwpExample example);

    TblGwp selectByPrimaryKey(Integer uuid);

    int updateByExampleSelective(@Param("record") TblGwp record, @Param("example") TblGwpExample example);

    int updateByExample(@Param("record") TblGwp record, @Param("example") TblGwpExample example);

    int updateByPrimaryKeySelective(TblGwp record);

    int updateByPrimaryKey(TblGwp record);
}