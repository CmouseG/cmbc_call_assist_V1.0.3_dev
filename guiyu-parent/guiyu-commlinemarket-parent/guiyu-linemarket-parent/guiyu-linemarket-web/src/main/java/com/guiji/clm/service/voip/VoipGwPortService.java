package com.guiji.clm.service.voip;

import java.util.List;

import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.clm.dao.VoipGwPortMapper;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.dao.entity.VoipGwPortExample;
import com.guiji.clm.dao.entity.VoipGwPortExample.Criteria;
import com.guiji.clm.enm.VoipGwStatusEnum;
import com.guiji.clm.vo.VoipGwPortQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 语音网关端口配置服务
* @Author: weiyunbo
* @date 2019年1月23日 上午10:32:41 
* @version V1.0  
*/
@Slf4j
@Service
public class VoipGwPortService {
	@Autowired
	VoipGwPortMapper voipGwPortMapper;
	@Autowired
	VoipGwPortHisHisService voipGwPortHisHisService;
	@Autowired
	LineService lineService;
	
	/**
	 * 新增/更新申请审批记录
	 * @param VoipGwPort
	 * @return
	 */
	public VoipGwPort save(VoipGwPort voipGwPort) {
		if(voipGwPort!=null) {
			if(voipGwPort.getId()!=null) {
				//更新
				voipGwPort.setUpdateTime(DateUtil.getCurrent4Time());
				voipGwPortMapper.updateByPrimaryKey(voipGwPort);

			}else {
				//新增
				voipGwPort.setGwStatus(VoipGwStatusEnum.OK.getCode()); //初始正常状态
				voipGwPort.setCrtTime(DateUtil.getCurrent4Time());
				voipGwPort.setUpdateTime(DateUtil.getCurrent4Time());
				voipGwPortMapper.insert(voipGwPort);
			}

			if(null == voipGwPort.getUserId()) {
				lineService.updateLineInfo(voipGwPort, ClmConstants.DEL);
			} else {
				lineService.updateLineInfo(voipGwPort, ClmConstants.UPDATE);
			}

			//异步记录变更历史
			voipGwPortHisHisService.asyncSaveGwPortHis(voipGwPort);
		}
		return voipGwPort;
	}
	
	
	/**
	 * 根据主键查询网关端口信息
	 * @param id
	 * @return
	 */
	public VoipGwPort queryById(Integer id) {
		if(id!=null) {
			return voipGwPortMapper.selectByPrimaryKey(id);
		}
		return null;
	}
	
	/**
	 * 根据网关删除所有端口数据--逻辑删除
	 * @param gwId
	 */
	public void delGwPortByGwId(Integer gwId) {
		if(gwId!=null) {
			VoipGwPort record = new VoipGwPort();
			record.setGwStatus(VoipGwStatusEnum.INVALID.getCode()); //删除
			VoipGwPortExample example = new VoipGwPortExample();
			example.createCriteria().andGwIdEqualTo(gwId);
			voipGwPortMapper.updateByExampleSelective(record, example);
		}
	}
	
	
	/**
	 * 某些某个网关某个端口号数据
	 * @param id
	 * @return
	 */
	public VoipGwPort queryByPort(Integer gwId,Integer port) {
		if(gwId !=null && port!=null) {
			VoipGwPortExample example = new VoipGwPortExample();
			example.createCriteria().andGwIdEqualTo(gwId).andPortEqualTo(port);
			List<VoipGwPort> list = voipGwPortMapper.selectByExample(example);
			if(list!=null && !list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}
	
	
	/**
	 * 根据网关编号查询某个网关对应的所有端口信息
	 * @param gwId
	 * @return
	 */
	public List<VoipGwPort> queryVoipGwPortsByGwId(Integer gwId){
		if(gwId!=null) {
			VoipGwPortExample example = new VoipGwPortExample();
			example.createCriteria().andGwIdEqualTo(gwId);
			return voipGwPortMapper.selectByExample(example);
		}
		return null;
	}
	
	/**
	 * 查询某个企业下分配到的网关端口
	 * @param orgCode
	 * @return
	 */
	public List<VoipGwPort> queryVoipGwPortsByOrgCode(String orgCode){
		if(StrUtils.isNotEmpty(orgCode)) {
			VoipGwPortExample example = new VoipGwPortExample();
			example.createCriteria().andOrgCodeEqualTo(orgCode);
			return voipGwPortMapper.selectByExample(example);
		}
		return null;
	}
	
	
	/**
	 * 根据条件查询网关端口列表
	 * @param condition
	 * @return
	 */
	public List<VoipGwPort> queryVoipGwPortList(VoipGwPortQueryCondition condition){
		VoipGwPortExample example = this.queryExample(condition);
		return voipGwPortMapper.selectByExample(example);
	}
	
	/**
	 * 根据条件查询网关端口列表-分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public Page<VoipGwPort> queryVoipGwPortForPageByCondition(VoipGwPortQueryCondition condition){
		Page<VoipGwPort> page = new Page<VoipGwPort>();
		int totalRecord = 0;
		int limitStart = (condition.getPageNo()-1)*condition.getPageSize();	//起始条数
		int limitEnd = condition.getPageSize();	//查询条数
		VoipGwPortExample example = this.queryExample(condition);
		//查询总数
		totalRecord = voipGwPortMapper.countByExample(example);
		if(totalRecord > 0) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
			List<VoipGwPort> list = voipGwPortMapper.selectByExample(example);
			page.setRecords(list);
		}
		page.setPageNo(condition.getPageNo());
		page.setPageSize(condition.getPageSize());
		page.setTotal(totalRecord);
		return page;
	}
	
	
	/**
	 * 根据条件获取查询example
	 * @param condition
	 * @return
	 */
	private VoipGwPortExample queryExample(VoipGwPortQueryCondition condition) {
		VoipGwPortExample example = new VoipGwPortExample();
		if(condition != null) {
			Criteria criteria = example.createCriteria();
			if(condition.getGwId()!=null) {
				criteria.andGwIdEqualTo(condition.getGwId());
			}
			if(StrUtils.isNotEmpty(condition.getStartSipAccount())) {
				criteria.andSipAccountEqualTo(condition.getStartSipAccount());
			}
			if(StrUtils.isNotEmpty(condition.getUserId())) {
				criteria.andUserIdEqualTo(condition.getUserId());
			}
			if(condition.getLineId()!=null) {
				criteria.andLineIdEqualTo(condition.getLineId());
			}
			if(StrUtils.isNotEmpty(condition.getOrgCode())) {
				criteria.andOrgCodeEqualTo(condition.getOrgCode());
			}
			if(StrUtils.isNotEmpty(condition.getPhoneNo())) {
				criteria.andPhoneNoLike("%"+ condition.getPhoneNo() +"%");
			}
			if(condition.getGwStatus()!=null && !condition.getGwStatus().isEmpty()) {
				if(condition.getGwStatus().size()==1) {
					criteria.andGwStatusEqualTo(condition.getGwStatus().get(0));
				}else {
					criteria.andGwStatusIn(condition.getGwStatus());
				}
			}
		}
		example.setOrderByClause(" gw_id desc,port asc");	//根据网关、端口排序
		return example;
	}
	
}
