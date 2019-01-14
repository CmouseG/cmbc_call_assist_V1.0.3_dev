package com.guiji.da.service.impl.robot;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.da.dao.RobotCallHisMapper;
import com.guiji.da.dao.RobotCallProcessStatMapper;
import com.guiji.da.dao.entity.CustPreInfo;
import com.guiji.da.dao.entity.RobotCallDetail;
import com.guiji.da.dao.entity.RobotCallHis;
import com.guiji.da.dao.entity.RobotCallHisExample;
import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.da.service.robot.ICustPreInfoService;
import com.guiji.da.service.robot.IRobotCallCallDetailService;
import com.guiji.da.service.vo.RotbotFdCallback;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.LocalCacheUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiNewTransService 
* @Description: 独立事务服务类，在一个service中独立另一个事务同个service没有效果的，此处单拉一个service用来处理这个问题
* @date 2018年12月6日 下午4:51:17 
* @version V1.0  
*/
@Service
public class RobotNewTransService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RobotCallHisMapper robotCallHisMapper; 
	@Autowired
	RobotCallProcessStatMapper robotCallProcessStatMapper; 
	@Autowired
	IAuth iAuth;
	@Autowired
	ICustPreInfoService iCustPreInfoService;
	@Autowired
	IRobotCallCallDetailService iRobotCallCallDetailService;
	
	/**
	 * 保存或更新一个通话记录
	 * 独立事物
	 * @param ttsWavHis
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public RobotCallHis recordRobotCallHis(RobotCallHis robotCallHis) {
		return this.recordRobotCallHisNon(robotCallHis);
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
	
	/**
	 * 保存飞龙回调的数据,并返回保存过程中查询的通话基本信息
	 * @param rotbotFdCallback
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public RobotCallHis recordFdCallback(RotbotFdCallback rotbotFdCallback) {
		if(rotbotFdCallback != null) {
			RobotCallHis robotCallHis = null;
			CustPreInfo cust = rotbotFdCallback.getCust();	//返回的callback客户信息
			List<RobotCallDetail> details = rotbotFdCallback.getDetails();	//返回callback的通话明细
			RobotCallHis base = rotbotFdCallback.getBase();	//返回的callback基本信息
			if(base != null) {
				String seqId = base.getSeqId();	//会话ID
				robotCallHis = this.queryRobotCallHis(seqId);
				if(robotCallHis!=null) {
					BeanUtil.copyProperties(cust, robotCallHis);
				}else {
					logger.error("根据seqid:{}未找到已经落地的数据",seqId);
					robotCallHis = base;
				}
				//1、保存/更新通话历史
				this.recordRobotCallHisNon(robotCallHis);
			}
			if(cust!=null) {
				//2、保存客户：暂时直接保存，后续可以根据手机号或者其他条件识别同一个用户更新
				cust.setSeqId(robotCallHis.getSeqId());
				cust.setPhone(robotCallHis.getPhoneNo());
				iCustPreInfoService.save(cust);
			}
			//3、保存通话明细
			if(details!=null && !details.isEmpty()) {
				for(RobotCallDetail detail : details) {
					iRobotCallCallDetailService.save(detail);
				}
			}
			return robotCallHis;
		}
		return null;
	}
	
	/**
	 * 根据会话id查询通话记录
	 * @param seqId
	 * @return
	 */
	private RobotCallHis queryRobotCallHis(String seqId) {
		RobotCallHisExample example = new RobotCallHisExample();
		example.createCriteria().andSeqIdEqualTo(seqId);
		List<RobotCallHis> list = robotCallHisMapper.selectByExample(example);
		if(list!=null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 保存呼叫历史（非独立事务）
	 * @param robotCallHis
	 * @return
	 */
	private RobotCallHis recordRobotCallHisNon(RobotCallHis robotCallHis) {
		if(robotCallHis != null) {
			try {
				if(StrUtils.isEmpty(robotCallHis.getId())) {
					//如果主键为空，那么新增一条信息
					//查询用户机构信息
					if(StrUtils.isNotEmpty(robotCallHis.getUserId())) {
						//从缓存中获取
						SysUser sysUser = LocalCacheUtil.getT(robotCallHis.getUserId());
						if(sysUser!=null) {
							//缓存中有，直接取
							robotCallHis.setOrgCode(sysUser.getOrgCode());
						}else{
							//缓存中没有,重新查，并放入内存
							ReturnData<SysUser> userRD = iAuth.getUserById(Long.valueOf(robotCallHis.getUserId()));
							if(userRD != null && userRD.getBody()!=null) {
								//内存1个小时有效
								LocalCacheUtil.set(robotCallHis.getUserId(), userRD.getBody(), LocalCacheUtil.ONE_HOUR);
								robotCallHis.setOrgCode(userRD.getBody().getOrgCode());
							}else {
								logger.error("用户ID:{},查询不到用户信息，返回：",robotCallHis.getUserId(),userRD);
							}
						}
					}
					robotCallHis.setCrtTime(new Date());
					robotCallHis.setCrtDate(DateUtil.getCurrentymd()); //创建日期 yyyy-MM-dd
					int id = robotCallHisMapper.insert(robotCallHis);	//创建时间
					robotCallHis.setId(id);
				}else {
					//主键不为空，更新信息
					robotCallHisMapper.updateByPrimaryKey(robotCallHis);
				}
			} catch (Exception e) {
				//不抛出异常,不能影响正常通话
				logger.error("保存通话"+robotCallHis.getSeqId()+"记录发生异常",e);
			}
		}
		return robotCallHis;
	}
	
}
