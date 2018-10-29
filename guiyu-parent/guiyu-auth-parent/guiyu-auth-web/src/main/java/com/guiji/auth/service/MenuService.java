package com.guiji.auth.service;

import org.springframework.stereotype.Service;

import com.guiji.user.dao.MenuMapper;
import com.guiji.user.dao.entity.Menu;

@Service
public class MenuService {

	private MenuMapper mapper;

	public void insert(Menu menu){
		mapper.insert(menu);
	}

	public void delete(String id){
		mapper.delete(id);
	}

	public void update(Menu menu){
		mapper.update(menu);
	}

	public Menu getMenuById(String id){
		return mapper.getMenuById(id);
	}

}
