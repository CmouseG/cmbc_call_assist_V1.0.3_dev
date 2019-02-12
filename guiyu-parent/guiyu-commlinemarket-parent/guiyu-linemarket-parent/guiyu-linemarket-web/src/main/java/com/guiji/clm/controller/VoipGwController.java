package com.guiji.clm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.clm.dao.entity.VoipGwInfo;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.service.voip.VoipGwManager;
import com.guiji.clm.service.voip.VoipGwPortService;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.VoipGwInfoVO;
import com.guiji.clm.vo.VoipGwPortQueryCondition;
import com.guiji.clm.vo.VoipGwPortVO;
import com.guiji.clm.vo.VoipGwQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 语音网关外部服务
* @Author: weiyunbo
* @date 2019年1月28日 下午8:46:55 
* @version V1.0  
*/
@Slf4j
@RestController
@RequestMapping(value = "/voip")
public class VoipGwController {
	@Autowired
	VoipGwManager voipGwManager;
	@Autowired
	VoipGwPortService voipGwPortService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;

	/**
	 * 语音网关设备初始化
	 * @param voipGwInfo
	 * @return
	 */
	@RequestMapping(value = "/gwInit", method = RequestMethod.POST)
	public Result.ReturnData<VoipGwInfo> receiveSellbotCallback(
			@RequestBody VoipGwInfo voipGwInfo,@RequestHeader Long userId){
		if(voipGwInfo!=null) {
			voipGwInfo.setUserId(userId.toString());
			voipGwInfo.setCrtUser(userId.toString());
			voipGwInfo.setUpdateUser(userId.toString());
		}
		VoipGwInfo initedGwInfo = voipGwManager.startVoipGwInit(voipGwInfo);
		return Result.ok(initedGwInfo);
	}
	
	/**
	 * 分页查询网关设备信息（带监控信息）
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/queryVoipGwPage", method = RequestMethod.POST)
	public Result.ReturnData<Page<VoipGwInfoVO>> queryVoipGwPage(
			@RequestBody VoipGwQueryCondition condition,
			@RequestHeader Long userId, 
			@RequestHeader Boolean isSuperAdmin
			){
		if(!isSuperAdmin) {
			//不是超管,按企业查询，超管查询全部
			SysOrganization sysOrganization = dataLocalCacheUtil.queryUserRealOrg(userId.toString());
			if(sysOrganization!=null) {
				String orgCode = sysOrganization.getCode();
				if(condition == null) {
					condition=new VoipGwQueryCondition();
				}
				condition.setOrgCode(orgCode);
			}else {
				log.error("用户：{}所属企业为空,不能查询",userId);
				return Result.ok();
			}
		}
		Page<VoipGwInfoVO> page = voipGwManager.queryVoipGwForPageWrap(condition.getPageNo(), condition.getPageSize(), condition);
		return Result.ok(page);
	}
	
	
	/**
	 * 查询某个网关对应的端口信息
	 * @param gwId
	 * @return
	 */
	@RequestMapping(value = "/queryVoipGwPortListWrap", method = RequestMethod.POST)
	public Result.ReturnData<List<VoipGwPortVO>> queryVoipGwPortListWrap(@RequestParam(value="gwId",required=true)Integer gwId){
		VoipGwPortQueryCondition condition = new VoipGwPortQueryCondition();
		condition.setGwId(gwId);
		List<VoipGwPortVO> portList = voipGwManager.queryVoipGwPortListWrap(condition);
		return Result.ok(portList);
	}
	
	
	/**
	 * 插卡分配
	 * @param gwPortIdList 待分配列表
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/assignGwPort", method = RequestMethod.POST)
	public Result.ReturnData assignGwPort(
			@RequestBody List<VoipGwPort> gwPortList,
			@RequestHeader Long userId){
		if(gwPortList!=null && !gwPortList.isEmpty()) {
			voipGwManager.assignGwPort(gwPortList, userId.toString());
		}
		return Result.ok();
	}
	
	
	/**
	 * 拔卡
	 * @param gwPortList
	 * @param assigedUserId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/unAssignGwPort", method = RequestMethod.POST)
	public Result.ReturnData unAssignGwPort(@RequestBody List<Integer> gwPortIdList,@RequestHeader Long userId){
		if(gwPortIdList!=null && !gwPortIdList.isEmpty()) {
			voipGwManager.unAssignGwPort(gwPortIdList, userId.toString());
		}
		return Result.ok();
	}
	
	
	/**
	 * 根据条件查询VOIP端口数据
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/queryVoipPortList", method = RequestMethod.POST)
	public Result.ReturnData queryVoipPortList(@RequestBody VoipGwPortQueryCondition condition){
		List<VoipGwPort> list = voipGwPortService.queryVoipGwPortList(condition);
		return Result.ok(list);
	}
}
