package com.guiji.clm.service.fee;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.vo.FeeItem;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 计费服务（本服务不计费，对接计费中心）
* @Author: weiyunbo
* @date 2019年1月30日 下午4:17:22 
* @version V1.0  
*/
@Slf4j
@Service
public class FeeService {
	public static final String BILLING_MQ_QUEUE = "billing.CHARGINGTERMNOTIFY";
	@Autowired
    private QueueSender queueSender;
	
	
	/**
	 * SIP线路计费
	 * @param feeOptEnum	费用操作类型
	 * @param sipLineExclusive	SIP线路信息
	 */
	public void sipFee(FeeOptEnum feeOptEnum,SipLineExclusive sipLineExclusive) {
		if(sipLineExclusive!=null && StrUtils.isNotEmpty(sipLineExclusive.getBelongUser()) && sipLineExclusive.getUnivalent()!=null) {
			//sip线路转为计费项
			FeeItem feeItem = this.sip2Fee(sipLineExclusive);
			switch (feeOptEnum){
			case UP:
				//新增、修改计费项
				feeItem.setStatus(1); //启用计费
				break;
			case DEL:
				feeItem.setStatus(0); //停止计费
				break;
			}
			//调用MQ消息通知计费中心
			log.info("SIP费用数据变更,准备调用计费中心做信息变更...",feeItem);
			queueSender.send(BILLING_MQ_QUEUE, JsonUtils.bean2Json(feeItem));
		}
	}
	
	/**
	 * 语音网关线路计费
	 * @param feeOptEnum
	 * @param voipGwPort
	 */
	public void voipFee(FeeOptEnum feeOptEnum,VoipGwPort voipGwPort) {
		if(voipGwPort!=null && StrUtils.isNotEmpty(voipGwPort.getUserId()) && voipGwPort.getUnivalent()!=null) {
			//sip线路转为计费项
			FeeItem feeItem = this.voip2Fee(voipGwPort);
			switch (feeOptEnum){
			case UP:
				//新增、修改计费项
				feeItem.setStatus(1); //启用计费
				break;
			case DEL:
				feeItem.setStatus(0); //停止计费
				break;
			}
			//调用MQ消息通知计费中心
			log.info("SIP费用数据变更,准备调用计费中心做信息变更...",feeItem);
			queueSender.send(BILLING_MQ_QUEUE, JsonUtils.bean2Json(feeItem));
		}
	}
	
	
	/**
	 * SIP线路转为计费对象
	 * @param sipLineExclusive
	 * @return
	 */
	private FeeItem sip2Fee(SipLineExclusive sipLineExclusive) {
		FeeItem feeItem = new FeeItem();
		feeItem.setChargingItemId(sipLineExclusive.getLineId().toString()); //线路id
		feeItem.setChargingItemName(sipLineExclusive.getLineName()); //线路名称
		feeItem.setChargingType(1); //按时长计费
		feeItem.setPrice(sipLineExclusive.getUnivalent().multiply(new BigDecimal(100)).toString());	//计费（分）
		feeItem.setUnitPrice(2); //计费单位-分钟
		feeItem.setUserId(sipLineExclusive.getBelongUser()); 
		feeItem.setIsDeducted(0); //扣费
		return feeItem;
	}
	
	/**
	 * voip端口线路转为计费对象
	 * @param sipLineExclusive
	 * @return
	 */
	private FeeItem voip2Fee(VoipGwPort voipGwPort) {
		if(voipGwPort.getUnivalent()!=null) {
			FeeItem feeItem = new FeeItem();
			feeItem.setChargingItemId(voipGwPort.getLineId().toString()); //线路id
			feeItem.setChargingItemName(voipGwPort.getPhoneNo()); //线路名称
			feeItem.setChargingType(1); //按时长计费
			feeItem.setPrice(voipGwPort.getUnivalent().multiply(new BigDecimal(100)).toString());	//计费（分）
			feeItem.setUnitPrice(2); //计费单位-分钟
			feeItem.setUserId(voipGwPort.getUserId()); 
			feeItem.setOrgCode(voipGwPort.getOrgCode());
			feeItem.setIsDeducted(1); //不扣费
			return feeItem;
		}
		return null;
	}
	
	
	/**
	* @Description: 计费操作类型
	* @Author: weiyunbo
	* @date 2019年1月30日 下午4:27:53 
	* @version V1.0
	 */
	public enum FeeOptEnum{
		UP,	//计费
		DEL		//删除计费项
		
	}
}
