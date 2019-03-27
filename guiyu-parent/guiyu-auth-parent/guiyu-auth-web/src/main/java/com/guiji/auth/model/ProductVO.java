package com.guiji.auth.model;

import java.util.List;

import com.guiji.user.dao.entity.SysProduct;

import lombok.Data;

/** 
* @ClassName: ProductVO 
* @Description: 产品信息前端展示数据
* @auth weiyunbo
* @date 2019年3月13日 下午4:28:54 
* @version V1.0  
*/
@Data
public class ProductVO extends SysProduct{
	//行业列表
	private List<String> industryIds;
    //菜单列表
    private List<String> menuIds;
    
    //前端显示
    //创建人
    private String crtUserName;
    //最后修改人
    private String updateUserName;
}
