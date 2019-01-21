package com.guiji.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.service.TunnelService;
import com.guiji.sms.dao.SmsTunnelMapper;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.dao.entity.SmsTunnelExample;
import com.guiji.sms.vo.TunnelListReqVO;
import com.guiji.sms.vo.TunnelListRspVO;
import com.guiji.sms.vo.TunnelReqVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.JsonUtils;

@Service
public class TunnelServiceImpl implements TunnelService
{
	@Autowired
	SmsTunnelMapper tunnelMapper;
	@Autowired
	IAuth iAuth;
	
	/**
	 * 获取短信通道列表
	 */
	@Override
	public TunnelListRspVO getTunnelList(TunnelListReqVO tunnelListReq)
	{
		TunnelListRspVO tunnelListRsp = new TunnelListRspVO();
		SmsTunnelExample example = new SmsTunnelExample();
		tunnelListRsp.setTotalCount(tunnelMapper.selectByExampleWithBLOBs(example).size()); //总条数
		
		example.setLimitStart((tunnelListReq.getPageNum() - 1) * tunnelListReq.getPageSize());
		example.setLimitEnd(tunnelListReq.getPageSize());
		tunnelListRsp.setSmsTunnelList(tunnelMapper.selectByExampleWithBLOBs(example)); //分页返回的记录
		
		return tunnelListRsp;
	}

	/**
	 * 新增短信通道
	 */
	@Override
	public void addTunnel(TunnelReqVO tunnelReq, Long userId)
	{
		SmsTunnel smsTunnel = setParams(tunnelReq,userId);
		tunnelMapper.insertSelective(smsTunnel);
	}
	
	/**
	 * 设置字段值
	 */
	private SmsTunnel setParams(TunnelReqVO tunnelReq, Long userId)
	{
		SmsTunnel smsTunnel = new SmsTunnel();
		smsTunnel.setPlatformName(tunnelReq.getPlatformName());
		smsTunnel.setTunnelName(tunnelReq.getPlatformName() + "-" + tunnelReq.getTunnelName());
		smsTunnel.setPlatformConfig(JsonUtils.bean2Json(tunnelReq.getParams()));
		ReturnData<SysOrganization> sysOrganization = iAuth.getOrgByUserId(userId);
		smsTunnel.setCompanyId(sysOrganization.body.getId().intValue());
		smsTunnel.setCompanyName(sysOrganization.body.getName());
		smsTunnel.setCreateId(userId.intValue());
		smsTunnel.setCreateTime(new Date());
		smsTunnel.setUpdateId(userId.intValue());
		smsTunnel.setUpdateTime(new Date());
		return smsTunnel;
	}

	/**
	 * 删除短信通道
	 */
	@Override
	public void delTunnel(Integer id)
	{
		tunnelMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取短信通道名称列表
	 */
	@Override
	public List<String> getTunnelNameList()
	{
		List<String> tunnelNameList = new ArrayList<>();
		
		SmsTunnelExample example = new SmsTunnelExample();
		List<SmsTunnel> tunnelList = tunnelMapper.selectByExample(example);
		for(SmsTunnel tunnel : tunnelList){
			tunnelNameList.add(tunnel.getTunnelName());
		}
		
		return tunnelNameList;
	}
	
}
