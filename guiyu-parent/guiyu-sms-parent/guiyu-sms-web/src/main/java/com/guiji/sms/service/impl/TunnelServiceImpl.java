package com.guiji.sms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.TunnelAddReq;
import com.guiji.sms.controller.bean.TunnelListRsp;
import com.guiji.sms.controller.bean.TunnelParamsRsp;
import com.guiji.sms.dao.SmsTunnelMapper;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.dao.entity.SmsTunnelExample;
import com.guiji.sms.service.TunnerlService;
import com.guiji.sms.utils.AuthUtil;
import com.guiji.sms.utils.JsonUtil;
import com.guiji.utils.RedisUtil;

@Service
public class TunnelServiceImpl implements TunnerlService
{
	@Autowired
	SmsTunnelMapper tunnelMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	AuthUtil authUtil;
	
	/**
	 * 获取短信通道列表
	 */
	@Override
	public TunnelListRsp queryTunnelList(Condition condition, AuthLevelData authLevelData)
	{
		TunnelListRsp rsp = new TunnelListRsp();
		SmsTunnelExample example = new SmsTunnelExample();
		Integer authLevel = authLevelData.getAuthLevel();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(authLevelData.getUserId().intValue());
		} else if(authLevel == 2) {
			example.createCriteria().andOrgCodeEqualTo(authLevelData.getOrgCode());
		}else if(authLevel == 3) {
			example.createCriteria().andOrgCodeLike(authLevelData.getOrgCode() + "%");
		}
		rsp.setTotalNum(tunnelMapper.countByExample(example));
		example.setLimitStart((condition.getPageNum()-1)*condition.getPageSize());
		example.setLimitEnd(condition.getPageSize());
		example.setOrderByClause("id desc");
		rsp.setRecords(tunnelMapper.selectByExampleWithBLOBs(example));
		return rsp;
	}

	/**
	 * 新增短信通道
	 */
	@Override
	@Transactional
	public void addTunnel(TunnelAddReq tunnelAddReq, Long userId, String orgCode)
	{
		SmsTunnel tunnel = new SmsTunnel();
		String platformName = tunnelAddReq.getPlatformName();
		String tunnelName = tunnelAddReq.getTunnelName();
		isExistTunnelName(platformName, tunnelName); // 判断通道名称是否重复
		tunnel.setPlatformName(platformName);
		tunnel.setTunnelName(platformName+"-"+tunnelName);
		tunnel.setContentType(tunnelAddReq.getContentType());
		tunnel.setOrgCode(orgCode);
		tunnel.setOrgName(authUtil.getOrgNameByOrgCode(orgCode));
		tunnel.setCreateId(userId.intValue());
		tunnel.setCreateName(authUtil.getUserNameByUserId(userId));
		tunnel.setCreateTime(new Date());
		tunnel.setPlatformConfig(JsonUtil.bean2JsonStr(tunnelAddReq.getParams()));
		tunnelMapper.insertSelective(tunnel);
		redisUtil.set(tunnel.getTunnelName(), tunnel);
	}

	/**
	 * 删除短信通道
	 */
	@Override
	@Transactional
	public void delTunnel(Integer id, String tunnelName)
	{
		tunnelMapper.deleteByPrimaryKey(id);
		redisUtil.del(tunnelName);
	}

	/**
	 * 获取所有的通道名称和内容形式
	 */
	@Override
	public List<TunnelParamsRsp> queryAllTunnelNameWithContentType(AuthLevelData authLevelData)
	{
		List<TunnelParamsRsp> rspList = new ArrayList<>();
		SmsTunnelExample example = new SmsTunnelExample();
		Integer authLevel = authLevelData.getAuthLevel();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(authLevelData.getUserId().intValue());
		} else if(authLevel == 2) {
			example.createCriteria().andOrgCodeEqualTo(authLevelData.getOrgCode());
		}else if(authLevel == 3) {
			example.createCriteria().andOrgCodeLike(authLevelData.getOrgCode() + "%");
		}
		List<SmsTunnel> tunnelList = tunnelMapper.selectByExampleWithBLOBs(example);
		for(SmsTunnel tunnel : tunnelList){
			TunnelParamsRsp rsp = new TunnelParamsRsp();
			rsp.setTunnelName(tunnel.getTunnelName());
			rsp.setContentType(tunnel.getContentType());
			rspList.add(rsp);
		}
		return rspList;
	}
	
	// 判断通道名称是否重复
	private void isExistTunnelName(String platformName, String tunnelName)
	{
		SmsTunnelExample example = new SmsTunnelExample();
		example.createCriteria().andTunnelNameEqualTo(platformName+"-"+tunnelName);
		long count = tunnelMapper.countByExample(example);
		if(count > 0){
			throw new RuntimeException("此通道名称已存在，请您更换其他名称");
		}
	}

}
