package com.guiji.cloud.zuul.controller;

import com.guiji.auth.api.IApiLogin;
import com.guiji.auth.api.IAuth;
import com.guiji.cloud.api.ILogin;
import com.guiji.cloud.zuul.config.AuthUtil;
import com.guiji.cloud.zuul.config.JwtConfig;
import com.guiji.cloud.zuul.entity.JwtToken;
import com.guiji.cloud.zuul.entity.WxAccount;
import com.guiji.cloud.zuul.service.ZuulService;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class LoginController implements ILogin {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final static int SUPER_ADMIN = 0;

    @Autowired
    private ZuulService zuulService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    IApiLogin iApiLogin;
    @Autowired
    IAuth auth;


    /**
     * 微信小程序登录，小程序不支持cookie
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("login")
    public Result.ReturnData login(@RequestParam("username") String username, @RequestParam("password") String password) {

		boolean isSuperAdmin = false;
		Long userId = zuulService.getUserId(username, AuthUtil.encrypt(password));
		if(userId == null){
			return Result.error("00010003");
		}
		userId = checkEffective(userId);
		if(userId == null){
			return Result.error("00010007");
		}
		List<SysRole> sysRoles = zuulService.getRoleByUserId(userId);
		if (sysRoles != null){
			for (SysRole sysRole : sysRoles){
				if (SUPER_ADMIN == sysRole.getSuperAdmin()){
					isSuperAdmin = true;
					break;
				}
			}
		}
		Long roleId = sysRoles.get(0).getId().longValue();
		long orgId = -1;
		ReturnData<SysOrganization> result = auth.getOrgByUserId(userId);
		if (result != null && result.getBody() != null)
		{
			orgId = result.getBody().getId();
		}
		SysUser sysUser = sysUserMapper.getUserById(userId);
		WxAccount wxAccount = new WxAccount();
		wxAccount.setUserId(userId);
		wxAccount.setOrgCode(sysUser.getOrgCode());
		wxAccount.setSuperAdmin(isSuperAdmin);
		wxAccount.setIsDesensitization(sysUser.getIsDesensitization());
		wxAccount.setLastTime(new Date());
		Integer authLevel = null;
		if (sysRoles.get(0).getDataAuthLevel() == null)
		{
			logger.error("用户：{}，没有配置数据查询权限，默认最低权限，查询本人数据..", username);
			authLevel = 1;
		} else
		{
			authLevel = sysRoles.get(0).getDataAuthLevel();
		}
		wxAccount.setAuthLevel(authLevel);
		wxAccount.setOrgId(orgId);
		wxAccount.setRoleId(roleId);
		String jwtToken = jwtConfig.createTokenByWxAccount(wxAccount);
		JwtToken token = new JwtToken(jwtToken);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isSuperAdmin", isSuperAdmin);
		map.put("token", jwtToken);
		map.put("username", sysUser.getUsername());
		map.put("isDesensitization", sysUser.getIsDesensitization());
		map.put("orgId", orgId);
		map.put("authLevel", authLevel);
		map.put("roleId", roleId);
		return Result.ok(map);
	}

    private Long checkEffective(Long userId)
	{
    	return zuulService.checkEffective(userId);
	}

	@RequestMapping("loginOut")
    public void loginOut (@RequestHeader String Authorization) {
        jwtConfig.deleteToken(Authorization);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @GetMapping("apiLogin")
    public Result.ReturnData apiLogin (@RequestParam("accessKey") String accessKey, @RequestParam("secretKey") String secretKey){

        try {
            boolean isSuperAdmin = false;
            Result.ReturnData<SysUser> result =iApiLogin.getUserByAccess(accessKey,secretKey);
            if(result==null || !result.success){
                return Result.error("00010003");
            }
            SysUser sysUser = result.getBody();
            if(sysUser==null || sysUser.getId()==null){
                return Result.error("00010003");
            }
            Long userId = sysUser.getId();
            List<SysRole> sysRoles = zuulService.getRoleByUserId(userId);
            if (sysRoles != null) {
                for (SysRole sysRole : sysRoles) {
                    if (SUPER_ADMIN == sysRole.getSuperAdmin()) {
                        isSuperAdmin = true;
                        break;
                    }
                }
            }
            Long roleId = sysRoles.get(0).getId().longValue();
            long orgId = -1;
            ReturnData<SysOrganization> returnData = auth.getOrgByUserId(userId);
            if(returnData != null && returnData.getBody() != null) {
            	orgId = returnData.getBody().getId();
            }

            WxAccount wxAccount = new WxAccount();
            wxAccount.setUserId(userId);
            wxAccount.setOrgCode(sysUser.getOrgCode());
            wxAccount.setSuperAdmin(isSuperAdmin);
            wxAccount.setIsDesensitization(sysUser.getIsDesensitization());
            wxAccount.setLastTime(new Date());
            wxAccount.setOrgId(orgId);
            wxAccount.setRoleId(roleId);
            String jwtToken = jwtConfig.createTokenByWxAccount(wxAccount);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token", jwtToken);

            return Result.ok(map);
        } catch (Exception e) {
            return Result.error("00010003");
        }
    }

    @RequestMapping("refreshToken")
    public Result.ReturnData refreshToken(@RequestHeader String Authorization) {
        try {
            String token = jwtConfig.verifyRefreshToken(Authorization);
            return Result.ok(token);
        } catch (Exception e) {
            return Result.error("00010005");

        }
    }

}
