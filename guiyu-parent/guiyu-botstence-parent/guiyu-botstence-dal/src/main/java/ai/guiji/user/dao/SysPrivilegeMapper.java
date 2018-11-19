package ai.guiji.user.dao;

import ai.guiji.user.dao.entity.SysPrivilege;
import ai.guiji.user.dao.entity.SysPrivilegeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysPrivilegeMapper {
    int countByExample(SysPrivilegeExample example);

    int deleteByExample(SysPrivilegeExample example);

    int deleteByPrimaryKey(@Param("roleId") String roleId, @Param("menuId") String menuId);

    int insert(SysPrivilege record);

    int insertSelective(SysPrivilege record);

    List<SysPrivilege> selectByExample(SysPrivilegeExample example);

    SysPrivilege selectByPrimaryKey(@Param("roleId") String roleId, @Param("menuId") String menuId);

    int updateByExampleSelective(@Param("record") SysPrivilege record, @Param("example") SysPrivilegeExample example);

    int updateByExample(@Param("record") SysPrivilege record, @Param("example") SysPrivilegeExample example);

    int updateByPrimaryKeySelective(SysPrivilege record);

    int updateByPrimaryKey(SysPrivilege record);
}