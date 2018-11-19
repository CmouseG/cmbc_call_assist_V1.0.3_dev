package com.guiji.robot.service.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.service.IUserAiCfgService;

/** 
* @ClassName: CustAiCfgAccountController 
* @Description: 用户机器人服务
* @date 2018年11月16日 下午3:40:47 
* @version V1.0  
*/
@RequestMapping(value = "/account")
@RestController
public class CustAiAccountController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUserAiCfgService iUserAiCfgService;
	
	
	@RequestMapping(value = "/queryCustAccount", method = RequestMethod.POST)
	public Result.ReturnData<List<UserAiCfgInfo>> queryCustAccount(@RequestParam(value="userId",required=true)String userId){
		List<UserAiCfgInfo> list = iUserAiCfgService.queryUserAiCfgListByUserId(userId);
		return Result.ok(list);
	}
}
