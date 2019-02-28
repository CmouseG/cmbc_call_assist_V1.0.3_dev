package com.guiji.auth.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.auth.model.UserIdVo;
import com.guiji.auth.service.OrganizationService;
import com.guiji.auth.util.RandomValidateCodeUtil;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.utils.JsonUtils;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.vo.QRCodeReqVO;
import com.guiji.wechat.vo.QRCodeRpsVO;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.service.UserService;
import com.guiji.auth.util.AuthUtil;
import com.guiji.common.model.Page;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.vo.SysUserVo;
import com.guiji.user.vo.UserParamVo;
import com.guiji.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	private static final String REDIS_USER_BY_ID = "REDIS_USER_BY_USERID_";

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
			throw new CheckConditionException("00010005");
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
			user.setVaildTime(new Date());
		} else {
			user.setVaildTime(parseStringDate(param.getVaildTime()));
		}
		service.insert(user, param.getRoleId());
		return user;
	}

	private Date parseStringDate(String date) {
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.parse(date);
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
		return Date.from(zdt.toInstant());
	}

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
	public Page<Object> getUserByPage(UserParamVo param, @RequestHeader Long userId) {
		return service.getUserByPage(param, userId);
	}

	@RequestMapping("/user/getUserById")
	public ReturnData<SysUser> getUserById(Long userId) {
		SysUser sysUser = service.getUserById(userId);
		return Result.ok(sysUser);
	}

	@RequestMapping("/user/getUserByName")
	public List<Map<String, String>> getUserByName(String username) {
		return service.getUserByName(username);
	}

	@RequestMapping("/user/changePassword")
	public void changePassword(String newPass, String oldPass, @RequestHeader Long userId)
			throws CheckConditionException {
		service.changePassword(newPass, oldPass, userId);
	}

	@GetMapping("/user/apiUpdatePassword")
	public Result.ReturnData apiUpdatePassword(@RequestParam("newPass") String newPass,@RequestParam("oldPass")  String oldPass,
										@RequestParam("userId")  Long userId) throws Exception{

		service.changePassword(newPass, oldPass, userId);
		return Result.ok();
	}

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
	public List<Object> selectLikeUserName(UserParamVo param, @RequestHeader Long userId) {
		return service.selectLikeUserName(param, userId);
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

	@RequestMapping("/user/getAllUserRoleByOrgCode")
	public ReturnData<List<SysUserRoleVo>> getAllUserRoleByOrgCode(@RequestParam("orgCode") String orgCode) {
		return new ReturnData<List<SysUserRoleVo>>(service.getAllUserRoleByOrgCode(orgCode));
	}

	@RequestMapping("/user/updateUserExt")
	public void updateUserExt(SysUserExt sysUserExt) {
		service.updateUserExt(sysUserExt);
	}

	@RequestMapping("/user/getUserExtByUserId")
	public ReturnData<SysUserExt> getUserExtByUserId(Long userId) throws UnsupportedEncodingException {
		SysUserExt sysUserExt = service.getUserExtByUserId(userId);
		sysUserExt.setWechat(URLDecoder.decode(sysUserExt.getWechat(), "utf-8"));
		return Result.ok(sysUserExt);
	}

	/**
	 * 生成验证码
	 */
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

	@RequestMapping("/user/userUnBindWechat")
	public void userUnBindWechat(Long userId) {
		service.userUnBindWechat(userId);
	}



}