package com.guiji.voipgateway.dingxin.dao;

import com.guiji.voipgateway.dingxin.dao.entity.TblNe;
import com.guiji.voipgateway.dingxin.dao.entity.TblNeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblNeMapper {
    int countByExample(TblNeExample example);

    int deleteByExample(TblNeExample example);

    int deleteByPrimaryKey(Integer uuid);

    TblNe selectByProductSn(String productSn);

    int insert(TblNe record);

    int insertSelective(TblNe record);

    List<TblNe> selectByExampleWithBLOBs(TblNeExample example);

    List<TblNe> selectByExample(TblNeExample example);

    TblNe selectByPrimaryKey(Integer uuid);

    int updateByExampleSelective(@Param("record") TblNe record, @Param("example") TblNeExample example);

    int updateByExampleWithBLOBs(@Param("record") TblNe record, @Param("example") TblNeExample example);

    int updateByExample(@Param("record") TblNe record, @Param("example") TblNeExample example);

    int updateByPrimaryKeySelective(TblNe record);

    int updateByPrimaryKeyWithBLOBs(TblNe record);

    int updateByPrimaryKey(TblNe record);
}