package com.guiji.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.service.TunnelService;
import com.guiji.sms.dao.SmsTunnelMapper;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.dao.entity.SmsTunnelExample;
import com.guiji.sms.vo.SmsTunnelVO;
import com.guiji.sms.vo.TunnelListReqVO;
import com.guiji.sms.vo.TunnelListRspVO;
import com.guiji.sms.vo.TunnelReqVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.LocalCacheUtil;
import com.guiji.utils.RedisUtil;

@Service
public class TunnelServiceImpl implements TunnelService
{
	@Autowired
	IAuth auth;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	SmsTunnelMapper tunnelMapper;
	
	/**
	 * 获取短信通道列表
	 */
	@Override
	public TunnelListRspVO getTunnelList(TunnelListReqVO tunnelListReq, Long userId)
	{
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(userId);
		TunnelListRspVO tunnelListRsp = new TunnelListRspVO();
		List<SmsTunnelVO> tunnelVOList = new ArrayList<>();
		
		SmsTunnelExample example = new SmsTunnelExample();
		example.createCriteria().andOrgCodeLike(sysOrganization.body.getCode()+"%");
		tunnelListRsp.setTotalCount(tunnelMapper.selectByExampleWithBLOBs(example).size()); //总条数
		
		example.setLimitStart((tunnelListReq.getPageNum() - 1) * tunnelListReq.getPageSize());
		example.setLimitEnd(tunnelListReq.getPageSize());
		example.setOrderByClause("id desc");
		List<SmsTunnel> tunnelList = tunnelMapper.selectByExampleWithBLOBs(example); //分页返回的记录
		for(SmsTunnel tunnel : tunnelList)
		{
			SmsTunnelVO tunnelVO = setVoPrrams(tunnel);
			tunnelVOList.add(tunnelVO);
		}
		tunnelListRsp.setSmsTunnelVOList(tunnelVOList);
		
		return tunnelListRsp;
	}
	
	private SmsTunnelVO setVoPrrams(SmsTunnel tunnel)
	{
		SmsTunnelVO tunnelVO = new SmsTunnelVO();
		tunnelVO.setId(tunnel.getId());
		tunnelVO.setPlatformName(tunnel.getPlatformName());
		tunnelVO.setTunnelName(tunnel.getTunnelName());
		tunnelVO.setCompanyName(tunnel.getCompanyName());
		tunnelVO.setCreateUser(getUserName(tunnel.getCreateId().toString()));
		tunnelVO.setCreateTime(tunnel.getCreateTime());
		tunnelVO.setUpdateUser(getUserName(tunnel.getUpdateId().toString()));
		tunnelVO.setUpdateTime(tunnel.getUpdateTime());
		tunnelVO.setPlatformConfig(tunnel.getPlatformConfig());
		return tunnelVO;
	}



	/**
	 * 新增短信通道
	 */
	@Override
	public void addTunnel(TunnelReqVO tunnelReq, Long userId)
	{
		SmsTunnel smsTunnel = setParams(tunnelReq,userId);
		tunnelMapper.insertSelective(smsTunnel);
		redisUtil.set(smsTunnel.getTunnelName(), smsTunnel);
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
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(userId);
		smsTunnel.setCompanyId(sysOrganization.body.getId().intValue());
		smsTunnel.setCompanyName(sysOrganization.body.getName());
		smsTunnel.setCreateId(userId.intValue());
		smsTunnel.setCreateTime(new Date());
		smsTunnel.setUpdateId(userId.intValue());
		smsTunnel.setUpdateTime(new Date());
		smsTunnel.setOrgCode(sysOrganization.body.getCode());
		return smsTunnel;
	}

	/**
	 * 删除短信通道
	 */
	@Override
	public void delTunnel(Integer id, String tunnelName)
	{
		tunnelMapper.deleteByPrimaryKey(id);
		redisUtil.del(tunnelName);
	}

	/**
	 * 获取短信通道名称列表
	 */
	@Override
	public List<String> getTunnelNameList(Long userId)
	{
		List<String> tunnelNameList = new ArrayList<>();
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(userId);
		SmsTunnelExample example = new SmsTunnelExample();
		example.createCriteria().andOrgCodeLike(sysOrganization.body.getCode()+"%");
		List<SmsTunnel> tunnelList = tunnelMapper.selectByExample(example);
		for(SmsTunnel tunnel : tunnelList){
			tunnelNameList.add(tunnel.getTunnelName());
		}
		return tunnelNameList;
	}
	
	public String getUserName(String userId) {
        String cacheName = LocalCacheUtil.getT(userId);
        if (cacheName != null) {
            return cacheName;
        } else {
            try {
                Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
                if(result!=null && result.getBody()!=null) {
                    String userName = result.getBody().getUsername();
                    if (userName != null) {
                    	LocalCacheUtil.set(userId, userName, LocalCacheUtil.HARF_HOUR);
                        return userName;
                    }
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return "";
    }
}
