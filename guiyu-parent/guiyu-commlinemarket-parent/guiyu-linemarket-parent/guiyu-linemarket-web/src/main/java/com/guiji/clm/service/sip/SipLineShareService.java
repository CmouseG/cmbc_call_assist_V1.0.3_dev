package com.guiji.clm.service.sip;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.clm.dao.SipLineShareMapper;
import com.guiji.clm.dao.entity.SipLineShare;
import com.guiji.clm.dao.entity.SipLineShareExample;
import com.guiji.clm.dao.entity.SipLineShareExample.Criteria;
import com.guiji.clm.vo.SipLineShareQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 共享出去可代理的线路信息
* @Author: weiyunbo
* @date 2019年1月23日 上午10:29:39 
* @version V1.0  
*/
@Slf4j
@Service
public class SipLineShareService {
	@Autowired
	SipLineShareMapper sipLineShareMapper;
	
	
	/**
	 * 新增/更新共享的线路
	 * @param SipLineShare
	 * @return
	 */
	@Transactional
	public SipLineShare save(SipLineShare sipLineShare) {
		if(sipLineShare!=null) {
			if(sipLineShare.getId()!=null) {
				//更新
				sipLineShare.setUpdateTime(DateUtil.getCurrent4Time());
				sipLineShareMapper.updateByPrimaryKey(sipLineShare);
			}else {
				//新增
				sipLineShare.setApplyNum(0); //初始时共享线路-申请次数0
				sipLineShare.setCrtTime(DateUtil.getCurrent4Time());
				sipLineShare.setUpdateTime(DateUtil.getCurrent4Time());
				sipLineShareMapper.insert(sipLineShare);
			}
		}
		return sipLineShare;
	}
	
	
	/**
	 * 根据主键查询某条共享线路信息
	 * @param id
	 * @return
	 */
	public SipLineShare queryById(Integer id) {
		if(id!=null) {
			return sipLineShareMapper.selectByPrimaryKey(id);
		}
		return null;
	}
	
	/**
	 * 根据条件查询共享线路
	 * @param condition
	 * @return
	 */
	public List<SipLineShare> querySipShareListByCondition(SipLineShareQueryCondition condition){
		SipLineShareExample example = this.queryExample(condition);
		return sipLineShareMapper.selectByExample(example);
	}
	
	/**
	 * 根据 发布企业、线路行业、外显地区、呼叫方向、单价获取 共享出去的线路信息
	 * @param orgCode
	 * @param industrys
	 * @param overtArea
	 * @param callDirec
	 * @param univalent
	 * @return
	 */
	public SipLineShare querySipLineShareByUnit(String orgCode,String industrys,String overtArea,Integer callDirec,BigDecimal univalent) {
		SipLineShareExample example = new SipLineShareExample();
		example.createCriteria().andOrgCodeEqualTo(orgCode).andIndustrysEqualTo(industrys).andOvertAreaEqualTo(overtArea)
			.andCallDirecEqualTo(callDirec).andUnivalentEqualTo(univalent);
		List<SipLineShare> list = sipLineShareMapper.selectByExample(example);
		if(list!=null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据条件查询共享线路-分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public Page<SipLineShare> querySipShareForPageByCondition(SipLineShareQueryCondition condition){
		int pageNo = condition.getPageNo();
		int pageSize = condition.getPageSize();
		Page<SipLineShare> page = new Page<SipLineShare>();
		int totalRecord = 0;
		int limitStart = (pageNo-1)*pageSize;	//起始条数
		int limitEnd = pageSize;	//查询条数
		SipLineShareExample example = this.queryExample(condition);
		//查询总数
		totalRecord = sipLineShareMapper.countByExample(example);
		if(totalRecord > 0) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
			List<SipLineShare> list = sipLineShareMapper.selectByExample(example);
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
	private SipLineShareExample queryExample(SipLineShareQueryCondition condition) {
		SipLineShareExample example = new SipLineShareExample();
		if(condition != null) {
			Criteria criteria = example.createCriteria();
			if(condition.getLineStatus()!=null) {
				criteria.andLineStatusEqualTo(condition.getLineStatus());
			}
			if(StrUtils.isNotEmpty(condition.getIndustry())) {
				criteria.andIndustrysEqualTo(condition.getIndustry());
			}
			if(StrUtils.isNotEmpty(condition.getOrgCode())) {
				criteria.andOrgCodeEqualTo(condition.getOrgCode());
			}
		}
		example.setOrderByClause(" crt_time desc");
		return example;
	}
}
