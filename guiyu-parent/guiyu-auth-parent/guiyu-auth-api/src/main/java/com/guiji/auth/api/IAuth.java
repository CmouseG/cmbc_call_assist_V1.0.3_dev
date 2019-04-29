package com.guiji.auth.api;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.auth.model.UserAuth;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExt;

@FeignClient(value = "guiyu-auth-web")
public interface IAuth {
	
	@RequestMapping(value = "/user/getUserById")
	public ReturnData<SysUser> getUserById(@RequestParam("userId") Long userId);

	@RequestMapping(value = "/user/getUserByAccessKey")
	public ReturnData<SysUser> getUserByAccessKey(@RequestParam("accessKey") String accessKey);

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
	
	@RequestMapping("/user/getAllUserByUserId")
	public ReturnData<List<SysUser>> getAllUserByUserId(@RequestParam("userId") Long userId);

	@RequestMapping("/user/getAllUserRoleByOrgCode")
	public ReturnData<List<SysUserRoleVo>> getAllUserRoleByOrgCode(@RequestParam("orgCode") String orgCode);

	@RequestMapping("/user/getUserExtByUserId")
	public ReturnData<SysUserExt> getUserExtByUserId(@RequestParam("userId") Long userId) throws UnsupportedEncodingException;

	/**
	 * 查询某个用户的菜单或者按钮
	 * @param userId
	 * @param menuType
	 * @return
	 */
	@RequestMapping("/user/querySysMenuByUser")
	public ReturnData<List<SysMenu>> querySysMenuByUser(@RequestParam("userId")Long userId,@RequestParam("menuType")Integer menuType);
	
	/**
	 * 查询用户数据权限服务
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/queryUserDataAuth")
	public ReturnData<UserAuth> queryUserDataAuth(@RequestParam("userId")Long userId);
	
	@RequestMapping("/user/getUserByName")
	public ReturnData<List<SysUser>> getUserByName(String username);
	
	/**
	 * 校验用户是否人工坐席
	 * @param userId
	 * @return
	 */
	@RequestMapping("/user/isAgentUser")
	public ReturnData<Boolean> isAgentUser(@RequestParam("userId") Integer userId);

	@RequestMapping("/user/getUserByOpenId")
	ReturnData<List<SysUser>> getUserByOpenId(@RequestParam("openId") String openId);
}
