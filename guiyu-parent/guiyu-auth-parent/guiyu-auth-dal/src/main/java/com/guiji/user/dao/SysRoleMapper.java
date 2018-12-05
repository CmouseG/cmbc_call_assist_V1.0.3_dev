package com.guiji.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysRoleExample;
import com.guiji.user.vo.RoleParamVo;

public interface SysRoleMapper {
    int countByExample(SysRoleExample example);

    int deleteByExample(SysRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    List<SysRole> selectByExample(SysRoleExample example);

    SysRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    int updateByExample(@Param("record") SysRole record, @Param("example") SysRoleExample example);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    public List<SysRole> getRoles();

    public void addMenus(@Param("roleId")Long roleId,@Param("menuIds")String[] menuIds);
    
    public int countByParamVo(RoleParamVo param);
    
    public List<Object> selectByParamVo(RoleParamVo param);
}