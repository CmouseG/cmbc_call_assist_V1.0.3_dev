package com.guiji.clm.controller;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.clm.api.LineSipRouteRemote;
import com.guiji.clm.dao.entity.SipRouteRule;
import com.guiji.clm.model.SipRouteItemVO;
import com.guiji.clm.model.SipRouteRuleVO;
import com.guiji.clm.service.sip.SipRouteRuleService;
import com.guiji.component.result.Result;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: LineMarketRemote
* @Description: 线路市场服务-线路路由
* @date 2019年1月29日 上午10:29:45 
* @version V1.0  
*/
@Slf4j
@RestController
public class LineSipRouteRemoteController implements LineSipRouteRemote{
	@Autowired
	SipRouteRuleService sipRouteRuleService;
	
	/**
	 * 查询用户sip线路路由规则
	 * @param userId
	 * @return
	 */
	public Result.ReturnData<List<SipRouteRuleVO>> querySipRouteRule(@RequestParam(value="userId",required=true) String userId){
		SipRouteRuleVO vo = new SipRouteRuleVO();
		List<SipRouteRule> list = sipRouteRuleService.queryBySipRouteListByUserId(userId);
		if(list!=null && !list.isEmpty()) {
			SipRouteRule rule = list.get(0);
			vo.setId(rule.getId());
			vo.setUserId(userId);
			vo.setRuleContent(rule.getRuleContent());
			List<SipRouteItemVO> itemList = new ArrayList<SipRouteItemVO>();
			if(StrUtils.isNotEmpty(rule.getRuleContent())) {
				String[] ruleItemArray = rule.getRuleContent().split(",");
				for(String ruleItem:ruleItemArray) {
					SipRouteItemVO itemVO = new SipRouteItemVO();
					itemVO.setItemCode(ruleItem);
					itemList.add(itemVO);
				}
			}
			vo.setItemList(itemList);
		}
		return Result.ok(Lists.newArrayList(vo));
    }
    
    
}
