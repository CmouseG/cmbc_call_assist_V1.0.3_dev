package com.guiji.auth.util;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: OrgUtil 
* @Description: 组织工具类 
* @auth weiyunbo
* @date 2019年3月20日 下午1:30:39 
* @version V1.0  
*/
@Slf4j
public class OrgUtil {

	public static String getParentOrg(String orgCode) {
		if(StrUtils.isNotEmpty(orgCode)) {
			if(AuthConstants.ROOT_ORG_CODE.equals(orgCode)) {
				return orgCode;
			}
			int last2 = orgCode.lastIndexOf(".",orgCode.lastIndexOf(".")-1);	//倒数第2个.
			if(last2>=0) {
				String parentOrgCode = orgCode.substring(0,last2+1);
				if((AuthConstants.ROOT_ORG_CODE+".").equals(parentOrgCode)) {
					return AuthConstants.ROOT_ORG_CODE;
				}else {
					return parentOrgCode;
				}
			}else {
				log.error("组织代码{}获取上级组织异常",orgCode);
			}
		}
		return null;
	}
}
