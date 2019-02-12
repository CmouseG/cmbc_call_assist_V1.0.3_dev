package com.guiji.clm.service.sip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.clm.dao.SipRouteItemMapper;
import com.guiji.clm.dao.entity.SipRouteItem;
import com.guiji.clm.dao.entity.SipRouteItemExample;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 规则项服务
* @Author: weiyunbo
* @date 2019年2月1日 下午6:47:10 
* @version V1.0  
*/
@Slf4j
@Service
public class SipRouteItemService {
	@Autowired
	SipRouteItemMapper sipRouteItemMapper;
	
	/**
	 * 查询规则项
	 * @return
	 */
	public List<SipRouteItem> queryAllSipRouteItemList(){
		SipRouteItemExample example = new SipRouteItemExample();
		example.setOrderByClause(" seq");
		return sipRouteItemMapper.selectByExample(example);
	}
}
