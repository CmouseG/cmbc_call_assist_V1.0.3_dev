package com.guiji.clm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.enm.SipLineStatusEnum;
import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;
import com.guiji.utils.StrUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import com.guiji.clm.service.sip.SipLineExclusiveService;
import com.guiji.clm.util.AreaDictUtil;
import com.guiji.clm.vo.SipLineExclusiveQueryCondition;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 提供给其他系统的服务
* @Author: weiyunbo
* @date 2019年2月1日 下午5:32:13 
* @version V1.0  
*/
@Slf4j
@RestController
public class LineMarketRemoteController implements LineMarketRemote{
	@Autowired
	SipLineExclusiveService SipLineExclusiveService;
	
	/**
	 * 查询用户SIP线路列表
	 * @param userId
	 * @return
	 */
	public Result.ReturnData<List<SipLineVO>> queryUserSipLineList(@RequestParam(value="userId",required=true) String userId){
    	List<SipLineExclusive> list = SipLineExclusiveService.queryUserNormalSipLineList(userId);
    	return Result.ok(this.exclusive2SipLine(list));
    }
    
    
    /**
	 * 查询用户一条SIP线路
	 * @param id
	 * @return
	 */
    public Result.ReturnData<SipLineVO> queryUserSipLineList(@RequestParam(value="id",required=true) Integer id){
    	SipLineExclusive sipLineExclusive = SipLineExclusiveService.queryById(id);
    	return Result.ok(this.exclusive2SipLine(sipLineExclusive));
    }
    
    /**
	 * 查询用户SIP线路列表
	 * @param userId
	 * @param lineId
	 * @return
	 */
	public Result.ReturnData<SipLineVO> queryUserSipLineByLineId(
			@RequestParam(value="userId",required=true) String userId,
			@RequestParam(value="lineId",required=true) Integer lineId){
		SipLineExclusiveQueryCondition condition = new SipLineExclusiveQueryCondition();
		condition.setUserId(userId);
		condition.setLineId(lineId);
		List<SipLineExclusive> list = SipLineExclusiveService.querySipLineExclusiveList(condition);
		if(list!=null && !list.isEmpty()) {
			return Result.ok(this.exclusive2SipLine(list.get(0)));
		}
		return Result.ok();
    }
    
    /**
     * 线路转其他系统需要的属性返回
     * @param list
     * @return
     */
    private List<SipLineVO> exclusive2SipLine(List<SipLineExclusive> list){
    	if(list!=null && !list.isEmpty()) {
    		List<SipLineVO> rtnList = new ArrayList<SipLineVO>();
    		for(SipLineExclusive sipLineExclusive : list) {
				rtnList.add(this.exclusive2SipLine(sipLineExclusive));
    		}
    		return rtnList;
    	}
    	return null;
    }
    
    /**
     * 线路转其他系统需要的属性返回
     * @param sipLineExclusive
     * @return
     */
    private SipLineVO exclusive2SipLine(SipLineExclusive sipLineExclusive) {
    	if(sipLineExclusive!=null) {
    		SipLineVO vo = new SipLineVO();
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
				vo.setExceptAreasName(AreaDictUtil.getLowAreaNames(vo.getExceptAreas()));
			}
			return vo;
    	}
    	return null;
    }
}
