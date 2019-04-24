package com.guiji.sms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.PlatAddReq;
import com.guiji.sms.controller.bean.PlatListRsp;
import com.guiji.sms.controller.bean.PlatParamsRsp;
import com.guiji.sms.dao.SmsPlatformMapper;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsPlatformExample;
import com.guiji.sms.service.PlatformService;
import com.guiji.sms.utils.AuthUtil;
import com.guiji.utils.RedisUtil;

@Service
public class PlatformServiceImpl implements PlatformService
{
	@Autowired
	SmsPlatformMapper platformMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	AuthUtil authUtil;

	/**
	 * 获取短信平台列表
	 */
	@Override
	public PlatListRsp queryPlatformList(Condition condition, AuthLevelData authLevelData)
	{
		PlatListRsp rsp = new PlatListRsp();
		SmsPlatformExample example = new SmsPlatformExample();
		Integer authLevel = authLevelData.getAuthLevel();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(authLevelData.getUserId().intValue());
		} else if(authLevel == 2) {
			example.createCriteria().andOrgCodeEqualTo(authLevelData.getOrgCode());
		}else if(authLevel == 3) {
			example.createCriteria().andOrgCodeLike(authLevelData.getOrgCode() + "%");
		}
		rsp.setTotalNum(platformMapper.countByExample(example));
		example.setLimitStart((condition.getPageNum()-1)*condition.getPageSize());
		example.setLimitEnd(condition.getPageSize());
		example.setOrderByClause("id desc");
		rsp.setRecords(platformMapper.selectByExample(example));
		return rsp;
	}

	/**
	 * 新增短信平台
	 */
	@Override
	@Transactional
	public void addPlatform(PlatAddReq platAddReq, Long userId, String orgCode)
	{
		SmsPlatform platform = new SmsPlatform();
		String platformName = platAddReq.getPlatformName();
		isExistPlatformName(platformName); // 判断平台名称是否重复
		platform.setPlatformName(platformName);
		platform.setPlatformParams(platAddReq.getParams());
		platform.setIdentification(platAddReq.getIdentification());
		platform.setContentType(platAddReq.getContentType());
		platform.setOrgCode(orgCode);
		platform.setCreateId(userId.intValue());
		platform.setCreateName(authUtil.getUserNameByUserId(userId));
		platform.setCreateTime(new Date());
		platformMapper.insertSelective(platform);
		redisUtil.set(platform.getPlatformName(), platform);
	}

	/**
	 * 删除短信平台
	 */
	@Override
	@Transactional
	public void delPlatform(Integer id, String platformName)
	{
		platformMapper.deleteByPrimaryKey(id);
		redisUtil.del(platformName);
	}

	/**
	 * 获取所有的平台名称和对应的参数列表
	 */
	@Override
	public List<PlatParamsRsp> queryAllPlatNameWithParams()
	{
		List<PlatParamsRsp> platParamsRspList = new ArrayList<>();
		PlatParamsRsp platParamsRsp = null;
		SmsPlatformExample example = new SmsPlatformExample();
		List<SmsPlatform> platformList = platformMapper.selectByExample(example);
		for(SmsPlatform platform : platformList)
		{
			platParamsRsp = new PlatParamsRsp();
			platParamsRsp.setPlatformName(platform.getPlatformName());
			platParamsRsp.setContentType(platform.getContentType());
			platParamsRsp.setParams(Arrays.asList(platform.getPlatformParams().split("/")));
			platParamsRspList.add(platParamsRsp);
		}
		return platParamsRspList;
	}
	
	// 判断平台名称是否重复
	private void isExistPlatformName(String platformName)
	{
		SmsPlatformExample example = new SmsPlatformExample();
		example.createCriteria().andPlatformNameEqualTo(platformName);
		long count = platformMapper.countByExample(example);
		if(count > 0){
			throw new RuntimeException("此平台名称已存在，请您更换其他名称");
		}
	}

}
