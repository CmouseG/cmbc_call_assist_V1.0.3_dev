package ai.guiji.botsentence.vo;

import java.util.List;

//import ai.guiji.cloud.auth.domain.JwtAccount;
import ai.guiji.user.dao.entity.SysMenu;

public class MenuUserInfo {

	private List<SysMenu> menus;
	
//	private JwtAccount jwtAccount;

	public List<SysMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenu> menus) {
		this.menus = menus;
	}

//	public JwtAccount getJwtAccount() {
//		return jwtAccount;
//	}
//
//	public void setJwtAccount(JwtAccount jwtAccount) {
//		this.jwtAccount = jwtAccount;
//	}
//
	
}
