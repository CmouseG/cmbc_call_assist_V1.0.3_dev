package ai.guiji.user.dao;

import java.util.List;

import ai.guiji.user.dao.entity.SysMenu;

public interface UserMenuMapper {
	
    List<String> selectByUserId(String id);
    
    List<SysMenu> selectMenusByUser(String name);
}
