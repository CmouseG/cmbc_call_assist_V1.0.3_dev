package com.guiji.dispatch.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.dispatch.dao.PhoneRegionErrorMapper;
import com.guiji.dispatch.dao.PhoneRegionMapper;
import com.guiji.dispatch.dao.entity.PhoneRegion;
import com.guiji.dispatch.dao.entity.PhoneRegionError;
import com.guiji.dispatch.dao.entity.PhoneRegionExample;
import com.guiji.dispatch.service.IPhoneRegionService;
import com.guiji.dispatch.util.MobileFromUtil;
import com.guiji.dispatch.util.MobileFromUtil.MobileOwnerRsp;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 手机号归属地服务
* @Author: weiyunbo
* @date 2019年1月30日 下午6:35:23 
* @version V1.0  
*/
@Slf4j
@Service
public class PhoneRegionServiceImpl implements IPhoneRegionService{
	private static final String PHONE_REGION_CACHE = "PHONE_REGION";	//redis key
	@Autowired
	PhoneRegionMapper phoneRegionMapper;
	@Autowired
	PhoneRegionErrorMapper phoneRegionErrorMapper;
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 获取手机号归属地
	 * 1、先从redis中获取
	 * 2、从DB获取（防止存量数据redis中没有）
	 * 3、到第三方查询
	 * @param phoneNo
	 * @return
	 */
	public String queryPhoneRegion(String phoneNo) {
		if(StrUtils.isNotEmpty(phoneNo)) {
			String phone7 = phoneNo.substring(0, 7); //手机号7位
			//1、先从redis中获取
			String region = this.queryCacheRegionByPhone7(phone7);
			if(StrUtils.isEmpty(region)) {
				//2、从本地db查询
				log.error("手机号{}归属地查询,缓存中查不到,开始到本地DB中查询",phone7);
				region = this.queryDbRegionByPhone7(phone7);
				if(StrUtils.isEmpty(region)) {
					//3、从第三方查询
					log.error("手机号{}归属地查询,本地DB中查不到,开始到第三方查询",phone7);
					MobileFromUtil mobileFromUtil = new MobileFromUtil();
					MobileOwnerRsp rsp = mobileFromUtil.getPhoneFrom(phoneNo);
					if(rsp!=null && StrUtils.isNotEmpty(rsp.getOwner())) {
						//第三方查到了，放入db/redis
						region = rsp.getOwner();
						PhoneRegion phoneRegion = new PhoneRegion();
						phoneRegion.setPhone7(phone7);
						phoneRegion.setRegionName(region);
						this.save(phoneRegion);
						//放入redis
						Map<String,Object> map = new HashMap<String,Object>();
						map.put(phone7, region);
						//提交到redis
						redisUtil.hmset(PHONE_REGION_CACHE, map);
					}else {
						log.error("手机号{}归属地查询,第三方也查不到,返回异常。。",phoneNo);
						this.saveError(phoneNo);
					}
				}else {
					//本地查询不为空，放入redis
					Map<String,Object> map = new HashMap<String,Object>();
					map.put(phone7, region);
					//提交到redis
					redisUtil.hmset(PHONE_REGION_CACHE, map);
				}
			}
			return region;
		}
		return null;
	}
	
	/**
	 * 新增/更新手机号归属地
	 * @param phoneRegion
	 * @return
	 */
	private PhoneRegion save(PhoneRegion phoneRegion) {
		if(phoneRegion!=null) {
			if(phoneRegion.getId()!=null) {
				//更新
				phoneRegionMapper.updateByPrimaryKey(phoneRegion);
			}else {
				//新增
				phoneRegion.setCrtTime(DateUtil.getCurrent4Time());
				phoneRegionMapper.insert(phoneRegion);
			}
		}
		return phoneRegion;
	}
	
	
	/**
	 * 手机号归属地异常记录
	 * @param phoneNo
	 * @return
	 */
	@Transactional
	private PhoneRegionError saveError(String phoneNo) {
		PhoneRegionError record = new PhoneRegionError();
		record.setPhone(phoneNo);
		record.setCrtTime(DateUtil.getCurrent4Time());
		phoneRegionErrorMapper.insert(record);
		return record;
	}
	
	
	
	/**
	 * 查询本地DB数据
	 * @return
	 */
	private String queryDbRegionByPhone7(String phone7) {
		PhoneRegionExample example = new PhoneRegionExample();
		example.createCriteria().andPhone7EqualTo(phone7);
		List<PhoneRegion> list = phoneRegionMapper.selectByExample(example);
		if(list!=null && !list.isEmpty()) {
			return list.get(0).getRegionName();
		}
		return null;
	}
	
	/**
	 * 从redis缓存中获取手机号地区
	 * @param phone7
	 * @return
	 */
	private String queryCacheRegionByPhone7(String phone7) {
		Object cacheObj = redisUtil.hget(PHONE_REGION_CACHE, phone7);
		if(cacheObj!=null){
			return (String)cacheObj;
		}else {
			return null;
		}
	}
	
}
