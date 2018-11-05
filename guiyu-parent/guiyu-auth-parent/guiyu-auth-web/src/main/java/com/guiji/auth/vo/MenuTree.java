package com.guiji.auth.vo;

import java.util.ArrayList;
import java.util.List;

import com.guiji.user.dao.entity.SysMenu;

public class MenuTree {
	
	private SysMenu parent;
	private List<MenuTree> child=new ArrayList<>();
	
	public SysMenu getParent() {
		return parent;
	}
	public void setParent(SysMenu parent) {
		this.parent = parent;
	}
	public List<MenuTree> getChild() {
		return child;
	}
	public void setChild(List<MenuTree> child) {
		this.child = child;
	}

}
