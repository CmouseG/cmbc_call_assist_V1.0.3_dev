package com.guiji.da.service.impl.robot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.da.dao.CustPreInfoMapper;
import com.guiji.da.dao.entity.CustPreInfo;
import com.guiji.da.dao.entity.CustPreInfoExample;
import com.guiji.da.service.robot.ICustPreInfoService;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 客户信息
* @Author: weiyunbo
* @date 2019年1月3日 上午9:40:26 
* @version V1.0  
*/
@Slf4j
@Service
public class CustPreInfoServiceImpl implements ICustPreInfoService{
	@Autowired
	CustPreInfoMapper custPreInfoMapper;
	
	/**
	 * 新增/更新通话详情
	 * @param RobotCallDetail
	 * @return
	 */
	public CustPreInfo save(CustPreInfo custPreInfo) {
		if(custPreInfo!=null) {
			if(custPreInfo.getId()!=null) {
				//更新
				custPreInfo.setUpdateTime(DateUtil.getCurrent4Time());
				custPreInfoMapper.updateByPrimaryKey(custPreInfo);
			}else {
				//新增
				custPreInfo.setCrtDate(DateUtil.getCurrentymd());
				custPreInfo.setCrtTime(DateUtil.getCurrent4Time());
				custPreInfo.setUpdateTime(DateUtil.getCurrent4Time());
				int id = custPreInfoMapper.insert(custPreInfo);
				custPreInfo.setId(id);
			}
		}
		return custPreInfo;
	}
	
	/**
	 * 根据主键ID查询某个通话详细
	 * @param id
	 * @return
	 */
	public CustPreInfo queryCustPreInfoById(int id) {
		return custPreInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据手机号查询用户
	 * @param phoneNo
	 * @return
	 */
	public CustPreInfo queryCustPreInfoByPhone(String phoneNo) {
		if(StrUtils.isNotEmpty(phoneNo)) {
			CustPreInfoExample example = new CustPreInfoExample();
			example.createCriteria().andPhoneEqualTo(phoneNo);
			List<CustPreInfo> list = custPreInfoMapper.selectByExample(example);
			if(list!=null && !list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}
}
