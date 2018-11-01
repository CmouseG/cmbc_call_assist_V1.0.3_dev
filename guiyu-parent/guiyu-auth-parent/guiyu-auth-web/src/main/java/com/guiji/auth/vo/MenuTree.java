package com.guiji.auth.vo;

import java.util.ArrayList;
import java.util.List;

import com.guiji.user.dao.entity.Menu;

public class MenuTree {
	
	private Menu parent;
	private List<MenuTree> child=new ArrayList<>();
	
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public List<MenuTree> getChild() {
		return child;
	}
	public void setChild(List<MenuTree> child) {
		this.child = child;
	}

}
