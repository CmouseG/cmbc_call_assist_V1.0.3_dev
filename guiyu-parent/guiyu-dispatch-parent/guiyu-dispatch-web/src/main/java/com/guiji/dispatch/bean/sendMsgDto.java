package com.guiji.dispatch.bean;

import java.io.Serializable;

public class sendMsgDto implements Serializable {
	
	private String phone ;
	//云通信平台用户账户Id：对应管理控制台中的 ACCOUNT SID
	private String accountSID;
	//云通信平台用户账户授权令牌：对应管理控制台中的 AUTH TOKEN
	private String authToken;
	//云通信API接口版本 目前可选版本：201512
	private String version;
	//功能所属分类call【语音类】/sms【消息类】/traffic【流量类】
	//account【账户类】 当前功能属：sms
	private String func = "sms";
	//业务功能的各类具体操作分支 当前功能属：TemplateSMS.wx
	private String funcURL = "TemplateSMS";
	//云通信平台API接口，包头验证信息：
	//base64加密(账户Id + "|" + 时间戳) 说明：时间戳有效时间为24小时 格式"yyyyMMddHHmmss"，如：20140416142030
	private String authorization;
	//云通信平台API接口，API 验证参数 ：MD5加密（账户Id + 账户授权令牌 +时间戳) 
	//*URL后必须带有Sign参数，例如：Sign=AAABBBCCCDDDEEEFFFGGG *时间戳需与Authorization中时间戳相同 
	//注:MD5加密32位,无论大小写
	private String sign;
	//API接口名称，默认值：templateSms（区分大小写）
	private String action;
	//用户登录云通信平台后，所创建的应用的编号appid，若想调用当前模板短信接口，则此应用必须包含有模板短信功能，否则调用失败。
	private String appid;
	//接收短信的手机号，可以传入多个手机号，并以英文逗号分隔（例：153****2082,188****7016）
	private String mobile;
	//所采用的模板编号templateId，使用事先编辑好、已通过平台审核的模板，可以加快你接入的速度
	private String templateId;
	//若所采用的模板中有{0}等占位符，
	//该字符串数组的元素分别填充模板占位符 详细说明请看下面报文示例关于模板templateId为1的例子
	private String[] datas;
	//（相关人员提供）
	private String spuid;
	//（相关人员提供）
	private String sppwd;
	
	private Integer smsTemplateId;
	
	
	
	
	
	public Integer getSmsTemplateId() {
		return smsTemplateId;
	}
	public void setSmsTemplateId(Integer smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccountSID() {
		return accountSID;
	}
	public void setAccountSID(String accountSID) {
		this.accountSID = accountSID;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public String getFuncURL() {
		return funcURL;
	}
	public void setFuncURL(String funcURL) {
		this.funcURL = funcURL;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String[] getDatas() {
		return datas;
	}
	public void setDatas(String[] datas) {
		this.datas = datas;
	}
	public String getSpuid() {
		return spuid;
	}
	public void setSpuid(String spuid) {
		this.spuid = spuid;
	}
	public String getSppwd() {
		return sppwd;
	}
	public void setSppwd(String sppwd) {
		this.sppwd = sppwd;
	}
	
	
	
}
