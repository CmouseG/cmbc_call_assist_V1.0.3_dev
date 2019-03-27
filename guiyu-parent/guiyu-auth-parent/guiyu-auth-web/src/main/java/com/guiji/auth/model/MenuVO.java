package com.guiji.auth.model;

import com.guiji.user.dao.entity.SysMenu;

import lombok.Data;

/** 
* @ClassName: MenuVO 
* @Description: 菜单前端显示
* @auth weiyunbo
* @date 2019年3月20日 下午4:50:05 
* @version V1.0  
*/
@Data
public class MenuVO extends SysMenu{
	//创建人
	private String createName;
	//更新人
	private String updateName;
}
