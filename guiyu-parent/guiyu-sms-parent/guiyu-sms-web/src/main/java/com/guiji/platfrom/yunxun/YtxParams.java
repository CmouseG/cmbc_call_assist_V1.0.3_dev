package com.guiji.platfrom.yunxun;

import lombok.Data;

@Data
public class YtxParams
{
	// 公共请求参数
	private String accountSID; // 云通信平台用户账户Id：对应管理控制台中的 ACCOUNT SID
	
	private String authToken; // 云通信平台用户账户授权令牌：对应管理控制台中的 AUTH TOKEN
	
	private String version = "201512"; // 云通信API接口版本 目前可选版本：201512
	
	private String func = "sms"; // 功能所属分类call【语音类】/sms【消息类】/traffic【流量类】/account【账户类】 当前功能属：sms
	
	private String funcURL = "TemplateSMS"; // 业务功能的各类具体操作分支 当前功能属：TemplateSMS.wx

	private String authorization; // 云通信平台API接口，包头验证信息：base64加密(账户Id + "|" + 时间戳) 说明：时间戳有效时间为24小时 格式"yyyyMMddHHmmss"，如：20140416142030
	
	private String sign; // 云通信平台API接口，API 验证参数 ：MD5加密（账户Id + 账户授权令牌 +时间戳) *URL后必须带有Sign参数，例如：Sign=AAABBBCCCDDDEEEFFFGGG *时间戳需与Authorization中时间戳相同 注:MD5加密32位,无论大小写
	
	// 请求参数
	private String action = "templateSms"; // API接口名称，默认值：templateSms（区分大小写）
	
	private String appid; // 用户登录云通信平台后，所创建的应用的编号appid，若想调用当前模板短信接口，则此应用必须包含有模板短信功能，否则调用失败。
	
	private String mobile; // 接收短信的手机号，可以传入多个手机号，并以英文逗号分隔（例：153****2082,188****7016）
	
	private Integer templateId; // 所采用的模板编号templateId，使用事先编辑好、已通过平台审核的模板，可以加快你接入的速度

}
