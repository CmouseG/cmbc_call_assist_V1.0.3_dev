package com.guiji.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.service.PlatformService;
import com.guiji.sms.dao.SmsPlatformMapper;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsPlatformExample;
import com.guiji.sms.vo.PlatformListReqVO;
import com.guiji.sms.vo.PlatformListRspVO;
import com.guiji.sms.vo.PlatformReqVO;
import com.guiji.sms.vo.PlatformRspVO;
import com.guiji.sms.vo.SmsPlatformVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.NameUtil;
import com.guiji.utils.RedisUtil;

@Service
public class PlatformServiceImpl implements PlatformService
{
	@Autowired
	IAuth auth;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	SmsPlatformMapper platformMapper;
	
	/**
	 * 获取短信平台列表
	 */
	@Override
	public PlatformListRspVO getPlatformList(PlatformListReqVO platformListReq, Long userId)
	{
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(userId);
		
		PlatformListRspVO platformListRsp = new PlatformListRspVO();
		List<SmsPlatformVO> platformVOList = new ArrayList<>();

		SmsPlatformExample example = new SmsPlatformExample();
		example.createCriteria().andOrgCodeLike(sysOrganization.body.getCode()+"%");
		platformListRsp.setTotalCount(platformMapper.selectByExample(example).size()); // 总条数

		example.setLimitStart((platformListReq.getPageNum() - 1) * platformListReq.getPageSize());
		example.setLimitEnd(platformListReq.getPageSize());
		example.setOrderByClause("id desc");
		List<SmsPlatform> platformList = platformMapper.selectByExample(example);
		for(SmsPlatform platform : platformList)
		{
			SmsPlatformVO platformVO = setVoPrrams(platform);
			platformVOList.add(platformVO);
		}
		platformListRsp.setSmsPlatformVOList(platformVOList);

		return platformListRsp;
	}

	private SmsPlatformVO setVoPrrams(SmsPlatform platform)
	{
		SmsPlatformVO platformVO = new SmsPlatformVO();
		platformVO.setId(platform.getId());
		platformVO.setPlatformName(platform.getPlatformName());
		platformVO.setPlatformParams(platform.getPlatformParams());
		platformVO.setIdentification(platform.getIdentification());
		platformVO.setCreateUser(NameUtil.getUserName(platform.getCreateId().toString()));
		platformVO.setCreateTime(platform.getCreateTime());
		platformVO.setUpdateUser(NameUtil.getUserName(platform.getUpdateId().toString()));
		platformVO.setUpdateTime(platform.getUpdateTime());
		return platformVO;
	}

	/**
	 * 新增短信平台
	 */
	@Override
	@Transactional
	public void addPlatform(PlatformReqVO platformReq, Long userId)
	{
		SmsPlatform platform = setParams(platformReq,userId);
		platformMapper.insertSelective(platform);
		redisUtil.set(platform.getPlatformName(), platform);
	}
	
	/**
	 * 设置字段值
	 */
	private SmsPlatform setParams(PlatformReqVO platformReq, Long userId)
	{
		SmsPlatform platform = new SmsPlatform();
		platform.setPlatformName(platformReq.getPlatformName());
		platform.setPlatformParams(platformReq.getParams());
		platform.setIdentification(platformReq.getIdentification());
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(userId);
		platform.setCreateId(userId.intValue());
		platform.setCreateTime(new Date());
		platform.setUpdateId(userId.intValue());
		platform.setUpdateTime(new Date());
		platform.setOrgCode(sysOrganization.body.getCode());
		return platform;
	}

	/**
	 * 获取短信平台名称列表及参数
	 */
	@Override
	public List<PlatformRspVO> getPlatformNameListWithParams()
	{
		List<PlatformRspVO> platformRspList = new ArrayList<>();
		PlatformRspVO platformRsp;
		
		SmsPlatformExample example = new SmsPlatformExample();
		List<SmsPlatform> platformList = platformMapper.selectByExample(example);
		for(SmsPlatform platform : platformList){
			platformRsp = new PlatformRspVO(); // 实例化
			platformRsp.setPlatformName(platform.getPlatformName());
			platformRsp.setParams(Arrays.asList(platform.getPlatformParams().split("/")));
			platformRspList.add(platformRsp);
		}
		return platformRspList;
	}

	/**
	 * 删除短信平台
	 */
	@Override
	public void delPlatform(Integer id, String platName)
	{
		platformMapper.deleteByPrimaryKey(id);
		redisUtil.del(platName);
	}
}
