package com.guiji.user.dao;

import com.guiji.user.dao.entity.SysProduct;
import com.guiji.user.dao.entity.SysProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysProductMapper {
    int countByExample(SysProductExample example);

    int deleteByExample(SysProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysProduct record);

    int insertSelective(SysProduct record);

    List<SysProduct> selectByExample(SysProductExample example);

    SysProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysProduct record, @Param("example") SysProductExample example);

    int updateByExample(@Param("record") SysProduct record, @Param("example") SysProductExample example);

    int updateByPrimaryKeySelective(SysProduct record);

    int updateByPrimaryKey(SysProduct record);
}