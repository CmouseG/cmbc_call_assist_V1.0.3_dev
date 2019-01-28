package com.guiji.botsentence.service;

/**
 * 微信小程序-->硅基快配音相关服务类
 * @author 张朋
 * @date 2018-12-25
 *
 */
public interface IWeChatAppletService {

	public void saveAccount(String account);
	
	public void updateAccount(String account, String password);
	
	//public JwtAccount login(String account, String password);
	
	public String generateQrCode(String templateId);
	
	
}
