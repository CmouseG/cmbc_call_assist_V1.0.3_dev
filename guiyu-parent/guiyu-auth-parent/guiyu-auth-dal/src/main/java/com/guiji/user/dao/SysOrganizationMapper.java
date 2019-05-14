package com.guiji.user.dao;

import com.guiji.common.model.Page;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysOrganizationMapper {
    int countByExample(SysOrganizationExample example);

    int deleteByExample(SysOrganizationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);

    List<SysOrganization> selectByExample(SysOrganizationExample example);

    SysOrganization selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysOrganization record, @Param("example") SysOrganizationExample example);

    int updateByExample(@Param("record") SysOrganization record, @Param("example") SysOrganizationExample example);

    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);
    
    List<Object> selectByPage(Page page);
    
    public List<SysOrganization> getOrgByUserId(Long userId);
    
    public boolean existChildren(SysOrganization record);
    
    public int countCode(String code);
    
    public List<Map> selectOpenByPage(Page page);

    int countRobotByUserId(String code);

    void insertOrganizationProduct(@Param("organizationId") Long organizationId, @Param("userId") Long userId, @Param("product") List<Integer> product);

    void updateOrganizationProduct(@Param("organizationId") Long organizationId, @Param("userId") Long userId, @Param("product") List<Integer> product);

    List<Integer> getProductByOrganizationId(Long organizationId);
    
    List<Integer> getOrgByProductId(Integer productId);

    void insertOrganizationIndustry(@Param("organizationId") Long organizationId, @Param("orgCode") String orgCode, @Param("userId") Long userId, @Param("industryIds") List<String> industryIds);

    void updateOrganizationIndustry(@Param("organizationId") Long organizationId, @Param("orgCode") String orgCode, @Param("userId") Long userId, @Param("industryIds") List<String> industryIds);

    List<String> getIndustryByOrganizationId(Long organizationId);

    List<String> getIndustryByOrgCode(String orgCode);

	List<Integer> getSubOrgIdByOrgId(@Param("orgId") Integer orgId);

	List<Integer> getAllOrgId();

	void updateUsableByOrgId(@Param("orgId") Integer orgId);

	List<Map> querySubOrgByOrgId(@Param("orgId") Integer orgId);

	List<Map> getSubOrgByAuthLevel(@Param("userId")Long userId, @Param("authLevel")Integer authLevel, @Param("orgCode")String orgCode);

	List<SysOrganization> queryChildrenOrg(String orgCode);

	Map getOrgByUsername(String username);
}