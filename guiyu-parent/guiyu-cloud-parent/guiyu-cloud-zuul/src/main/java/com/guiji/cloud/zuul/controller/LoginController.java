package com.guiji.cloud.zuul.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guiji.auth.api.IApiLogin;
import com.guiji.cloud.api.ILogin;
import com.guiji.cloud.zuul.config.JwtConfig;
import com.guiji.cloud.zuul.entity.JwtToken;
import com.guiji.cloud.zuul.entity.WxAccount;
import com.guiji.component.result.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guiji.cloud.zuul.config.AuthUtil;
import com.guiji.cloud.zuul.service.ZuulService;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;

@RestController
@RequestMapping
public class LoginController implements ILogin {
    private final static int SUPER_ADMIN = 0;

    @Autowired
    private ZuulService zuulService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    IApiLogin iApiLogin;


    /**
     * 微信小程序登录，小程序不支持cookie
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("login")
    public Result.ReturnData login(@RequestParam("username") String username,@RequestParam("password") String password) {

        try {
            boolean isSuperAdmin = false;

            Long userId = zuulService.getUserId(username, AuthUtil.encrypt(password));
            List<SysRole> sysRoles = zuulService.getRoleByUserId(userId);
            if (sysRoles != null) {
                for (SysRole sysRole : sysRoles) {
                    if (SUPER_ADMIN == sysRole.getSuperAdmin()) {
                        isSuperAdmin = true;
                        break;
                    }
                }
            }
            SysUser sysUser = sysUserMapper.getUserById(userId);
            WxAccount wxAccount = new WxAccount();
            wxAccount.setUserId(userId);
            wxAccount.setOrgCode(sysUser.getOrgCode());
            wxAccount.setSuperAdmin(isSuperAdmin);

            wxAccount.setLastTime(new Date());
            String jwtToken = jwtConfig.createTokenByWxAccount(wxAccount);
            JwtToken token = new JwtToken(jwtToken);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isSuperAdmin", isSuperAdmin);
            map.put("roleId", sysRoles.get(0).getId());
            map.put("token", jwtToken);
            return Result.ok(map);
        } catch (Exception e) {
            return Result.error("00010003");
        }
    }

    @RequestMapping("loginOut")
    public void loginOut (@RequestHeader String Authorization) {
        jwtConfig.deleteToken(Authorization);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @GetMapping("apiLogin")
    public Result.ReturnData apiLogin (@RequestParam("accessKey") String accessKey,@RequestParam("secretKey") String secretKey){

        try {
            boolean isSuperAdmin = false;
            SysUser sysUser =iApiLogin.getUserByAccess(accessKey,secretKey);
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

            WxAccount wxAccount = new WxAccount();
            wxAccount.setUserId(userId);
            wxAccount.setOrgCode(sysUser.getOrgCode());
            wxAccount.setSuperAdmin(isSuperAdmin);

            wxAccount.setLastTime(new Date());
            String jwtToken = jwtConfig.createTokenByWxAccount(wxAccount);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token", jwtToken);

            return Result.ok(map);
        } catch (Exception e) {
            return Result.error("00010003");
        }
    }

    }
