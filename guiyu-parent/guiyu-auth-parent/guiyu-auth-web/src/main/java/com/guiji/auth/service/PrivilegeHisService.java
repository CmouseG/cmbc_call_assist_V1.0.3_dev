package com.guiji.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.user.dao.SysPrivilegeHisMapper;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysPrivilegeHis;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;

/** 
* @ClassName: PrivilegeHisService 
* @Description: 权限删除历史记录
* @auth weiyunbo
* @date 2019年3月10日 下午4:11:15 
* @version V1.0  
*/
@Service
public class PrivilegeHisService {

	@Autowired
	SysPrivilegeHisMapper sysPrivilegeHisMapper;
	
	/**
	 * 新增/更新产品
	 * @param sysProduct
	 * @return
	 */
	@Async
	@Transactional
	public SysPrivilegeHis save(Integer doUser,SysPrivilege sysPrivilege) {
		if(sysPrivilege!=null) {
			SysPrivilegeHis his = new SysPrivilegeHis();
			BeanUtil.copyProperties(sysPrivilege, his);
			his.setPrivilegeId(sysPrivilege.getId());
			his.setDoUser(doUser);
			his.setDoTime(DateUtil.getCurrent4Time());
			sysPrivilegeHisMapper.insert(his);
			return his;
		}
		return null;
	}
}
