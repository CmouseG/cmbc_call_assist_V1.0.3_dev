package com.guiji.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.BussinessService;
import com.guiji.user.dao.entity.SysBusiness;

@RestController
@RequestMapping("bussiness")
public class BussinessController
{
	@Autowired
	private BussinessService bussinessService;
	
	/**
	 * 新增商务
	 */
	@RequestMapping("addBussiness")
	public void addBussiness(String businessName, String businessEmail,
			@RequestHeader Long userId,
			@RequestHeader String orgCode)
	{
		bussinessService.addBussiness(businessName,businessEmail,userId,orgCode);
	}
	
	/**
	 * 查询商务列表
	 */
	@RequestMapping("queryBusinessList")
	public List<SysBusiness> queryBusinessList(@RequestHeader Long userId, @RequestHeader Integer authLevel, @RequestHeader String orgCode)
	{
		return bussinessService.queryBusinessList(userId,orgCode,authLevel);
	}
	
	/**
	 * 删除商务
	 */
	@RequestMapping("delBusiness")
	public void delBusiness(Integer id)
	{
		bussinessService.delBusiness(id);
	}
}
