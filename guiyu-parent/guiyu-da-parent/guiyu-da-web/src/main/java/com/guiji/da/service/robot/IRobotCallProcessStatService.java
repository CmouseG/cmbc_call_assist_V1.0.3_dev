package com.guiji.da.service.robot;

import java.util.List;

import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.da.service.vo.RobotCallProcessStatCache;
import com.guiji.da.service.vo.RobotCallProcessStatQueryCondition;

/** 
* @ClassName: IRobotCallProcessStatService 
* @Description: 机器人统计分析
* @date 2018年12月6日 下午3:00:17 
* @version V1.0  
*/
public interface IRobotCallProcessStatService {
	
	/**
	 * 保存或者更新一TTS合成信息
	 * 同时记录历史
	 * @param ttsWavHis
	 * @return
	 */
	RobotCallProcessStat saveOrUpdate(RobotCallProcessStat robotCallProcessStat);
	
	
	/**
	 * 根据条件查询统计分析数据(汇总过的数据)
	 * @param condition
	 * @return
	 */
	List<RobotCallProcessStatCache> queryRobotCallProcessStatByCondition(RobotCallProcessStatQueryCondition condition);
}
