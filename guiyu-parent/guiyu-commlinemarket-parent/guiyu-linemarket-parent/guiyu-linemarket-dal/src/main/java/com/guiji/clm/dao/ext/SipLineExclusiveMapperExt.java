package com.guiji.clm.dao.ext;

import com.guiji.clm.dao.entity.ext.SipLineQuery;

/** 
* @ClassName: SipLineExclusiveMapperExt 
* @Description: 线路扩展服务
* @date 2019年2月28日 下午1:40:07 
* @version V1.0  
*/
public interface SipLineExclusiveMapperExt {

	/**
	 * 按条件统计线路
	 * @return
	 */
	Integer querySipLineExclusiveNum(SipLineQuery query);
}
