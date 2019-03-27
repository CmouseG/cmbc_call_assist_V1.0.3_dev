package com.guiji.user.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.vo.RoleParamVo;

/** 
* @ClassName: SysRoleMapperExt 
* @Description: 用户权限扩展服务
* @auth weiyunbo
* @date 2019年3月11日 下午5:31:04 
* @version V1.0  
*/
public interface SysRoleMapperExt {
	public List<SysRole> getRoles();

    public void addMenus(@Param("roleId")Integer roleId,@Param("menuIds")String[] menuIds);
    
    public int countByParamVo(RoleParamVo param);
    
    public List<Object> selectByParamVo(RoleParamVo param);
    
    public boolean existRoleName(SysRole role);
}
