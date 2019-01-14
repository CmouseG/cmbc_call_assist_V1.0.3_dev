package com.guiji.da.dao;

import com.guiji.da.dao.entity.CustPreInfo;
import com.guiji.da.dao.entity.CustPreInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustPreInfoMapper {
    int countByExample(CustPreInfoExample example);

    int deleteByExample(CustPreInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustPreInfo record);

    int insertSelective(CustPreInfo record);

    List<CustPreInfo> selectByExample(CustPreInfoExample example);

    CustPreInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustPreInfo record, @Param("example") CustPreInfoExample example);

    int updateByExample(@Param("record") CustPreInfo record, @Param("example") CustPreInfoExample example);

    int updateByPrimaryKeySelective(CustPreInfo record);

    int updateByPrimaryKey(CustPreInfo record);
}