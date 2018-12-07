package com.guiji.da.service.impl.robot;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.da.dao.RobotCallHisMapper;
import com.guiji.da.dao.entity.RobotCallHis;
import com.guiji.da.dao.entity.RobotCallHisExample;
import com.guiji.da.service.robot.IRobotCallHisService;
import com.guiji.utils.DateUtil;
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
	
	
	/**
	 * 保存或者更新一TTS合成信息
	 * 同时记录历史
	 * @param robotCallbackHis
	 * @return
	 */
	@Transactional
	@Override
	public RobotCallHis saveOrUpdate(RobotCallHis robotCallbackHis) {
		if(robotCallbackHis != null) {
			if(StrUtils.isEmpty(robotCallbackHis.getId())) {
				//如果主键为空，那么新增一条信息
				robotCallbackHis.setCrtTime(new Date());
				robotCallbackHis.setCrtDate(DateUtil.getCurrentymd()); //创建日期 yyyy-MM-dd
				robotCallHisMapper.insert(robotCallbackHis);	//创建时间
			}else {
				//主键不为空，更新信息
				robotCallHisMapper.updateByPrimaryKeyWithBLOBs(robotCallbackHis);
			}
		}
		return robotCallbackHis;
	}
	
	/**
	 * 根据主键ID查询某个通话记录
	 * @param id
	 * @return
	 */
	@Override
	public RobotCallHis queryRobotCallById(String id) {
		if(StrUtils.isNotEmpty(id)) {
			return robotCallHisMapper.selectByPrimaryKey(id);
		}
		return null;
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
}
