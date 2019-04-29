package com.guiji.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.api.IProduct;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ProductStatusEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.ProductCondition;
import com.guiji.auth.model.ProductTemplatesVO;
import com.guiji.auth.model.ProductVO;
import com.guiji.auth.service.OrganizationService;
import com.guiji.auth.service.PrivilegeService;
import com.guiji.auth.service.ProductService;
import com.guiji.auth.service.UserService;
import com.guiji.auth.util.DataLocalCacheUtil;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysProduct;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: ProductController 
* @Description: 产品信息管理功能
* @auth weiyunbo
* @date 2019年3月11日 下午6:05:00 
* @version V1.0  
*/
@RestController
@RequestMapping("product")
public class ProductController implements IProduct{
	@Autowired
	ProductService productService;
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	UserService userService;

	/**
	 * 新增/更新产品信息
	 * @param product
	 * @return
	 */
	@Jurisdiction("system_productManagement_add,system_productManagement_edit")
	@RequestMapping("saveProduct")
	public Result.ReturnData saveProduct(
			@RequestBody ProductVO product,
			@RequestHeader Integer userId,
			@RequestHeader String orgCode){
		if(product.getId()==null) {
			product.setCrtUser(userId);
		}else {
			SysProduct extSysProduct = productService.queryById(product.getId());
			product.setProductStatus(extSysProduct.getProductStatus()); //状态
			product.setCrtUser(extSysProduct.getCrtUser());
			product.setCrtTime(extSysProduct.getCrtTime());
		}
		product.setUpdateUser(userId);
		productService.save(product);
		if (product.getIndustryIds() != null && !product.getIndustryIds().isEmpty()) {
			//给企业绑定行业资源
			privilegeService.savePrivlegeTree(userId, orgCode, AuthObjTypeEnum.PRODUCT.getCode(), product.getId().toString(), ResourceTypeEnum.TRADE.getCode(), product.getIndustryIds());
		}
		if (product.getMenuIds() != null && !product.getMenuIds().isEmpty()) {
			//给企业绑定菜单资源
			privilegeService.savePrivlegeTree(userId, orgCode, AuthObjTypeEnum.PRODUCT.getCode(), product.getId().toString(), ResourceTypeEnum.MENU.getCode(), product.getMenuIds());
		}
		return Result.ok();
	}
	
	
	/**
	 * 根据用户编号查询所属产品
	 * @param userId
	 * @return
	 */
	@RequestMapping("queryProductByUserId")
	public Result.ReturnData<SysProduct> queryProductByUserId(Long userId){
		if(userId !=null) {
			SysOrganization sysOrganization = userService.getOrgByUserId(userId);
			if(sysOrganization!=null) {
				List<Integer> productIds = sysOrganization.getProduct();
				if(productIds!=null && !productIds.isEmpty()) {
					SysProduct sysProduct = productService.queryById(productIds.get(0));
					return Result.ok(sysProduct);
				}
			}
		}
		return Result.ok();
	}
	
	
	/**
	 * 根据用户编号查询所属产品
	 * @param userId
	 * @return
	 */
	@RequestMapping("queryProductByOrgCode")
	public Result.ReturnData<SysProduct> queryProductByOrgCode(String orgCode){
		if(StrUtils.isNotEmpty(orgCode)) {
			SysOrganization sysOrganization = organizationService.getOrgByCode(orgCode);
			if(sysOrganization!=null) {
				List<Integer> productIds = sysOrganization.getProduct();
				if(productIds!=null && !productIds.isEmpty()) {
					SysProduct sysProduct = productService.queryById(productIds.get(0));
					return Result.ok(sysProduct);
				}
			}
		}
		return Result.ok();
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @param userId
	 * @return
	 */
	@Jurisdiction("system_productManagement_delete")
	@RequestMapping("delProduct")
	public Result.ReturnData delProduct(
			@RequestParam(value="id",required=true)Integer id,
			@RequestHeader Integer userId){
		SysProduct sysProduct = productService.queryById(id);
		if(sysProduct!=null) {
			List<Integer> orgIdList = organizationService.getOrgByProductId(id);
			if(orgIdList!=null && !orgIdList.isEmpty()) {
				throw new GuiyuException("产品使用中,不能直接删除!");
			}
			sysProduct.setProductStatus(ProductStatusEnum.INVALID.getCode()); //打删除标记
			sysProduct.setUpdateUser(userId);
			sysProduct.setUpdateTime(DateUtil.getCurrent4Time());
			//删除产品
			productService.save(sysProduct);
			//删除产品-菜单关系
			privilegeService.delProivilegeTree(userId, id.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.MENU.getCode());
			//删除产品-模板关系
			privilegeService.delProivilegeTree(userId, id.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.TRADE.getCode());
		}
		return Result.ok();
	}
	
	/**
	 * 按条件查询产品信息-分页
	 * @param condition
	 * @return
	 */
	@RequestMapping("queryProductForPage")
	public Result.ReturnData<Page<ProductVO>> queryProductForPage(@RequestBody ProductCondition condition){
		if(condition==null) {
			condition = new ProductCondition();
		}
		if(condition.getProductStatus()==null || condition.getProductStatus().isEmpty()) {
			List<Integer> list = new ArrayList<>();
			list.add(ProductStatusEnum.OK.getCode());
			condition.setProductStatus(list);	//默认查询正常状态的产品
		}
		Page<SysProduct> page = productService.queryProductPageByCondition(condition);
		Page<ProductVO> rtnPage = new Page<ProductVO>(condition.getPageNo(),page.getTotalRecord(),this.product2VO(page.getRecords()));
		return Result.ok(rtnPage);
	}
	
	
	/**
	 * 返回本企业下行业权限以及本产品的行业权限
	 * @param productId
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("getIndustrysByOrgId")
	public ReturnData<Map<String,Object>> getIndustrysByOrg(
			Integer productId,
			@RequestHeader String orgCode){
		return Result.ok(productService.getTemplateTradeByProduct(orgCode,productId));
	}
	
	/**
	 * 转VO
	 * @param list
	 * @return
	 */
	private List<ProductVO> product2VO(List<SysProduct> list){
		if(list!=null && !list.isEmpty()) {
			List<ProductVO> rtnList = new ArrayList<ProductVO>();
			for(SysProduct sysProduct : list) {
				ProductVO vo = new ProductVO();
				BeanUtil.copyProperties(sysProduct, vo);
				if(sysProduct.getCrtUser()!=null) {
					SysUser sysUser = dataLocalCacheUtil.queryUser(sysProduct.getCrtUser());
					if(sysUser!=null) {
						vo.setCrtUserName(sysUser.getUsername());
					}
				}
				if(sysProduct.getUpdateUser()!=null) {
					SysUser sysUser = dataLocalCacheUtil.queryUser(sysProduct.getUpdateUser());
					if(sysUser!=null) {
						vo.setUpdateUserName(sysUser.getUsername());
					}
				}
				rtnList.add(vo);
			}
			return rtnList;
		}
		return null;
	}


	/**
	 * 保存创建的模版到关联关系（privilege）
	 */
	@Override
	@PostMapping("saveProductTemplates")
	public void saveProductTemplates(@RequestBody ProductTemplatesVO productTemplates)
	{
		List<SysProduct> productList = productService.queryOkProductList();
		for(SysProduct product : productList){
			privilegeService.savePrivlege(1, "1", 1, product.getId().toString(), 2, productTemplates.getTemplateIds());
		}
	}
}
