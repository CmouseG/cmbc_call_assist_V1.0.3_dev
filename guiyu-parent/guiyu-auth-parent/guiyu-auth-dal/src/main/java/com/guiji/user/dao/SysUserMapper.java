package com.guiji.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.guiji.common.model.Page;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExample;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.user.vo.UserParamVo;

public interface SysUserMapper {
    int countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    //
    void addRole(@Param("userId")Long userId,@Param("roleId")Long roleId);
    
    String getPassword(String principal);
    
    Long getUserId(@Param("username")String username,@Param("password")String password); 
    
    Long getUserIdForLogin(@Param("username")String username,@Param("password")String password);
    
    List<SysUser> getUserByName(String userName);
    
    List<SysRole> getRoleByUserId(Long id);
    
    int count();
    
    List<Map<String,String>> getUsersByPage(Page<Map<String,String>> page);
    
    void insertUserRole(@Param("userId")Long userId,@Param("roleId")Long roleId);
    
    public boolean existUserName(SysUser user);
    
    public int countByParamVo(UserParamVo param);
    
    public List<Object> selectByParamVo(UserParamVo param);
    
    public String getSecretKey(String accessKey);
    
    public boolean checkSecretKey(String secretKey);
    
    public boolean checkAccessKey(String AccessKey);
    
    public SysUser getUserById(Long id);

    public List<Object> selectLikeUserName(@Param("param")UserParamVo param, @Param("userId")Integer userId, @Param("authLevel")Integer authLevel, @Param("orgCode")String orgCode);

    public List<SysUser> getAllCompanyUser();

    public List<SysUser> getAllUserByOrgCode(String orgCode);

    void addUserExt(@Param("userId")Long userId);

    SysUserExt getSysUserExtByUserId(Long id);

	Integer getAuthLevelByUserId(Long userId);

	List<SysUser> getUserByOpenId(@Param("openId") String openId);

	Long checkEffective(Long userId);
}