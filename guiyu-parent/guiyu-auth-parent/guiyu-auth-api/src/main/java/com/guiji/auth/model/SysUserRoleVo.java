package com.guiji.auth.model;

import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ty on 2019/1/29.
 */
public class SysUserRoleVo implements Serializable {
    private SysUser sysUser;

    private List<SysRole> sysRoleList;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public List<SysRole> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRole> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }
}
