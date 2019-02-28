package com.guiji.clm.dao.entity.ext;

import java.util.List;

import lombok.Data;

/** 
* @ClassName: SipLineQuery 
* @Description: sip线路查询条件
* @date 2019年2月28日 下午1:26:37 
* @version V1.0  
*/
@Data
public class SipLineQuery {
	//按企业查询（like）
	private String orgCode;
	//查询某个用户
	private String userId;
	//查询状态
	private List<Integer> lineStatusList;
}
