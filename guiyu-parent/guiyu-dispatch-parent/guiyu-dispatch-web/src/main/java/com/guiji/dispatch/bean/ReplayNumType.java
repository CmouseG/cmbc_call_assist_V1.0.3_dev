package com.guiji.dispatch.bean;

/**
 * 重播号码类型
 * @author Administrator
 *
 */
public class ReplayNumType {
	//占线
	private String busy;
	//无人接听
	private String noAnswer;
	//主叫停机
	private String mainCallStop;
	//关机
	private String closePhone;
	//被叫停机
	private String beCallStop;
	//呼叫限制
	private String callrestrict;
	//其他 
	private String other;
	//用户拒接
	private String userrefuse;
	public String getBusy() {
		return busy;
	}
	public void setBusy(String busy) {
		this.busy = busy;
	}
	public String getNoAnswer() {
		return noAnswer;
	}
	public void setNoAnswer(String noAnswer) {
		this.noAnswer = noAnswer;
	}
	public String getMainCallStop() {
		return mainCallStop;
	}
	public void setMainCallStop(String mainCallStop) {
		this.mainCallStop = mainCallStop;
	}
	public String getClosePhone() {
		return closePhone;
	}
	public void setClosePhone(String closePhone) {
		this.closePhone = closePhone;
	}
	public String getBeCallStop() {
		return beCallStop;
	}
	public void setBeCallStop(String beCallStop) {
		this.beCallStop = beCallStop;
	}
	public String getCallrestrict() {
		return callrestrict;
	}
	public void setCallrestrict(String callrestrict) {
		this.callrestrict = callrestrict;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getUserrefuse() {
		return userrefuse;
	}
	public void setUserrefuse(String userrefuse) {
		this.userrefuse = userrefuse;
	}
	
	
	
	
}
