package com.guiji.da.service.impl.robot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.da.dao.RobotCallHisMapper;
import com.guiji.da.dao.entity.RobotCallHis;
import com.guiji.da.dao.entity.RobotCallHisExample;
import com.guiji.da.service.robot.IRobotCallHisService;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: RobotCallbackHisServiceImpl 
* @Description: 机器人通话纪录 
* @date 2018年12月6日 下午2:20:35 
* @version V1.0  
*/
@Service
public class RobotCallHisServiceImpl implements IRobotCallHisService{
	@Autowired
	RobotCallHisMapper robotCallHisMapper; 
	@Autowired
	RobotNewTransService robotNewTransService;
	
	
	
	/**
	 * 根据主键ID查询某个通话记录
	 * @param id
	 * @return
	 */
	@Override
	public RobotCallHis queryRobotCallById(int id) {
		return robotCallHisMapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 根据会话id查询童话记录
	 * @param seqId
	 * @return
	 */
	@Override
	public RobotCallHis queryRobotCallBySeqId(String seqId) {
		if(StrUtils.isNotEmpty(seqId)) {
			RobotCallHisExample example = new RobotCallHisExample();
			example.createCriteria().andSeqIdEqualTo(seqId);
			List<RobotCallHis> list = robotCallHisMapper.selectByExample(example);
			if(list != null && !list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}
	
	
	/**
	 * 根据会话id更新通话状态
	 * @param seqId
	 * @param callStatus
	 */
	@Override
	public void updateCallStatus(String seqId,int callStatus) {
		if(StrUtils.isNotEmpty(seqId)) {
			RobotCallHis robotCallHis = this.queryRobotCallBySeqId(seqId);
			if(robotCallHis!=null) {
				robotCallHis.setCallStatus(callStatus);
				robotNewTransService.recordRobotCallHis(robotCallHis);
			}
			
		}
	}
	
}
