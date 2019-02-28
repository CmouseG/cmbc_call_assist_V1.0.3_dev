package com.guiji.auth.api;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.user.vo.SysUserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;

@FeignClient("guiyu-auth-web")
public interface IAuth {
	
	@RequestMapping(value = "/user/getUserById")
	public ReturnData<SysUser> getUserById(@RequestParam("userId") Long userId);
	
	@RequestMapping("/user/getRoleByUserId")
	public ReturnData<List<SysRole>> getRoleByUserId(@RequestParam("userId") Long userId);
	
	@RequestMapping("/user/changeAccessKey")
	public ReturnData<String> changeAccessKey(@RequestParam("userId") Long userId);
	
	@RequestMapping("/user/changeSecretKey")
	public ReturnData<String> changeSecretKey(@RequestParam("userId") Long userId);

	@RequestMapping("/user/getOrgByUserId")
	public ReturnData<SysOrganization> getOrgByUserId(@RequestParam("userId") Long userId);

	@RequestMapping("/user/getAllCompanyUser")
	public ReturnData<List<SysUser>> getAllCompanyUser();

	@GetMapping("/user/apiUpdatePassword")
	Result.ReturnData apiUpdatePassword(@RequestParam("newPass") String newPass,
									 @RequestParam("oldPass")  String oldPass, @RequestParam("userId")  Long userId) throws Exception;

	@RequestMapping("/user/insertCustmomService")
	public ReturnData<SysUser> insertCustmomService( @RequestParam("username") String username, @RequestParam("password") String password,  @RequestParam("userId") Long userId);

	@RequestMapping("/user/checkUsernameIsExist")
	public ReturnData<Boolean> checkUsernameIsExist( @RequestParam("username") String username);

	@RequestMapping("/user/getAllUserByOrgCode")
	public ReturnData<List<SysUser>> getAllUserByOrgCode(@RequestParam("orgCode") String orgCode);

	@RequestMapping("/user/getAllUserRoleByOrgCode")
	public ReturnData<List<SysUserRoleVo>> getAllUserRoleByOrgCode(@RequestParam("orgCode") String orgCode);

	@RequestMapping("/user/getUserExtByUserId")
	public ReturnData<SysUserExt> getUserExtByUserId(@RequestParam("userId") Long userId) throws UnsupportedEncodingException;
	
}
