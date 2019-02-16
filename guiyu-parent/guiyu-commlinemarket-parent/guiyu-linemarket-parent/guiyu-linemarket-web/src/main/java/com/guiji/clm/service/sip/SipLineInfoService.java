package com.guiji.clm.service.sip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.clm.dao.SipLineBaseInfoMapper;
import com.guiji.clm.dao.entity.SipLineBaseInfo;
import com.guiji.clm.dao.entity.SipLineBaseInfoExample;
import com.guiji.clm.dao.entity.SipLineBaseInfoExample.Criteria;
import com.guiji.clm.enm.SipLineStatusEnum;
import com.guiji.clm.vo.SipLineInfoQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 代理第三方线路服务
* @Author: weiyunbo
* @date 2019年1月23日 上午10:29:39 
* @version V1.0  
*/
@Slf4j
@Service
public class SipLineInfoService {
	@Autowired
	SipLineBaseInfoMapper sipLineBaseInfoMapper;
	
	
	/**
	 * 新增/更新申请审批记录
	 * @param SipLineBaseInfo
	 * @return
	 */
	@Transactional
	public SipLineBaseInfo save(SipLineBaseInfo sipLineBaseInfo) {
		if(sipLineBaseInfo!=null) {
			if(sipLineBaseInfo.getId()!=null) {
				//更新
				sipLineBaseInfo.setUpdateTime(DateUtil.getCurrent4Time());
				sipLineBaseInfoMapper.updateByPrimaryKey(sipLineBaseInfo);
			}else {
				//新增
				sipLineBaseInfo.setUseConcurrentCalls(0);	//初始已用并发数0
				sipLineBaseInfo.setLineStatus(SipLineStatusEnum.INIT.getCode()); //默认-线路未生效（初始化)
				sipLineBaseInfo.setCrtTime(DateUtil.getCurrent4Time());
				sipLineBaseInfo.setUpdateTime(DateUtil.getCurrent4Time());
				sipLineBaseInfoMapper.insert(sipLineBaseInfo);
			}
		}
		return sipLineBaseInfo;
	}
	
	/**
	 * 查询共享虚拟线路对应的SIP线路列表
	 * @param sipShareId
	 * @return
	 */
	public List<SipLineBaseInfo> queryByShareSipId(Integer sipShareId) {
		if(sipShareId!=null) {
			SipLineBaseInfoExample example = new SipLineBaseInfoExample();
			example.createCriteria().andSipShareIdEqualTo(sipShareId);
			return sipLineBaseInfoMapper.selectByExample(example);
		}
		return null;
	}

	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public void delete(Integer id) {
		if(id!=null) {
			sipLineBaseInfoMapper.deleteByPrimaryKey(id);
		}
	}
	
	
	/**
	 * 根据主键查询第三方sip线路
	 * @param id
	 * @return
	 */
	public SipLineBaseInfo queryById(Integer id) {
		if(id!=null) {
			return sipLineBaseInfoMapper.selectByPrimaryKey(id);
		}
		return null;
	}
	
	/**
	 * 根据条件查询第三方线路列表
	 * @param condition
	 * @return
	 */
	public List<SipLineBaseInfo> querySipLineBaseListByCondition(SipLineInfoQueryCondition condition){
		SipLineBaseInfoExample example = this.queryExample(condition);
		return sipLineBaseInfoMapper.selectByExample(example);
	}
	
	/**
	 * 根据条件查询第三方线路列表-分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public Page<SipLineBaseInfo> querySipLineBaseForPageByCondition(SipLineInfoQueryCondition condition){
		Page<SipLineBaseInfo> page = new Page<SipLineBaseInfo>();
		int totalRecord = 0;
		int pageNo = condition.getPageNo();
		int pageSize = condition.getPageSize();
		int limitStart = (pageNo-1)*pageSize;	//起始条数
		int limitEnd = pageSize;	//查询条数
		SipLineBaseInfoExample example = this.queryExample(condition);
		//查询总数
		totalRecord = sipLineBaseInfoMapper.countByExample(example);
		if(totalRecord > 0) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
			List<SipLineBaseInfo> list = sipLineBaseInfoMapper.selectByExample(example);
			page.setRecords(list);
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(totalRecord);
		return page;
	}
	
	
	/**
	 * 根据条件获取查询example
	 * @param condition
	 * @return
	 */
	private SipLineBaseInfoExample queryExample(SipLineInfoQueryCondition condition) {
		SipLineBaseInfoExample example = new SipLineBaseInfoExample();
		if(condition != null) {
			Criteria criteria = example.createCriteria();
			if(condition.getSipShareId()!=null) {
				criteria.andSipShareIdEqualTo(condition.getSipShareId());
			}
			if(StrUtils.isNotEmpty(condition.getLineName())) {
				criteria.andLineNameLike("%"+condition.getLineName()+"%");
			}
			if(StrUtils.isNotEmpty(condition.getCrtUser())) {
				criteria.andCrtUserEqualTo(condition.getCrtUser());
			}
			if(StrUtils.isNotEmpty(condition.getOrgCode())) {
				criteria.andOrgCodeEqualTo(condition.getOrgCode());
			}
			if(condition.getStatus()!=null) {
				criteria.andLineStatusEqualTo(condition.getStatus());
			}
			if(condition.getStatusList()!=null) {
				if(condition.getStatusList().size()==1) {
					criteria.andLineStatusEqualTo(condition.getStatusList().get(0));
				}else {
					criteria.andLineStatusIn(condition.getStatusList());
				}
			}
		}
		example.setOrderByClause(" crt_time desc");
		return example;
	}
}
