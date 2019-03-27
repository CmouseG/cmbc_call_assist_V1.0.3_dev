package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ProductStatusEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.ProductCondition;
import com.guiji.botsentence.api.IBotSentenceTradeService;
import com.guiji.botsentence.api.entity.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.common.model.Page;
import com.guiji.user.dao.SysProductMapper;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysProduct;
import com.guiji.user.dao.entity.SysProductExample;
import com.guiji.user.dao.entity.SysProductExample.Criteria;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: ProductService 
* @Description: 产品服务
* @date 2019年3月10日 下午3:07:09 
* @version V1.0  
*/
@Slf4j
@Service
public class ProductService {
	@Autowired
	OrganizationService organizationService;
	@Autowired
	SysProductMapper sysProductMapper;
	@Autowired
	private IBotSentenceTradeService botSentenceTradeService;
	@Autowired
	PrivilegeService privilegeService;
	
	
	
	/**
	 * 新增/更新产品
	 * @param sysProduct
	 * @return
	 */
	@Transactional
	public SysProduct save(SysProduct sysProduct) {
		if(sysProduct!=null) {
			if(sysProduct.getId()!=null) {
				//更新
				sysProduct.setUpdateTime(DateUtil.getCurrent4Time());
				sysProductMapper.updateByPrimaryKey(sysProduct);
			}else {
				//新增
				sysProduct.setProductStatus(ProductStatusEnum.OK.getCode());	//初始正常
				sysProduct.setCrtTime(DateUtil.getCurrent4Time());
				sysProduct.setUpdateTime(DateUtil.getCurrent4Time());
				sysProductMapper.insert(sysProduct);
			}
		}
		return sysProduct;
	}
	
	
	/**
	 * 根据主键ID查询产品
	 * @param id
	 * @return
	 */
	public SysProduct queryById(Integer id) {
		if(id != null) {
			return sysProductMapper.selectByPrimaryKey(id);
		}
		return null;
	}
	
	/**
	 * 查询所有状态正常的产品列表
	 * @return
	 */
	public List<SysProduct> queryOkProductList(){
		SysProductExample example = new SysProductExample();
		example.createCriteria().andProductStatusEqualTo(ProductStatusEnum.OK.getCode());
		example.setOrderByClause(" id desc");
		return sysProductMapper.selectByExample(example);
	}
	
	/**
	 * 查询产品列表
	 * @return
	 */
	public List<SysProduct> queryProductListByCondition(ProductCondition condition){
		SysProductExample example = this.queryExample(condition);
		return sysProductMapper.selectByExample(example);
	}
	
	/**
	 * 分页查询产品列表
	 * @param condition
	 * @return
	 */
	public Page<SysProduct> queryProductPageByCondition(ProductCondition condition){
		int pageNo = condition.getPageNo();
		int pageSize = condition.getPageSize();
		Page<SysProduct> page = new Page<SysProduct>();
		int totalRecord = 0;
		int limitStart = (pageNo-1)*pageSize;	//起始条数
		int limitEnd = pageSize;	//查询条数
		SysProductExample example = this.queryExample(condition);
		//查询总数
		totalRecord = sysProductMapper.countByExample(example);
		if(totalRecord > 0) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
			List<SysProduct> list = sysProductMapper.selectByExample(example);
			page.setRecords(list);
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(totalRecord);
		return page;
	}
	
	
	/**
	 * 按组织查询组织关联的行业-模板树形数据
	 * orgCode挂在pOrgCode下
	 * @param pOrgCode
	 * @param orgCode
	 * @return
	 */
	public Map<String,Object> getTemplateTradeByProduct(String orgCode,Integer productId){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StrUtils.isNotEmpty(orgCode)) {
			//获取当前用户所属企业的权限
			SysOrganization organization = organizationService.getOrgByCode(orgCode);
			if(organization!=null) {
				List<SysPrivilege> pOrgTradeList = privilegeService.queryPrivilegeListByAuth(organization.getCode(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.TRADE.getCode());
				if(pOrgTradeList!=null && !pOrgTradeList.isEmpty()) {
					List<String> pOrgTempList = this.getTemplateList(pOrgTradeList);
					//上级组织关联的行业-话术模板树形
					ServerResult<List<BotSentenceTemplateTradeVO>> pOrgTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(pOrgTempList);
					if(pOrgTempTradeTreeData!=null && pOrgTempTradeTreeData.getData()!=null) {
						map.put("trades", pOrgTempTradeTreeData.getData());
						if(StrUtils.isNotEmpty(productId)) {
							List<SysPrivilege> productTradeList = privilegeService.queryPrivilegeListByAuth(productId.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.TRADE.getCode());
							if(productTradeList!=null) {
								List<String> productTempList = this.getTemplateList(productTradeList);
								ServerResult<List<BotSentenceTemplateTradeVO>> productTempTradeTreeData = botSentenceTradeService.queryTradeListByTemplateIdList(productTempList);
								if(productTempTradeTreeData!=null && productTempTradeTreeData.getData()!=null) {
									map.put("selected", productTempTradeTreeData.getData());
								}
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 权限列表转模板list
	 * @param list
	 * @return
	 */
	private List<String> getTemplateList(List<SysPrivilege> list){
		if(list!=null && !list.isEmpty()) {
			List<String> tempList = new ArrayList<String>();
			for(SysPrivilege privilege : list) {
				tempList.add(privilege.getResourceId());
			}
			return tempList;
		}
		return null;
	}
	
	
	private SysProductExample queryExample(ProductCondition condition) {
		SysProductExample example = new SysProductExample();
		if(condition!=null) {
			Criteria criteria = example.createCriteria();
			if(StrUtils.isNotEmpty(condition.getName())) {
				criteria.andNameLike("%"+condition.getName()+"%");
			}
			if(StrUtils.isNotEmpty(condition.getProductDesc())) {
				criteria.andProductDescLike("%"+condition.getProductDesc()+"%");
			}
			if(condition.getProductStatus()!=null && !condition.getProductStatus().isEmpty()) {
				if(condition.getProductStatus().size()==1) {
					criteria.andProductStatusEqualTo(condition.getProductStatus().get(0));
				}else {
					criteria.andProductStatusIn(condition.getProductStatus());
				}
			}
		}
		example.setOrderByClause(" id desc");
		return example;
	}
	
}
