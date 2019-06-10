package com.guiji.clm.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.dao.entity.SipLineBaseInfo;
import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.model.SimLineStatus;
import com.guiji.clm.model.SipLineVO;
import com.guiji.clm.service.LineService;
import com.guiji.clm.service.sip.SipLineExclusiveService;
import com.guiji.clm.service.sip.SipLineInfoService;
import com.guiji.clm.service.voip.VoipGwManager;
import com.guiji.clm.service.voip.VoipGwPortService;
import com.guiji.clm.util.AreaDictUtil;
import com.guiji.clm.vo.SipLineExclusiveQueryCondition;
import com.guiji.clm.vo.SipLineInfoQueryCondition;
import com.guiji.clm.vo.VoipGwPortQueryCondition;
import com.guiji.component.result.Result;
import com.guiji.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	VoipGwPortService voipGwPortService;
	@Autowired
	SipLineInfoService sipLineInfoService;
	
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
	 * 本接口只给呼叫中心提供查询线路名称的服务，会先查sip线路，如果不是，那么查询卡线，再然后从原始线路中查询名称
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
		SipLineVO vo = new SipLineVO();
		List<SipLineExclusive> list = SipLineExclusiveService.querySipLineExclusiveList(condition);

		if(list!=null && !list.isEmpty()) {

			vo = exclusive2SipLine(list.get(0));
			vo.setLineId(lineId);
			vo.setUserId(userId);
			vo.setUnivalent(list.get(0).getUnivalent());
			vo.setLineName(list.get(0).getLineName());
			vo.setLineType(1);
			return Result.ok(vo);
		}else {
			vo.setLineType(2);
			vo.setLineId(lineId);
			vo.setUserId(userId);
			//卡线
			VoipGwPortQueryCondition portCondition = new VoipGwPortQueryCondition();
			portCondition.setLineId(lineId);
			List<VoipGwPort> portLieList = voipGwPortService.queryVoipGwPortList(portCondition);
			if(portLieList!=null && !portLieList.isEmpty()) {
				if(StrUtils.isNotEmpty(portLieList.get(0).getPhoneNo())) {
					//卡线如果有手机号，那么返回手机号
					vo.setLineName(portLieList.get(0).getPhoneNo());
				}else {
					vo.setLineName("网关端口"+portLieList.get(0).getPort());
				}
				return Result.ok(vo);
			}else {
				//从原始线路中查找下
				SipLineInfoQueryCondition sipCondition = new SipLineInfoQueryCondition();
				sipCondition.setLineId(lineId);
				List<SipLineBaseInfo> baseLineList = sipLineInfoService.querySipLineBaseListByCondition(sipCondition);
				if(baseLineList!=null && !baseLineList.isEmpty()) {
					vo.setLineName(baseLineList.get(0).getLineName());
					return Result.ok(vo);
				}
			}
		}
		return Result.ok();
    }

    @Autowired
	LineService lineService;

	/**
	 * 查询用户线路名
	 * @param userId
	 * @param lineId
	 * @return
	 */
	@Override
	public Result.ReturnData<SipLineVO> queryLineNameByLineId(
			@RequestParam(value="userId",required=true) String userId,
			@RequestParam(value="lineId",required=true) Integer lineId) {

		SipLineVO vo = lineService.queryLineInfo(Integer.valueOf(userId), lineId);

		return Result.ok(vo);
	}



    @Autowired
	VoipGwManager voipGwManager;


	@Override
	public Result.ReturnData<SimLineStatus> querySimLineStatus(Integer lineId) {

		return Result.ok(voipGwManager.querySimLineStatus(lineId));

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
