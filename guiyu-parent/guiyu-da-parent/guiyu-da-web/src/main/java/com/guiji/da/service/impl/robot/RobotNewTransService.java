package com.guiji.da.service.impl.robot;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.da.dao.RobotCallHisMapper;
import com.guiji.da.dao.RobotCallProcessStatMapper;
import com.guiji.da.dao.entity.RobotCallHis;
import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiNewTransService 
* @Description: 独立事务服务类，在一个service中独立另一个事务同个service没有效果的，此处单拉一个service用来处理这个问题
* @date 2018年12月6日 下午4:51:17 
* @version V1.0  
*/
@Service
public class RobotNewTransService {
	@Autowired
	RobotCallHisMapper robotCallHisMapper; 
	@Autowired
	RobotCallProcessStatMapper robotCallProcessStatMapper; 
	
	/**
	 * 保存或者更新一TTS合成信息
	 * 独立事物
	 * @param ttsWavHis
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public RobotCallHis recordRobotCallHis(RobotCallHis robotCallHis) {
		if(robotCallHis != null) {
			if(StrUtils.isEmpty(robotCallHis.getId())) {
				//如果主键为空，那么新增一条信息
				robotCallHis.setCrtTime(new Date());
				robotCallHis.setCrtDate(DateUtil.getCurrentymd()); //创建日期 yyyy-MM-dd
				robotCallHisMapper.insert(robotCallHis);	//创建时间
			}else {
				//主键不为空，更新信息
				robotCallHisMapper.updateByPrimaryKeyWithBLOBs(robotCallHis);
			}
		}
		return robotCallHis;
	}
	
	
	/**
	 * 保存或者更新一条统计分析数据
	 * 同时记录历史
	 * @param ttsWavHis
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public RobotCallProcessStat recordRobotCallProcessStat(RobotCallProcessStat robotCallProcessStat) {
		if(robotCallProcessStat != null) {
			if(StrUtils.isEmpty(robotCallProcessStat.getId())) {
				//如果主键为空，那么新增一条信息
				robotCallProcessStat.setCrtTime(new Date());
				robotCallProcessStatMapper.insert(robotCallProcessStat);	//创建时间
			}else {
				//主键不为空，更新信息
				robotCallProcessStatMapper.updateByPrimaryKey(robotCallProcessStat);
			}
		}
		return robotCallProcessStat;
	}
	
}
