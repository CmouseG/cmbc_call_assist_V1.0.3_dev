package com.guiji.clm.service.voip;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.clm.dao.VoipGwInfoMapper;
import com.guiji.clm.dao.entity.VoipGwInfo;
import com.guiji.clm.dao.entity.VoipGwInfoExample;
import com.guiji.clm.dao.entity.VoipGwInfoExample.Criteria;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.enm.VoipGwStatusEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.vo.VoipGwQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 语音网关配置
* @Author: weiyunbo
* @date 2019年1月23日 上午10:32:19 
* @version V1.0  
*/
@Slf4j
@Service
public class VoipGwService {
	@Autowired
	VoipGwInfoMapper voipGwInfoMapper;
	@Autowired
	VoipGwPortService voipGwPortService;
	
	
	/**
	 * 新增/更新申请审批记录
	 * @param VoipGwInfo
	 * @return
	 */
	public VoipGwInfo save(VoipGwInfo voipGwInfo) {
		if(voipGwInfo!=null) {
			String gwName = voipGwInfo.getGwName();	//网关名称
			//根据网关名称查询
			VoipGwInfo existGw = this.queryByGwName(gwName);
			if(voipGwInfo.getId()!=null) {
				if(existGw!=null && !voipGwInfo.getId().equals(existGw.getId())) {
					//网关名称不能重复
					log.error("本次网关名称：{},重复的网关ID：{}",gwName,existGw.getId());
					throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
				}
				//更新
				voipGwInfo.setUpdateTime(DateUtil.getCurrent4Time());
				voipGwInfoMapper.updateByPrimaryKey(voipGwInfo);
			}else {
				//新增
				if(existGw!=null) {
					//网关名称不能重复
					log.error("本次网关名称：{},重复的网关ID：{}",gwName,existGw.getId());
					throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
				}
				voipGwInfo.setGwStatus(VoipGwStatusEnum.OK.getCode()); //初始正常状态
				voipGwInfo.setCrtTime(DateUtil.getCurrent4Time());
				voipGwInfo.setUpdateTime(DateUtil.getCurrent4Time());
				voipGwInfoMapper.insert(voipGwInfo);
			}
		}
		return voipGwInfo;
	}
	
	
	/**
	 * 根据主键查询网关端口信息
	 * @param id
	 * @return
	 */
	public VoipGwInfo queryById(Integer id) {
		if(id!=null) {
			return voipGwInfoMapper.selectByPrimaryKey(id);
		}
		return null;
	}
	
	/**
	 * 根据网关名称查询网关信息
	 * 因为语音网关硬件设备上配置信息时只能拿描述信息和业务系统中的网关绑定，所以我们定义网关名称为唯一对应关系，所以不能重复
	 * @param gwName
	 * @return
	 */
	public VoipGwInfo queryByGwName(String gwName) {
		if(StrUtils.isNotEmpty(gwName)) {
			VoipGwInfoExample example = new VoipGwInfoExample();
			example.createCriteria().andGwNameEqualTo(gwName);
			List<VoipGwInfo> list = voipGwInfoMapper.selectByExample(example);
			if(list!=null && !list.isEmpty()){
				return list.get(0);
			}
		}
		return null;
	}
	
	
	/**
	 * 根据条件查询网关列表
	 * @param condition
	 * @return
	 */
	public List<VoipGwInfo> queryVoipGwInfoList(VoipGwQueryCondition condition){
		VoipGwInfoExample example = this.queryExample(condition);
		return voipGwInfoMapper.selectByExample(example);
	}
	
	/**
	 * 根据条件查询网关列表-分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public Page<VoipGwInfo> queryVoipGwInfoForPageByCondition(int pageNo, int pageSize,VoipGwQueryCondition condition){
		Page<VoipGwInfo> page = new Page<VoipGwInfo>();
		int totalRecord = 0;
		int limitStart = (pageNo-1)*pageSize;	//起始条数
		int limitEnd = pageSize;	//查询条数
		VoipGwInfoExample example = this.queryExample(condition);
		//查询总数
		totalRecord = voipGwInfoMapper.countByExample(example);
		if(totalRecord > 0) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
			List<VoipGwInfo> list = voipGwInfoMapper.selectByExample(example);
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
	private VoipGwInfoExample queryExample(VoipGwQueryCondition condition) {
		VoipGwInfoExample example = new VoipGwInfoExample();
		if(condition != null) {
			Criteria criteria = example.createCriteria();
			if(StrUtils.isNotEmpty(condition.getSipIp())) {
				criteria.andSipIpEqualTo(condition.getSipIp());
			}
			if(StrUtils.isNotEmpty(condition.getGwName())) {
				criteria.andGwNameLike("%"+condition.getGwName()+"%");
			}
			if(StrUtils.isNotEmpty(condition.getGwBrand())) {
				criteria.andGwBrandLike("%"+condition.getGwBrand()+"%");
			}
			if(StrUtils.isNotEmpty(condition.getOrgCode())) {
				criteria.andOrgCodeEqualTo(condition.getOrgCode());
			}
			if(condition.getGwStatus()!=null && !condition.getGwStatus().isEmpty()) {
				if(condition.getGwStatus().size()==1) {
					criteria.andGwStatusEqualTo(condition.getGwStatus().get(0));
				}else {
					criteria.andGwStatusIn(condition.getGwStatus());
				}
			}
		}
		example.setOrderByClause(" crt_time desc");
		return example;
	}
	
	
	/**
	 * 初始化语音网关端口信息
	 * @param VoipGwInfo
	 * @return
	 */
	public List<VoipGwPort> initVoipGwPort(VoipGwInfo voipGwInfo){
		List<VoipGwPort> list = new ArrayList<VoipGwPort>();
		int portNum = voipGwInfo.getPortNum();
		for(int i=1;i<=portNum;i++){
			VoipGwPort port = new VoipGwPort();
			//属性拷贝
			BeanUtil.copyProperties(voipGwInfo, port, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).setIgnoreProperties("id","userId","orgCode"));
			port.setPort(i);	//端口编号
			port.setGwId(voipGwInfo.getId()); //网关id
			port.setSipAccount(voipGwInfo.getStartSipAccount() + (i-1)*voipGwInfo.getSipAccountStep());	//端口账号
			port.setSipPwd(voipGwInfo.getStartSipPwd() + (i-1)*voipGwInfo.getSipPwdStep()); //端口sip密码
			list.add(port);
			voipGwPortService.save(port);
		}
		return list;
	}
	
}
