package com.guiji.user.dao;

import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.user.dao.entity.SysUserExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserExtMapper {
    int countByExample(SysUserExtExample example);

    int deleteByExample(SysUserExtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserExt record);

    int insertSelective(SysUserExt record);

    List<SysUserExt> selectByExample(SysUserExtExample example);

    SysUserExt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUserExt record, @Param("example") SysUserExtExample example);

    int updateByExample(@Param("record") SysUserExt record, @Param("example") SysUserExtExample example);

    int updateByPrimaryKeySelective(SysUserExt record);

    int updateByUserId(SysUserExt record);

    int updateByPrimaryKey(SysUserExt record);
}