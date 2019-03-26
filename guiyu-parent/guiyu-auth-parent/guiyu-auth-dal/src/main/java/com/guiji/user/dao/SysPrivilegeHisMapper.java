package com.guiji.user.dao;

import com.guiji.user.dao.entity.SysPrivilegeHis;
import com.guiji.user.dao.entity.SysPrivilegeHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysPrivilegeHisMapper {
    int countByExample(SysPrivilegeHisExample example);

    int deleteByExample(SysPrivilegeHisExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysPrivilegeHis record);

    int insertSelective(SysPrivilegeHis record);

    List<SysPrivilegeHis> selectByExample(SysPrivilegeHisExample example);

    SysPrivilegeHis selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysPrivilegeHis record, @Param("example") SysPrivilegeHisExample example);

    int updateByExample(@Param("record") SysPrivilegeHis record, @Param("example") SysPrivilegeHisExample example);

    int updateByPrimaryKeySelective(SysPrivilegeHis record);

    int updateByPrimaryKey(SysPrivilegeHis record);
}