package com.guiji.da.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.da.api.IDaRemote;
import com.guiji.da.model.RobotCallHis;
import com.guiji.da.service.impl.robot.RobotNewTransService;
import com.guiji.da.service.robot.IRobotCallHisService;
import com.guiji.utils.BeanUtil;

/** 
* @Description:量化分析对外服务
* @Author: weiyunbo
* @date 2019年1月2日 下午1:06:38 
* @version V1.0  
*/
@RestController
public class DaRemoteController implements IDaRemote{
	@Autowired
	IRobotCallHisService iRobotCallHisService;
	@Autowired
	RobotNewTransService robotNewTransService;
	
	/**
	 * 根据会话ID查询通话历史
	 * @param seqId
	 * @return
	 */
	@Override
	public ReturnData<RobotCallHis> queryRobotCallBySeqId(@RequestParam(value="seqId",required=true)String seqId) {
		com.guiji.da.dao.entity.RobotCallHis rch = iRobotCallHisService.queryRobotCallBySeqId(seqId);
		RobotCallHis rtnCallHis = new RobotCallHis();
		BeanUtil.copyProperties(rch, rtnCallHis);
		return Result.ok(rtnCallHis);
	}

	/**
	 * 根据会话ID更新通话历史通话状态
	 * @param seqId
	 * @param callStatus
	 * @return
	 */
	@Override
	public ReturnData updateCallStatus(@RequestParam(value="seqId",required=true) String seqId,@RequestParam(value="callStatus",required=true) int callStatus) {
		iRobotCallHisService.updateCallStatus(seqId, callStatus);
		return Result.ok();
	}
	

	/**
     * 保存通话历史
     * @param robotCallHis
     * @return
     */
	public ReturnData recordRobotCallHis(@RequestBody RobotCallHis robotCallHis){
    	if(robotCallHis!=null) {
    		com.guiji.da.dao.entity.RobotCallHis rch = new com.guiji.da.dao.entity.RobotCallHis();
        	BeanUtil.copyProperties(robotCallHis, rch);
        	robotNewTransService.recordRobotCallHis(rch);
    	}
    	return Result.ok();
    }

}
