package com.guiji.clm.controller;

import com.google.common.base.Function;
import com.guiji.clm.api.VoipMarketRemote;
import com.guiji.clm.dao.entity.VoipGwInfo;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.enm.VoipGwStatusEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.model.SimLineVo;
import com.guiji.clm.ro.OperPortLimitRo;
import com.guiji.clm.service.VoipGwPortLimitService;
import com.guiji.clm.service.voip.VoipGwManager;
import com.guiji.clm.service.voip.VoipGwPortService;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.*;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 
* @Description: 语音网关外部服务
* @Author: weiyunbo
* @date 2019年1月28日 下午8:46:55 
* @version V1.0  
*/
@Slf4j
@RestController
@RequestMapping(value = "/voip")
public class VoipGwController implements VoipMarketRemote {
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
	@Jurisdiction("setPort_save")
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
	 * 删除网关设备
	 * @param gwId
	 * @return
	 */
	@RequestMapping(value = "/delVoipGateway", method = RequestMethod.POST)
	public Result.ReturnData delVoipGateway(@RequestParam(value="gwId",required=true)Integer gwId){
		voipGwManager.delVoipGateway(gwId);
		return Result.ok();
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
			@RequestHeader Boolean isSuperAdmin,
			@RequestHeader String orgCode
			){
		if(!isSuperAdmin) {
			//不是超管,按企业查询，超管查询全部
			if(condition == null) {
				condition=new VoipGwQueryCondition();
			}
			condition.setOrgCode(orgCode);
		}
		if(condition.getGwStatus()==null) {
			//默认查询正常数据
			condition.setGwStatus(Lists.newArrayList(VoipGwStatusEnum.OK.getCode()));
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
	@Jurisdiction("setPort_insertCard,setPort_changeCard")
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
	@Jurisdiction("setPort_pullCard")
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
	public Result.ReturnData<List<VoipGwPort>> queryVoipPortList(@RequestBody VoipGwPortQueryCondition condition){
		if(condition == null) {
			condition = new VoipGwPortQueryCondition();
		}
		if(condition.getGwStatus()==null) {
			//默认查询正常数据
			condition.setGwStatus(Lists.newArrayList(VoipGwStatusEnum.OK.getCode()));
		}
		List<VoipGwPort> list = voipGwPortService.queryVoipGwPortList(condition);
		return Result.ok(list);
	}
	
	
	/**
	 * 根据条件查询VOIP端口数据-分页
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/queryVoipPortForPage", method = RequestMethod.POST)
	public Result.ReturnData<Page<VoipGwPortVO>> queryVoipPortForPage(
			@RequestBody VoipGwPortQueryCondition condition,
			@RequestHeader Long userId,
			@RequestHeader String orgCode){
		if(condition == null) {
			condition = new VoipGwPortQueryCondition();
		}
		if(condition.getGwStatus()==null) {
			//默认查询正常数据
			condition.setGwStatus(Lists.newArrayList(VoipGwStatusEnum.OK.getCode()));
		}
		condition.setOrgCode(orgCode);
		Page<VoipGwPortVO> portPage = voipGwManager.queryVoipGwPortListWrapForPage(condition);
		return Result.ok(portPage);
	}
	
	
	/**
	 * 查询已分配的端口列表
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/queryUserAvailableVoipPortList", method = RequestMethod.POST)
	public Result.ReturnData<List<VoipGwPort>> queryUserAvailableVoipPortList(@RequestHeader Long userId){
		VoipGwPortQueryCondition condition = new VoipGwPortQueryCondition();
		condition.setUserId(userId.toString());
		if(condition.getGwStatus()==null) {
			//默认查询正常数据
			condition.setGwStatus(Lists.newArrayList(VoipGwStatusEnum.OK.getCode()));
		}
		List<VoipGwPort> list = voipGwPortService.queryVoipGwPortList(condition);
		return Result.ok(list);
	}

	@Override
	@RequestMapping(value = "/querySimLineInfo", method = RequestMethod.POST)
	public Result.ReturnData<List<SimLineVo>> querySimLineInfo(@RequestParam("userId") Long userId) {
		VoipGwPortQueryCondition condition = new VoipGwPortQueryCondition();
		condition.setUserId(userId.toString());
		if (condition.getGwStatus() == null) {
			//默认查询正常数据
			condition.setGwStatus(com.google.common.collect.Lists.newArrayList(VoipGwStatusEnum.OK.getCode()));
		}
		List<VoipGwPort> list = voipGwPortService.queryVoipGwPortList(condition);

		return Result.ok(com.google.common.collect.Lists.transform(list, new Function<VoipGwPort, SimLineVo>() {
			@Override
			public SimLineVo apply(VoipGwPort voipGwPort) {

				SimLineVo simLineVo = new SimLineVo();
				BeanUtil.copyProperties(voipGwPort, simLineVo);
				return simLineVo;
			}
		}));
	}

	/**
	 * 新增
	 * @param ro
	 * @param userId
	 * @return
	 */
	@RequestMapping("/insertPortLimit")
	public Result.ReturnData insertPortLimit(@RequestBody OperPortLimitRo ro, @RequestHeader Integer userId) {

		if(CollectionUtils.isEmpty(ro.getRoList()) || ro.getPortId() == null) {
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(), ClmErrorEnum.C00060001.getErrorMsg());
		}

		ro.setUserId(userId);

		voipGwPortLimitService.insertLimitInfo(ro);

		return Result.ok();

	}

	/**
	 * 新增
	 * @param ro
	 * @param userId
	 * @return
	 */
	@RequestMapping("/editPortLimit")
	public Result.ReturnData editPortLimit(@RequestBody OperPortLimitRo ro, @RequestHeader Integer userId) {

		if(CollectionUtils.isEmpty(ro.getRoList())) {
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(), ClmErrorEnum.C00060001.getErrorMsg());
		}

		ro.setUserId(userId);

		voipGwPortLimitService.updateLimitInfo(ro);

		return Result.ok();

	}

	/**
	 * 查询
	 * @param portId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/queryPortLimit")
	public Result.ReturnData editPortLimit(@RequestParam(value = "portId", required = true) Integer portId, @RequestHeader Integer userId) {

		if(portId == null) {
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(), ClmErrorEnum.C00060001.getErrorMsg());
		}

		List<PortLimitInfoVo> voList = voipGwPortLimitService.queryPortLimitByPortId(portId);

		return Result.ok(voList);

	}


	@Autowired
	VoipGwPortLimitService voipGwPortLimitService;
}
