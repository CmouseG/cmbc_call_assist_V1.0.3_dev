package com.guiji.da.service.impl.robot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.da.dao.RobotCallDetailMapper;
import com.guiji.da.dao.entity.RobotCallDetail;
import com.guiji.da.dao.entity.RobotCallDetailExample;
import com.guiji.da.service.robot.IRobotCallCallDetailService;
import com.guiji.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 通话详情
* @Author: weiyunbo
* @date 2019年1月2日 下午8:18:13 
* @version V1.0  
*/
@Slf4j
@Service
public class RobotCallCallDetailServiceImpl implements IRobotCallCallDetailService{
	@Autowired
	RobotCallDetailMapper robotCallDetailMapper;
	
	
	/**
	 * 新增/更新通话详情
	 * @param RobotCallDetail
	 * @return
	 */
	@Transactional
	public RobotCallDetail save(RobotCallDetail robotCallDetail) {
		if(robotCallDetail.getId()!=null) {
			//更新
			robotCallDetail.setUpdateTime(DateUtil.getCurrent4Time());
			robotCallDetailMapper.updateByPrimaryKey(robotCallDetail);
		}else {
			//新增
			robotCallDetail.setCrtDate(DateUtil.getCurrentymd());
			robotCallDetail.setCrtTime(DateUtil.getCurrent4Time());
			robotCallDetail.setUpdateTime(DateUtil.getCurrent4Time());
			int id = robotCallDetailMapper.insert(robotCallDetail);
			robotCallDetail.setId(id);
		}
		return robotCallDetail;
	}
	
	/**
	 * 根据主键ID查询某个通话详细
	 * @param id
	 * @return
	 */
	public RobotCallDetail queryRobotCallDetailById(int id) {
		return robotCallDetailMapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 根据会话id查询通话详细
	 * @param seqId
	 * @return
	 */
	public RobotCallDetail queryRobotCallDetailBySeqId(String seqId) {
		RobotCallDetailExample example = new RobotCallDetailExample();
		example.createCriteria().andSeqIdEqualTo(seqId);
		List<RobotCallDetail> detailList = robotCallDetailMapper.selectByExample(example);
		if(detailList!=null && !detailList.isEmpty()){
			return detailList.get(0);
		}
		return null;
	}
}
