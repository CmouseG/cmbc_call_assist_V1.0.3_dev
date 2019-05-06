package com.guiji.user.dao;

import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysPrivilegeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysPrivilegeMapper {
    long countByExample(SysPrivilegeExample example);

    int deleteByExample(SysPrivilegeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysPrivilege record);

    int insertSelective(SysPrivilege record);

    List<SysPrivilege> selectByExample(SysPrivilegeExample example);

    SysPrivilege selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysPrivilege record, @Param("example") SysPrivilegeExample example);

    int updateByExample(@Param("record") SysPrivilege record, @Param("example") SysPrivilegeExample example);

    int updateByPrimaryKeySelective(SysPrivilege record);

    int updateByPrimaryKey(SysPrivilege record);
    
    List<Integer> queryOrgIds(@Param("authId") String authId);
}