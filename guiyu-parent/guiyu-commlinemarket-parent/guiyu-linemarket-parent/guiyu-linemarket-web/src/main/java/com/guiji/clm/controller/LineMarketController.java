package com.guiji.clm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.clm.dao.entity.SipLineApply;
import com.guiji.clm.dao.entity.SipLineBaseInfo;
import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.dao.entity.SipLineShare;
import com.guiji.clm.dao.entity.ext.SipLineQuery;
import com.guiji.clm.dao.ext.SipLineExclusiveMapperExt;
import com.guiji.clm.enm.SipLineStatusEnum;
import com.guiji.clm.service.sip.SipLineApplyService;
import com.guiji.clm.service.sip.SipLineExclusiveService;
import com.guiji.clm.service.sip.SipLineInfoService;
import com.guiji.clm.service.sip.SipLineManager;
import com.guiji.clm.service.sip.SipLineShareService;
import com.guiji.clm.util.AreaDictUtil;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.SipLineApplyQueryCondition;
import com.guiji.clm.vo.SipLineApplyVO;
import com.guiji.clm.vo.SipLineBaseInfoVO;
import com.guiji.clm.vo.SipLineExclusiveQueryCondition;
import com.guiji.clm.vo.SipLineExclusiveVO;
import com.guiji.clm.vo.SipLineInfoQueryCondition;
import com.guiji.clm.vo.SipLineShareQueryCondition;
import com.guiji.clm.vo.SipLineShareVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.StrUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 线路市场Controller 
* @Author: weiyunbo
* @date 2019年1月31日 上午10:41:03 
* @version V1.0  
*/
@Slf4j
@RestController
@RequestMapping(value = "/sip")
public class LineMarketController {
	@Autowired
	SipLineApplyService sipLineApplyService;
	@Autowired
	SipLineExclusiveService sipLineExclusiveService;
	@Autowired
	SipLineInfoService sipLineInfoService;
	@Autowired
	SipLineShareService sipLineShareService;
	@Autowired
	SipLineManager sipLineManager;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	SipLineExclusiveMapperExt sipLineExclusiveMapperExt;
	
	/**
	 * 统计线路数量
	 * 一般客户统计自己的
	 * 管理员按企业统计
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/querySipLineExclusiveNum", method = RequestMethod.POST)
	public Result.ReturnData querySipLineExclusiveNum(
			@RequestHeader Long userId,
			@RequestHeader String orgCode,
			@RequestHeader Boolean isSuperAdmin){
		SipLineQuery query = new SipLineQuery();
		List<Integer> lineStatusList = new ArrayList<Integer>();
		lineStatusList.add(SipLineStatusEnum.OK.getCode());	//正常线路
		query.setLineStatusList(lineStatusList);
		if(isSuperAdmin) {
			//查询全部
		}else {
			//如果有管理员角色，按企业查询
			boolean isAdmin = false;
			List<SysRole> roleList = dataLocalCacheUtil.queryUserRole(userId.toString());
			if(roleList!=null && !roleList.isEmpty()) {
				for(SysRole role : roleList) {
					if("管理员".equals(role.getName())) {
						//TODO  如果是管理员，先简单判断吧，后续可以增加企业编号字段来确定，现在表里的orgcode是企业真实code，不能直接使用
						isAdmin = true;
						break;
					}
				}
			}
			if(isAdmin) {
				query.setOrgCode(orgCode);
			}else {
				query.setUserId(userId.toString());
			}
		}
		Integer totalNum = sipLineExclusiveMapperExt.querySipLineExclusiveNum(query);
		return Result.ok(totalNum);
	}
	
	/**
	 * 新增/修改第三方线路
	 * @param sipLineBaseInfo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/saveSipLine", method = RequestMethod.POST)
	public Result.ReturnData<SipLineBaseInfo> saveSipLine(
			@RequestBody SipLineBaseInfo sipLineBaseInfo,
			@RequestHeader Long userId,
			@RequestHeader Boolean isSuperAdmin){
		if(sipLineBaseInfo!=null) {
			sipLineBaseInfo.setCrtUser(userId.toString());
			sipLineBaseInfo.setUpdateUser(userId.toString());
			sipLineBaseInfo = sipLineManager.thirdSipLineCfg(sipLineBaseInfo,isSuperAdmin);
			return Result.ok(sipLineBaseInfo);
		}
		return Result.ok();
	}
	
	/**
	 * 删除第三方线路
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delThirdSipLine", method = RequestMethod.POST)
	public Result.ReturnData delThirdSipLine(@RequestParam(value="id",required=true) Integer id){
		sipLineManager.delThirdSipLineCfg(id);
		return Result.ok();
	}
	
	
	/**
	 * 生效第三方线路
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/effectThirdSip", method = RequestMethod.POST)
	public Result.ReturnData effectThirdSip(
			@RequestParam(value="id",required=true) Integer id,
			@RequestHeader Long userId,
			@RequestHeader Boolean isSuperAdmin){
		sipLineManager.effectThirdSip(id, isSuperAdmin);
		return Result.ok();
	}
	
	/**
	 * 查询第三方线路列表-分页
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/querySipLineBaseForPageByCondition", method = RequestMethod.POST)
	public Result.ReturnData<Page<SipLineBaseInfoVO>> querySipLineBaseForPageByCondition(@RequestBody SipLineInfoQueryCondition condition){
		//分页查询申请线路列表
		if(condition==null) {
			condition = new SipLineInfoQueryCondition();
		}
		if(condition.getStatusList()==null || condition.getStatusList().isEmpty()) {
			//默认查询未生效和正常数据
			condition.setStatusList(new ArrayList<Integer>(){{add(SipLineStatusEnum.INIT.getCode());add(SipLineStatusEnum.OK.getCode());}});
		}
		Page<SipLineBaseInfo> sipLineBasePage = sipLineInfoService.querySipLineBaseForPageByCondition(condition);
		Page<SipLineBaseInfoVO> rtnPage = new Page<SipLineBaseInfoVO>(condition.getPageNo(),sipLineBasePage.getTotalRecord(),this.baseLine2VO(sipLineBasePage.getRecords()));
		return Result.ok(rtnPage);
	}
	
	
	/**
	 * 查询可申请线路-分页
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/querySipLineSharePage", method = RequestMethod.POST)
	public Result.ReturnData<Page<SipLineShareVO>> querySipLineSharePage(@RequestBody SipLineShareQueryCondition condition){
		//分页查询申请线路列表
		if(condition==null) {
			condition = new SipLineShareQueryCondition();
		}
		if(condition.getStatusList()==null || condition.getStatusList().isEmpty()) {
			//默认查询正常
			condition.setStatusList(new ArrayList<Integer>(){{add(SipLineStatusEnum.OK.getCode());}});
		}
		Page<SipLineShare> shareLinePage = sipLineShareService.querySipShareForPageByCondition(condition);
		Page<SipLineShareVO> rtnPage = new Page<SipLineShareVO>(condition.getPageNo(),shareLinePage.getTotalRecord(),this.shareLine2VO(shareLinePage.getRecords()));
		return Result.ok(rtnPage);
	}
	
	
	/**
	 * 申请线路
	 * @param sipLineApply
	 * @return
	 */
	@RequestMapping(value = "/sipLineApply", method = RequestMethod.POST) 
	public Result.ReturnData sipLineApply(@RequestBody SipLineApply sipLineApply,@RequestHeader Long userId){
		sipLineApply.setApplyUser(userId.toString());
		sipLineApply.setCrtUser(userId.toString());
		sipLineApply.setUpdateUser(userId.toString());
		sipLineManager.applySipLine(sipLineApply);
		return Result.ok();
	}
	
	/**
	 * 根据共享线路ID查询真实第三方线路列表
	 * @param sipShareId
	 * @return
	 */
	@RequestMapping(value = "/queryThirdSipLineByShareId", method = RequestMethod.POST)
	public Result.ReturnData<List<SipLineBaseInfoVO>> queryThirdSipLineByShareId(@RequestParam(value="sipShareId",required=true) Integer sipShareId){
		SipLineInfoQueryCondition condition = new SipLineInfoQueryCondition();
		condition.setSipShareId(sipShareId);
		if(condition.getStatusList()==null || condition.getStatusList().isEmpty()) {
			//默认查询正常
			condition.setStatusList(new ArrayList<Integer>(){{add(SipLineStatusEnum.OK.getCode());}});
		}
		List<SipLineBaseInfo> list = sipLineInfoService.querySipLineBaseListByCondition(condition);
		return Result.ok(this.baseLine2VO(list));
	}
	
	
	/**
	 * 查询申请的线路列表-分页
	 * 申请、审批都这个查询
	 * @return
	 */
	@RequestMapping(value = "/queryApplyLinePage", method = RequestMethod.POST)
	public Result.ReturnData<Page<SipLineApplyVO>> queryApplyLinePage(@RequestBody SipLineApplyQueryCondition condition){
		Page<SipLineApply> page = sipLineApplyService.querySipLineApplyForPageByCondition(condition);
		Page<SipLineApplyVO> rtnPage = new Page<SipLineApplyVO>(page.getPageNo(),page.getTotalRecord(),this.applyLine2VO(page.getRecords()));
		return Result.ok(rtnPage);
	}
	
	
	/**
	 * SIP线路审批
	 * @param sipLineApply
	 * @return
	 */
	@RequestMapping(value = "/approveSipLine", method = RequestMethod.POST)
	public Result.ReturnData approveSipLine(@RequestBody SipLineApply sipLineApply,@RequestHeader Long userId){
		sipLineApply.setApproveUser(userId.toString());
		sipLineApply.setUpdateUser(userId.toString());
		sipLineManager.approveSipLine(sipLineApply);
		return Result.ok();
	}
	
	/**
	 * 查询我的线路-分页
	 * @param sipLineApply
	 * @return
	 */
	@RequestMapping(value = "/queryMyExclusiveSipLinePage", method = RequestMethod.POST)
	public Result.ReturnData<Page<SipLineExclusiveVO>> queryMyExclusiveSipLinePage(@RequestBody SipLineExclusiveQueryCondition condition,@RequestHeader Long userId){
		if(condition==null) condition=new SipLineExclusiveQueryCondition();
		if(StrUtils.isEmpty(condition.getUserId())) {
			condition.setUserId(userId.toString());
		}
		if(condition.getStatusList()==null || condition.getStatusList().isEmpty()) {
			//默认查询正常
			condition.setStatusList(new ArrayList<Integer>(){{add(SipLineStatusEnum.OK.getCode());}});
		}
		Page<SipLineExclusive> page = sipLineExclusiveService.querySipLineExclusiveForPageByCondition(condition);
		Page<SipLineExclusiveVO> rtnPage = new Page<SipLineExclusiveVO>(condition.getPageNo(),page.getTotalRecord(),this.exclusiveLine2VO(page.getRecords()));
		return Result.ok(rtnPage);
	}
	
	/**
	 * 查询我的线路
	 * @param sipLineApply
	 * @return
	 */
	@RequestMapping(value = "/queryMyExclusiveSipLine", method = RequestMethod.POST)
	public Result.ReturnData<List<SipLineExclusiveVO>> queryMyExclusiveSipLine(@RequestBody SipLineExclusiveQueryCondition condition,@RequestHeader Long userId){
		if(condition==null) condition=new SipLineExclusiveQueryCondition();
		if(StrUtils.isEmpty(condition.getUserId())) {
			condition.setUserId(userId.toString());
		}
		if(condition.getStatusList()==null || condition.getStatusList().isEmpty()) {
			//默认查询正常
			condition.setStatusList(new ArrayList<Integer>(){{add(SipLineStatusEnum.OK.getCode());}});
		}
		List<SipLineExclusive> list = sipLineExclusiveService.querySipLineExclusiveList(condition);
		return Result.ok(this.exclusiveLine2VO(list));
	}
	
	/**
	 * 根据条件查询线路信息
	 * @param sipLineApply
	 * @return
	 */
	@RequestMapping(value = "/queryConditionExclusiveSipLinePage", method = RequestMethod.POST)
	public Result.ReturnData<Page<SipLineExclusiveVO>> queryConditionExclusiveSipLinePage(@RequestBody SipLineExclusiveQueryCondition condition){
		if(condition==null) condition=new SipLineExclusiveQueryCondition();
		if(condition.getStatusList()==null || condition.getStatusList().isEmpty()) {
			//默认查询正常
			condition.setStatusList(new ArrayList<Integer>(){{add(SipLineStatusEnum.OK.getCode());}});
		}
		Page<SipLineExclusive> page = sipLineExclusiveService.querySipLineExclusiveForPageByCondition(condition);
		Page<SipLineExclusiveVO> rtnPage = new Page<SipLineExclusiveVO>(condition.getPageNo(),page.getTotalRecord(),this.exclusiveLine2VO(page.getRecords()));
		return Result.ok(rtnPage);
	}
	
	
	/**
	 * 将共享线路转VO返回前端页面
	 * @param list
	 * @return
	 */
	private List<SipLineShareVO> shareLine2VO(List<SipLineShare> list){
		if(list!=null && !list.isEmpty()) {
			List<SipLineShareVO> voList = new ArrayList<SipLineShareVO>();
			for(SipLineShare sipLineShare : list) {
				SipLineShareVO vo = new SipLineShareVO();
				BeanUtil.copyProperties(sipLineShare, vo,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
				//TODO 接通率
				vo.setUnivalentStr(sipLineShare.getUnivalent()+"元/分钟");
				//线路拥有者
				vo.setLineOwner(sipLineManager.getLineOwner(dataLocalCacheUtil.queryOrgByCode(sipLineShare.getOrgCode())));
				if(StrUtils.isNotEmpty(vo.getOvertArea())) {
					//外显归属地
					vo.setOvertAreaName(AreaDictUtil.getAreaName(vo.getOvertArea()));
				}
				if(StrUtils.isNotEmpty(vo.getAreas())) {
					//地区
					vo.setAreasName(AreaDictUtil.getAreaName(vo.getAreas()));
				}
				if(StrUtils.isNotEmpty(vo.getExceptAreas())) {
					//盲区
					vo.setExceptAreasName(AreaDictUtil.getAreaName(vo.getExceptAreas()));
				}
				voList.add(vo);
			}
			return voList;
		}
		return null;
	}
	
	
	/**
	 * 将线路基本信息转VO返回前端页面
	 * @param list
	 * @return
	 */
	private List<SipLineBaseInfoVO> baseLine2VO(List<SipLineBaseInfo> list){
		if(list!=null && !list.isEmpty()) {
			List<SipLineBaseInfoVO> voList = new ArrayList<SipLineBaseInfoVO>();
			for(SipLineBaseInfo sipLineBaseInfo : list) {
				SipLineBaseInfoVO vo = new SipLineBaseInfoVO();
				BeanUtil.copyProperties(sipLineBaseInfo, vo,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
				if(StrUtils.isNotEmpty(vo.getOvertArea())) {
					//外显归属地
					vo.setOvertAreaName(AreaDictUtil.getAreaName(vo.getOvertArea()));
				}
				if(StrUtils.isNotEmpty(vo.getAreas())) {
					//地区
					vo.setAreasName(AreaDictUtil.getAreaName(vo.getAreas()));
				}
				if(StrUtils.isNotEmpty(vo.getExceptAreas())) {
					//盲区
					vo.setExceptAreasName(AreaDictUtil.getAreaName(vo.getExceptAreas()));
				}
				//企业名称
				if(StrUtils.isNotEmpty(sipLineBaseInfo.getBelongOrgCode())) {
					SysOrganization org = dataLocalCacheUtil.queryOrgByCode(sipLineBaseInfo.getBelongOrgCode());
					if(org != null) {
						vo.setBelongOrgName(org.getName());
					}
				}
				//线路拥有者(查询原线路的归属企业)
				vo.setLineOwner(sipLineManager.getLineOwner(sipLineBaseInfo));
				vo.setContractUnivalentStr(sipLineBaseInfo.getContractUnivalent()+"元/分钟");
				vo.setUnivalentStr(sipLineBaseInfo.getUnivalent()+"元/分钟");
				if(StrUtils.isEmpty(sipLineBaseInfo.getBelongOrgCode())) {
					//没有归属企业 - 共享线路
					if(SipLineStatusEnum.INIT.getCode()==sipLineBaseInfo.getLineStatus()) {
						//共享线路没有生效前，可以编辑
						vo.setEditable(true);
						vo.setEffectable(true); //需要显示生效按钮
					}else {
						vo.setEditable(false);
						vo.setEffectable(false); 
					}
				}else {
					//自备线路
					vo.setEditable(true);
					vo.setEffectable(false); 
				}
				voList.add(vo);
			}
			return voList;
		}
		return null;
	}
	
	/**
	 * 将线路基本信息转VO返回前端页面
	 * @param list
	 * @return
	 */
	private List<SipLineExclusiveVO> exclusiveLine2VO(List<SipLineExclusive> list){
		if(list!=null && !list.isEmpty()) {
			List<SipLineExclusiveVO> voList = new ArrayList<SipLineExclusiveVO>();
			for(SipLineExclusive sipLineExclusive : list) {
				SipLineExclusiveVO vo = new SipLineExclusiveVO();
				BeanUtil.copyProperties(sipLineExclusive, vo,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
				if(StrUtils.isNotEmpty(vo.getOvertArea())) {
					//外显归属地
					vo.setOvertAreaName(AreaDictUtil.getAreaName(vo.getOvertArea()));
				}
				if(StrUtils.isNotEmpty(vo.getAreas())) {
					//地区
					vo.setAreasName(AreaDictUtil.getAreaName(vo.getAreas()));
				}
				if(StrUtils.isNotEmpty(vo.getExceptAreas())) {
					//盲区
					vo.setExceptAreasName(AreaDictUtil.getAreaName(vo.getExceptAreas()));
				}
				//线路拥有者(查询原线路的归属企业)
				Integer sipLineId = sipLineExclusive.getSipLineId();
				SipLineBaseInfo sipLineBaseInfo = sipLineInfoService.queryById(sipLineId);
				vo.setLineOwner(sipLineManager.getLineOwner(sipLineBaseInfo));
				vo.setUnivalentStr(sipLineExclusive.getUnivalent()+"元/分钟");
				voList.add(vo);
			}
			return voList;
		}
		return null;
	}
	
	
	/**
	 * 将线路申请信息转VO返回前端页面
	 * @param list
	 * @return
	 */
	private List<SipLineApplyVO> applyLine2VO(List<SipLineApply> list){
		if(list!=null && !list.isEmpty()) {
			List<SipLineApplyVO> voList = new ArrayList<SipLineApplyVO>();
			for(SipLineApply sipLineApply : list) {
				SipLineApplyVO vo = new SipLineApplyVO();
				BeanUtil.copyProperties(sipLineApply, vo,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
				if(StrUtils.isNotEmpty(vo.getApplyUser())) {
					SysUser sysUser = dataLocalCacheUtil.queryUser(vo.getApplyUser());
					if(sysUser!=null) {
						vo.setApplyUserName(sysUser.getUsername());
					}
				}
				if(StrUtils.isNotEmpty(vo.getApplyOrgCode())) {
					SysOrganization org = dataLocalCacheUtil.queryOrgByCode(vo.getApplyOrgCode());
					if(org!=null) {
						vo.setApplyOrgName(org.getName());
					}
				}
				//设置共享线路部分字段
				Integer agentLineId = sipLineApply.getAgentLineId();	//共享代理线路ID
				if(agentLineId!=null) {
					SipLineShare sipLineShare = sipLineShareService.queryById(agentLineId);
					if(StrUtils.isNotEmpty(sipLineShare.getOvertArea())) {
						//外显归属地
						vo.setOvertAreaName(AreaDictUtil.getAreaName(sipLineShare.getOvertArea()));
					}
					if(StrUtils.isNotEmpty(sipLineShare.getAreas())) {
						//地区
						vo.setAreasName(AreaDictUtil.getAreaName(sipLineShare.getAreas()));
					}
					if(StrUtils.isNotEmpty(sipLineShare.getExceptAreas())) {
						//盲区
						vo.setExceptAreasName(AreaDictUtil.getAreaName(sipLineShare.getExceptAreas()));
					}
				}
				//线路拥有者
				vo.setLineOwner(sipLineManager.getLineOwner(dataLocalCacheUtil.queryOrgByCode(sipLineApply.getOrgCode())));
				vo.setUnivalentStr(sipLineApply.getUnivalent()+"元/分钟");
				voList.add(vo);
			}
			return voList;
		}
		return null;
	}
	
	
}
