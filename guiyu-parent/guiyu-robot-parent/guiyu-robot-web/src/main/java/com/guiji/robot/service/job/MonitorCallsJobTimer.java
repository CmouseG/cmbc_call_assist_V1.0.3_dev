package com.guiji.robot.service.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.impl.MonitorUserService;
import com.guiji.robot.service.vo.CallInfo;
import com.guiji.robot.service.vo.MonitorCallData;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;
import com.guiji.ws.api.WsConnectApi;
import com.guiji.ws.api.WsOnlineUserApi;
import com.guiji.ws.model.WebSocketConnect;
import com.guiji.ws.model.WsMsg;
import com.guiji.ws.model.WsSenceEnum;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/** 
* @ClassName: MonitorCallsJobTimer 
* @Description: 通话实时监控服务
* @date 2019年2月25日 下午10:02:34 
* @version V1.0  
*/
@Component
@JobHandler(value="monitorCallsJobTimer")
public class MonitorCallsJobTimer extends IJobHandler{
	@Autowired
	WsConnectApi wsConnectApi;
	@Autowired
	WsOnlineUserApi wsOnlineUserApi;
	@Autowired
	MonitorUserService monitorUserService;
	@Autowired
	AiCacheService aiCacheService;
	
	/**
	 * 通话情况实时监控
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		try {
			//获取实时监控 ws连接
			ReturnData<CopyOnWriteArraySet<WebSocketConnect>> listData = wsConnectApi.queryWsConnects(WsSenceEnum.montiorcall.toString());
			CopyOnWriteArraySet<WebSocketConnect> list = listData.getBody();
			if(list!=null && !list.isEmpty()) {
				Map<String,Map<String,List<CallInfo>>> orgUserCallMap = new HashMap<String,Map<String,List<CallInfo>>>();
				for(WebSocketConnect socket:list) {
					WsMsg wsMsg = new WsMsg();
					wsMsg.setConnect(socket);
					String orgCode = socket.getOrgCode();
					String userId = socket.getUserId();
					boolean isAssignUser = socket.isAssistCallUser();	//是否协呼人员
					if(StrUtils.isNotEmpty(userId)) {
						//查询该用户需要监控的实时通话
						List<CallInfo> myCallList = null;
						if(!isAssignUser) {
							MonitorCallData data = monitorUserService.queryMoniorCallListByUser(userId);
				        	if(data!=null) {
				        		//是一个人的数据
			        			myCallList = data.getOneUserCallList();
				        	}
						}else {
							//协呼人员，先查询缓存数据中是否已经查过了
							if(orgUserCallMap.get(orgCode)!=null) {
								//从企业数据中查询当前用户分配的实时通话
								myCallList = orgUserCallMap.get(orgCode).get(userId);
							}else {
								MonitorCallData data = monitorUserService.queryMoniorCallListByUser(userId);
					        	if(data!=null) {
					        		//获取企业下所有用户的数据
					        		Map<String,List<CallInfo>> userCallMap = data.getUserCallMap();
					        		orgUserCallMap.put(orgCode, userCallMap);
					        		myCallList = userCallMap.get(userId);
					        	}
							}
						}
						if(myCallList!=null && !myCallList.isEmpty()) {
							String rtnJson = JsonUtils.bean2Json(myCallList);
							wsMsg.setMessage(rtnJson);
							wsConnectApi.asyncSendMsg(wsMsg);
						}else {
							//告诉前端清空数据
							wsMsg.setMessage("");
							wsConnectApi.asyncSendMsg(wsMsg);
						}
					}
				}
			}
			//1S循环一次
			Thread.sleep(1000);
		} catch (Exception e) {
			XxlJobLogger.log("循环监听实时通话服务发生异常!!!",e);
			Thread.sleep(2000);
		}
		return SUCCESS;
	}
	
}
