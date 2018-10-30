package com.guiji.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guiji.user.dao.entity.Menu;

@Mapper
public interface MenuMapper {
	
	public void insert(Menu menu);
	
	public void delete(String id);
	
	public void update(Menu menu);
	
	public Menu getMenuById(String id);
	
	public List<Menu> getMenuByUserId(String userId);
	
}
