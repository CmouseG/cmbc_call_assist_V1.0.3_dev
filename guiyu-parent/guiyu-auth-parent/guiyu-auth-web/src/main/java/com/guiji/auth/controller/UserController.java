package com.guiji.auth.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.auth.model.UserAuth;
import com.guiji.auth.model.UserIdVo;
import com.guiji.auth.model.UserRobotNumVO;
import com.guiji.auth.service.OrganizationService;
import com.guiji.auth.service.UserService;
import com.guiji.auth.util.AuthUtil;
import com.guiji.auth.util.DataLocalCacheUtil;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.user.vo.SysUserVo;
import com.guiji.user.vo.UserParamVo;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.vo.QRCodeReqVO;
import com.guiji.wechat.vo.QRCodeRpsVO;

/**
 * Created by ty on 2018/10/22.
 */
@RestController
public class UserController implements IAuth {
	static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private WeChatApi weChatApi;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;

	private static final String REDIS_USER_BY_ID = "REDIS_USER_BY_USERID_";

	@Jurisdiction("system_usr_add")
	@RequestMapping("/user/regist")
	public SysUser insert(SysUserVo param, @RequestHeader Long userId) throws Exception {
		SysUser user = new SysUser();
		user.setId(param.getId());
		user.setUsername(param.getUsername());
		user.setPassword(param.getPassword());
		user.setStatus(param.getStatus());
		user.setPushType(param.getPushType());
		user.setIntenLabel(param.getIntenLabel());
		user.setOrgCode(param.getOrgCode());
		user.setDelFlag(0);
		if (service.existUserName(user)) {
			throw new GuiyuException("用户名已存在！");
		}
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		user.setCreateId(userId);
		user.setUpdateId(userId);
		if (StringUtils.isEmpty(param.getStartTime())) {
			user.setStartTime(new Date());
		} else {
			user.setStartTime(parseStringDate(param.getStartTime()));
		}

		if (StringUtils.isEmpty(param.getVaildTime())) {
			user.setVaildTime(null);
		} else {
			user.setVaildTime(parseStringDate(param.getVaildTime()));
		}
		user.setIsDesensitization(param.getIsDesensitization());
		service.insert(user, param.getRoleId());
		return user;
	}

	private Date parseStringDate(String date) {
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.parse(date);
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
		return Date.from(zdt.toInstant());
	}

	@Jurisdiction("system_usr_edit")
	@RequestMapping("/user/update")
	public void update(SysUserVo param, @RequestHeader Long userId) throws CheckConditionException {
		SysUser user = new SysUser();
		user.setId(param.getId());
		user.setUsername(param.getUsername());
		user.setPassword(param.getPassword());
		user.setStatus(param.getStatus());
		user.setPushType(param.getPushType());
		user.setIntenLabel(param.getIntenLabel());
		user.setOrgCode(param.getOrgCode());
		user.setIsDesensitization(param.getIsDesensitization());
		if (!StringUtils.isEmpty(param.getStartTime())) {
			user.setStartTime(parseStringDate(param.getStartTime()));
		}
		if (!StringUtils.isEmpty(param.getVaildTime())) {
			user.setVaildTime(parseStringDate(param.getVaildTime()));
		}

		if (service.existUserName(user)) {
			throw new CheckConditionException("00010005");
		}
		user.setUpdateId(userId);
		user.setUpdateTime(new Date());
		if (!StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(AuthUtil.encrypt(user.getPassword()));
		}
		service.update(user, param.getRoleId());
	}

	@RequestMapping("/user/delete")
	public void delete(Long id) {
		service.delete(id);
	}

	@RequestMapping("/user/getUserByPage")
	public Page<Object> getUserByPage(UserParamVo param, @RequestHeader Long userId, 
			@RequestHeader Integer authLevel, @RequestHeader String orgCode)
	{
		param.setUserId(userId);
		param.setAuthLevel(authLevel);
		param.setOrgCode(orgCode);
		return service.getUserByPage(param);
	}

	@RequestMapping("/user/getUserById")
	public ReturnData<SysUser> getUserById(Long userId) {
		SysUser sysUser = service.getUserById(userId);
		return Result.ok(sysUser);
	}

	@Override
	public ReturnData<SysUser> getUserByAccessKey(String accessKey) {
		SysUser sysUser = service.getUserByAccessKey(accessKey);
		return Result.ok(sysUser);
	}

	@RequestMapping("/user/getUserByName")
	public ReturnData<List<SysUser>> getUserByName(String username) {
		return Result.ok(service.getUserByName(username));
	}

	@Jurisdiction("personCenter_myselfInfo_basicInfo_revisePwd")
	@RequestMapping("/user/changePassword")
	public void changePassword(String newPass, String oldPass, @RequestHeader Long userId) throws Exception {
		service.changePassword(newPass, oldPass, userId);
	}

	@GetMapping("/user/apiUpdatePassword")
	public Result.ReturnData apiUpdatePassword(@RequestParam("newPass") String newPass,@RequestParam("oldPass")  String oldPass,
										@RequestParam("userId")  Long userId) throws Exception{

		service.changePassword(newPass, oldPass, userId);
		return Result.ok();
	}

	@Jurisdiction("personCenter_myselfInfo_getData_save")
	@RequestMapping("/user/updateUserData")
	public void updateUserData(SysUser user, @RequestHeader Long userId) {
		user.setId(userId);
		user.setUpdateId(userId);
		user.setUpdateTime(new Date());
		service.updateUserData(user);
	}

	@RequestMapping("/user/getUserInfo")
	public Map<String, Object> getUserInfo(@RequestHeader Long userId) {
		return service.getUserInfo(userId);
	}

	@RequestMapping("/user/changeAccessKey")
	public ReturnData<String> changeAccessKey(@RequestHeader Long userId) {
		return Result.ok(service.changeAccessKey(userId));
	}

	@RequestMapping("/user/changeSecretKey")
	public ReturnData<String> changeSecretKey(@RequestHeader Long userId) {
		return Result.ok(service.changeSecretKey(userId));
	}

	@RequestMapping("/user/getRoleByUserId")
	public ReturnData<List<SysRole>> getRoleByUserId(Long userId) {
		return Result.ok(service.getRoleByUserId(userId));
	}

	@RequestMapping("/user/getOrgByUserId")
	public ReturnData<SysOrganization> getOrgByUserId(Long userId) {
		return Result.ok(service.getOrgByUserId(userId));
	}

	@RequestMapping("/user/selectLikeUserName")
	public List<Object> selectLikeUserName(UserParamVo param, @RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode) {
		return service.selectLikeUserName(param, userId, authLevel, orgCode);
	}

	@RequestMapping("/user/getAllCompanyUser")
	@Override
	public ReturnData<List<SysUser>> getAllCompanyUser() {
		return new ReturnData<List<SysUser>>(service.getAllCompanyUser());
	}

	// ----------------------add by xujin

	@RequestMapping("/user/getUserById4Keys")
	public ReturnData<SysUser> getUserById4Keys(Long userId) {
		SysUser sysUser = service.getUserById(userId);
		if (sysUser != null) {
			if ((sysUser.getAccessKey() == null || sysUser.getSecretKey() == null) || (sysUser.getAccessKey() ==""||sysUser.getSecretKey() == null)) {
				String changeAccessKey = service.changeAccessKey(userId);
				String changeSecretKey = service.changeSecretKey(userId);
				sysUser.setAccessKey(changeAccessKey);
				sysUser.setSecretKey(changeSecretKey);
				redisUtil.set(REDIS_USER_BY_ID + userId, sysUser);
			}
		}
		return Result.ok(sysUser);
	}

	@RequestMapping("/user/insertCustmomService")
	public ReturnData<SysUser> insertCustmomService(String username,String password,Long userId){
		SysUser user=new SysUser();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return Result.error("00010016");
		}
		user.setUsername(username);
		user.setPassword(password);
		user.setStatus(1);//正常状态
		user.setPushType(1);//平台
		SysOrganization org = service.getOrgByUserId(userId);
		if (org != null && !org.getCode().isEmpty()) {
			String orgCode = organizationService.getSubOrgCode(org.getCode());
			user.setOrgCode(orgCode);
		}
		user.setDelFlag(0);
		if(service.existUserName(user)){
			return Result.error("00010005");
		}
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		user.setCreateId(userId);
		user.setUpdateId(userId);
		service.insert(user,5L);//客服角色
		return Result.ok(user);
	}


	@RequestMapping("/user/checkUsernameIsExist")
	public ReturnData<Boolean> checkUsernameIsExist(String username){
		SysUser user=new SysUser();
		user.setUsername(username);
		if(service.existUserName(user)){
			return Result.ok(true);
		} else {
			return Result.ok(false);
		}
	}

	@RequestMapping("/user/getAllUserByOrgCode")
	public ReturnData<List<SysUser>> getAllUserByOrgCode(@RequestParam("orgCode") String orgCode) {
		return new ReturnData<List<SysUser>>(service.getAllUserByOrgCode(orgCode));
	}
	
	/**
	 * 通过userId，查询其组织或者下级组织的所有用户列表
	 */
	@RequestMapping("/user/getAllUserByUserId")
	public ReturnData<List<SysUser>> getAllUserByUserId(@RequestParam("userId") Long userId)
	{
		return Result.ok(service.getAllUserByUserId(userId));
	}

	@RequestMapping("/user/getAllUserRoleByOrgCode")
	public ReturnData<List<SysUserRoleVo>> getAllUserRoleByOrgCode(@RequestParam("orgCode") String orgCode) {
		return new ReturnData<List<SysUserRoleVo>>(service.getAllUserRoleByOrgCode(orgCode));
	}

	@Jurisdiction("personCenter_myselfInfo_basicInfo_reviseEmail,personCenter_myselfInfo_basicInfo_revisePhone")
	@RequestMapping("/user/updateUserExt")
	public void updateUserExt(SysUserExt sysUserExt) {
		service.updateUserExt(sysUserExt);
	}

	@RequestMapping("/user/getUserExtByUserId")
	public ReturnData<SysUserExt> getUserExtByUserId(Long userId) throws UnsupportedEncodingException {
		SysUserExt sysUserExt = service.getUserExtByUserId(userId);
		if (sysUserExt != null && sysUserExt.getWechat() != null) {
			sysUserExt.setWechat(URLDecoder.decode(sysUserExt.getWechat(), "utf-8"));
		}
		return Result.ok(sysUserExt);
	}

	/**
	 * 生成验证码
	 */
	@Jurisdiction("personCenter_myselfInfo_basicInfo_bindwx")
	@RequestMapping(value = "/user/getQRCode")
	public void getQRCode(Long userId, HttpServletResponse response) {
		QRCodeReqVO request = new QRCodeReqVO();
		UserIdVo userIdVo = new UserIdVo();
		userIdVo.setUserId(String.valueOf(userId));
		request.setCallbackParameter(JsonUtils.bean2Json(userIdVo));
		Result.ReturnData<QRCodeRpsVO> result = weChatApi.getQRCode(request);
		byte[] bytes = null;
		OutputStream os = null;
		if (result.success) {
			if (result.getBody() != null) {
				QRCodeRpsVO qrCodeRpsVO = (QRCodeRpsVO)result.getBody();
				bytes = qrCodeRpsVO.getQrCodeBytes();
			}
		} else {
			logger.error("生成二维码失败:" + result.getMsg());
		}
		try {
			response.setContentType("image/png");
			os = response.getOutputStream();
			os.write(bytes);
			os.flush();
		}catch (Exception e) {
			logger.error("生成二维码失败:" + e.getMessage());
		}finally {
			IOUtils.closeQuietly(os);
		}

	}

	@Jurisdiction("personCenter_myselfInfo_basicInfo_unBindwx")
	@RequestMapping("/user/userUnBindWechat")
	public void userUnBindWechat(Long userId) {
		service.userUnBindWechat(userId);
	}

	/**
	 * 查询某个用户的菜单或者按钮
	 * @param userId
	 * @param menuType
	 * @return
	 */
	@Jurisdiction(value = "system_user_defquery")
	@RequestMapping("/user/querySysMenuByUser")
	public ReturnData<List<SysMenu>> querySysMenuByUser(Long userId,Integer menuType) {
		List<SysMenu> list = service.querySysMenuByUser(userId.intValue(), menuType);
		return Result.ok(list);
	}
	

	/**
	 * 提供给权限注解切面调用
	 */
	@RequestMapping("/user/queryButtonByUser")
	public ReturnData<List<String>> queryButtonByUser(Long userId)
	{
		String roleId = getRoleByUserId(userId).getBody().get(0).getId().toString();
		List<String> urlList = redisUtil.getT("Key_Jurisdiction_"+roleId);
		if(urlList != null){return Result.ok(urlList);}
		urlList = new ArrayList<>();
		List<SysMenu> sysMenuList = service.querySysMenuByUser(userId.intValue(), 2);
		if (sysMenuList != null && !sysMenuList.isEmpty()) {
			for (SysMenu sysMenu : sysMenuList) {
				urlList.add(sysMenu.getUrl());
			}
		}
		redisUtil.set("Key_Jurisdiction_"+roleId, urlList);
		return Result.ok(urlList);
	}

	/**
	 * 查询用户数据查询权限
	 */
	@RequestMapping("/user/queryUserDataAuth")
	public ReturnData<UserAuth> queryUserDataAuth(Long userId){
		UserAuth userAuth = dataLocalCacheUtil.queryUserAuth(userId.intValue());
		return Result.ok(userAuth);
	}

	
	/**
	 * 校验用户是否人工坐席
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/isAgentUser")
	public ReturnData<Boolean> isAgentUser(@RequestParam("userId") Integer userId){
		boolean isAgent = service.isAgentUser(userId);
		return Result.ok(isAgent);
	}

	@Override
	@RequestMapping("/user/getUserByOpenId")
	public ReturnData<List<SysUser>> getUserByOpenId(@RequestParam("openId") String openId)
	{
		return Result.ok(service.getUserByOpenId(openId));
	}

	/**
	 * 获取首页账号数
	 */
	@RequestMapping("/user/getUserCount")
	public ReturnData<Integer> getUserCount(@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode) {
		return Result.ok(service.getUserCount(userId,authLevel,orgCode));
	}
	
	@RequestMapping("/user/queryUserRobotNumByOpenId")
	public ReturnData<List<UserRobotNumVO>> queryUserRobotNumByOpenId(String openId)
	{
		List<UserRobotNumVO> list = new ArrayList<>();
		List<SysUser> users = getUserByOpenId(openId).getBody();
		for(SysUser user : users)
		{
			int robotNum = organizationService.countRobotByUserId(user.getId());
			UserRobotNumVO vo = new UserRobotNumVO();
			vo.setUsernName(user.getUsername());
			vo.setRobotNum(robotNum);
			list.add(vo);
		}
		return Result.ok(list);
	}
}