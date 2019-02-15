package com.guiji.clm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.clm.dao.entity.SipRouteRule;
import com.guiji.clm.model.SipRouteRuleVO;
import com.guiji.clm.service.sip.SipRouteRuleService;
import com.guiji.component.result.Result;
import com.guiji.utils.StrUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;

/** 
* @Description: SIP线路路由规则
* @Author: weiyunbo
* @date 2019年2月2日 上午9:32:01 
* @version V1.0  
*/
@Slf4j
@RestController
@RequestMapping(value = "/rule")
public class SipRouteRuleController {
	@Autowired
	SipRouteRuleService sipRouteRuleService;
	
	
	/**
	 * 根据用户查询用户SIP线路规则路由配置情况
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserSipRouteRule", method = RequestMethod.POST)
	public Result.ReturnData<SipRouteRuleVO> queryUserSipRouteRule(
			@RequestParam(value="userId",required=false) String qUserId,
			@RequestHeader Long userId){
		if(StrUtils.isEmpty(qUserId)) {
			qUserId = userId.toString();
		}
		SipRouteRuleVO vo = sipRouteRuleService.queryUserSipRouteRuleVO(qUserId);
		return Result.ok(vo);
	}
	
	/**
	 * 保存用户SIP线路规则
	 * @param sipRouteRule
	 * @return
	 */
	@RequestMapping(value = "/saveUserSipRoute", method = RequestMethod.POST)
	public Result.ReturnData saveUserSipRoute(
			@RequestBody SipRouteRule sipRouteRule,
			@RequestHeader Long userId) {
		if(sipRouteRule!=null && sipRouteRule.getId()!=null) {
			SipRouteRule extSipRouteRule = sipRouteRuleService.queryById(sipRouteRule.getId());
			BeanUtil.copyProperties(extSipRouteRule, sipRouteRule, CopyOptions.create().setIgnoreProperties("ruleContent"));
		}else {
			if(StrUtils.isEmpty(sipRouteRule.getUserId())) {
				sipRouteRule.setUserId(userId.toString());
			}
			sipRouteRule.setCrtUser(userId.toString());
		}
		sipRouteRule.setUpdateUser(userId.toString());
		sipRouteRuleService.save(sipRouteRule);
		return Result.ok();
	}
}
