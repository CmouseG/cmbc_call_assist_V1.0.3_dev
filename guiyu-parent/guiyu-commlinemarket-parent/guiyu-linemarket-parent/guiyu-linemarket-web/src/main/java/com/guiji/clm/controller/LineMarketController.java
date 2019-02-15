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
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.StrUtils;

import cn.hutool.core.bean.BeanUtil;
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
	 * 查询可申请线路-分页
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/querySipLineSharePage", method = RequestMethod.POST)
	public Result.ReturnData<Page<SipLineShareVO>> querySipLineSharePage(@RequestBody SipLineShareQueryCondition condition){
		//分页查询申请线路列表
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
				BeanUtil.copyProperties(sipLineShare, vo);
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
				BeanUtil.copyProperties(sipLineBaseInfo, vo);
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
	private List<SipLineExclusiveVO> exclusiveLine2VO(List<SipLineExclusive> list){
		if(list!=null && !list.isEmpty()) {
			List<SipLineExclusiveVO> voList = new ArrayList<SipLineExclusiveVO>();
			for(SipLineExclusive sipLineExclusive : list) {
				SipLineExclusiveVO vo = new SipLineExclusiveVO();
				BeanUtil.copyProperties(sipLineExclusive, vo);
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
				BeanUtil.copyProperties(sipLineApply, vo);
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
				Integer sipLineId = sipLineApply.getSipLineId();
				SipLineBaseInfo sipLineBaseInfo = sipLineInfoService.queryById(sipLineId);
				if(sipLineBaseInfo==null) {
					List<SipLineBaseInfo> baseList = sipLineInfoService.queryByBaseSipId(sipLineApply.getAgentLineId()); 
					if(baseList!=null) {
						sipLineBaseInfo = baseList.get(0);
					}
				}
				vo.setLineOwner(sipLineManager.getLineOwner(sipLineBaseInfo));
				voList.add(vo);
			}
			return voList;
		}
		return null;
	}
	
	
}
