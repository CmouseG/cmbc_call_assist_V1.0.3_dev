package com.guiji.robot.service.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;

/** 
* @ClassName: MonitorCallData 
* @Description: 数据监控对象
* @date 2019年2月23日 上午9:44:32 
* @version V1.0  
*/
@Data
public class MonitorCallData {
	/**1、查出的1人的数据**/
	//是否查询结果是1个人的数据
	private boolean oneUserDataFlag;
	//独自一人的查询数据
	private List<CallInfo> oneUserCallList;
	
	/**2、查出的该企业的用户数据--性能考虑，一块返回了**/
	//查询用户的所属企业
	private String orgCode;
	//企业下：用户-监控数据
	private Map<String,List<CallInfo>> userCallMap;
	
	
}
