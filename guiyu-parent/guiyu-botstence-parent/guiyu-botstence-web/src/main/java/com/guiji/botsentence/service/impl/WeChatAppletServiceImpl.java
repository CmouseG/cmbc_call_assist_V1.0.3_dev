package com.guiji.botsentence.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guiji.botsentence.dao.BotSentenceWechatUserInfoMapper;
import com.guiji.botsentence.dao.entity.BotSentenceWechatUserInfo;
import com.guiji.botsentence.dao.entity.BotSentenceWechatUserInfoExample;
import com.guiji.botsentence.service.IWeChatAppletService;
import com.guiji.component.client.config.WeiXinQRManager;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.client.util.QiuniuUploadUtil;
import com.guiji.common.exception.CommonException;

@Service
public class WeChatAppletServiceImpl implements IWeChatAppletService{

	private Logger logger = LoggerFactory.getLogger(WeChatAppletServiceImpl.class);
	
	@Autowired
	private WeiXinQRManager weiXin;
	
	@Autowired
	private QiuniuUploadUtil qiuniuUploadUtil;
	
	@Autowired
	private BotSentenceWechatUserInfoMapper botSentenceWechatUserInfoMapper;
	
	/*@Autowired
	private IAuthService authService;*/
	
	@Override
	public void saveAccount(String accountNo) {
		logger.info("新增小程序账号");
		BotSentenceWechatUserInfo userInfo = new BotSentenceWechatUserInfo();
		userInfo.setAccountNo(accountNo);
		userInfo.setPassword("c7c0a9e31c9115281ec6cb825bdb09e5");
		userInfo.setCrtTime(new Date(System.currentTimeMillis()));
		//userInfo.setCrtUser(UserUtil.getUserId());
		
		botSentenceWechatUserInfoMapper.insert(userInfo);
	}

	@Override
	public void updateAccount(String account, String password) {
		logger.info("更新小程序登录密码");
		BotSentenceWechatUserInfo userInfo = this.getWechatUserInfo(account);
		if(null != userInfo) {
			String encodePassword=DigestUtils.md5Hex(password + "botsentence123");
			userInfo.setPassword(encodePassword);
			userInfo.setLstUpdateTime(new Date(System.currentTimeMillis()));
			//userInfo.setLstUpdateUser(UserUtil.getUserId());
			
			botSentenceWechatUserInfoMapper.updateByPrimaryKey(userInfo);
		}else {
			throw new CommonException("当前小程序用户不存在");
		}
	}

	/*@Override
	public JwtAccount login(String accountNo, String password) {
		String encodePassword=DigestUtils.md5Hex(password + "botsentence123");
		BotSentenceWechatUserInfo userInfo = this.getWechatUserInfo(accountNo);
		if(null == userInfo) {
			return null;
		}
		if(encodePassword.equals(userInfo.getPassword())) {
			logger.info("登录成功");
			//获取token
			return authService.login(accountNo, "12345");
		}else {
			logger.info("登录失败");
		}
		return null;
	}*/

	@Override
	public String generateQrCode(String templateId) {
		String token = weiXin.getToken();
		InputStream inStream = weiXin.getminiqrQr(token, templateId);
		
		String keyName = DateUtil.yyyyMMddHHmmss2.format(new Date(System.currentTimeMillis())) + "_" + templateId+ ".jpg";
		String qrCodeUrl = qiuniuUploadUtil.upload(inStream, keyName);
		return qrCodeUrl;
	}

	public BotSentenceWechatUserInfo getWechatUserInfo(String accountNo) {
		if(StringUtils.isNotBlank(accountNo)) {
			BotSentenceWechatUserInfoExample example = new BotSentenceWechatUserInfoExample();
			example.createCriteria().andAccountNoEqualTo(accountNo);
			List<BotSentenceWechatUserInfo> list = botSentenceWechatUserInfoMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

	
	public static void main(String[] args) {
		String encodePassword=DigestUtils.md5Hex("123456botsentence123");
		System.out.println(encodePassword);
	}
}
