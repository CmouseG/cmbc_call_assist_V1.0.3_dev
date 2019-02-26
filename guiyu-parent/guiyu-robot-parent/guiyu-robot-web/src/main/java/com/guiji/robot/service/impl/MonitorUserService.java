package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.service.vo.CallInfo;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.service.vo.MonitorCallData;
import com.guiji.robot.util.DataLocalCacheUtil;
import com.guiji.robot.util.ListUtil;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.ws.api.WsOnlineUserApi;
import com.guiji.ws.model.OnlineUser;
import com.guiji.ws.model.WsSenceEnum;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: MonitorUserService 
* @Description: 监控用户服务
* @date 2019年2月22日 下午6:24:06 
* @version V1.0  
*/
@Slf4j
@Service
public class MonitorUserService {
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	WsOnlineUserApi wsOnlineUserApi;
	
	
	/**
	 * 获取某个用户可以监控的实时通话列表
	 * 1、如果是普通用户，那么查询自己的实时通话数据
	 * 2、如果是有协呼权限的用户，根据上线用户数量，平均分配实时通话数据
	 * @param userId
	 * @return
	 */
	public MonitorCallData queryMoniorCallListByUser(String userId){
		//查询用户是否协呼用户
		ReturnData<OnlineUser> userData = wsOnlineUserApi.queryOnlineUserByUserId(WsSenceEnum.montiorcall.toString(), userId);
		if(userData !=null && userData.success) {
			OnlineUser callMonitorUser = userData.getBody();
			MonitorCallData data = new MonitorCallData();
			if(callMonitorUser!=null) {
				if(!callMonitorUser.isAssistCallUser()) {
					//普通用户,查询自己的拨打计划实时通话数据
					List<CallInfo> callList = aiCacheService.queryUserCallList(userId);
					if(callList!=null && !callList.isEmpty()) {
						//按incr编号升序排序
						callList.sort(Comparator.comparing(CallInfo::getIncr));
						for(int i=0;i<callList.size();i++) {
							callList.get(i).setAssistFlag(false); //不能协呼
							callList.get(i).setAiName("硅语"+(i+1)+"号");
						}
					}
					data.setOneUserDataFlag(true);	//	只是1个人的数据
					data.setOneUserCallList(callList);
					return data;
				}else {
					//当前人员是可以协呼的人员
					SysOrganization org = dataLocalCacheUtil.queryUserRealOrg(userId);
					//协呼人员按企业处理
					data.setOneUserDataFlag(false); 
					data.setOrgCode(org.getCode());
					//获取可以协呼的用户列表
					List<OnlineUser> assistUserList = this.getAssistUserList(org.getCode());
					//把用户也排下序，这样分配数据的列表会尽量保持一致
					assistUserList.sort(Comparator.comparing(OnlineUser::getUserId));
					if(assistUserList!=null && !assistUserList.isEmpty()) {
						//获取企业下所有实时通话数据
						List<CallInfo> orgCallList = this.queryOrgCallList(org.getCode());
						if(orgCallList!=null && !orgCallList.isEmpty()) {
							//按incr编号升序排序
							orgCallList.sort(Comparator.comparing(CallInfo::getIncr));
						}
						//把企业下所有通话数据按在线的协呼人员数量分组
						List<List<CallInfo>> averageDataList = ListUtil.averageAssign(orgCallList, assistUserList.size());
						if(averageDataList!=null && !averageDataList.isEmpty()) {
							Map<String,List<CallInfo>> userCallMap = new HashMap<String,List<CallInfo>>();
							for(int i=0;i<assistUserList.size();i++) {
								//遍历企业下协呼人员，逐个分配监控的数据
								OnlineUser assistUser = assistUserList.get(i);
								if(orgCallList!=null && !orgCallList.isEmpty()) {
									//顺序分配实时通话数据
									List<CallInfo> callList = averageDataList.get(i);
									//设置是否可协呼操作
									for(int j=0;j<callList.size();j++) {
										String templateId = callList.get(j).getTemplateId();	//话术模板
										HsReplace hsReplace = dataLocalCacheUtil.queryTemplate(templateId);
										if(hsReplace!=null && hsReplace.isAgent()) {
											//TODO 暂时使用转人工判断是否协呼模板
											callList.get(j).setAssistFlag(true);
										}else {
											callList.get(j).setAssistFlag(false);
										}
										callList.get(j).setAssistFlag(false); //不能协呼
									}
									//分配后的数据再重新排序，优先 可转人工操作 / 大小
									callList.sort(Comparator.comparing(CallInfo::isAssistFlag).reversed().thenComparing(CallInfo::getIncr));
									for(int j=0;j<callList.size();j++) {
										callList.get(j).setAiName("硅语"+(j+1)+"号");
									}
									//分配给协呼用户实时通话数据
									userCallMap.put(assistUser.getUserId(), callList);
								}else {
									//分配给协呼用户实时通话数据为空（空也需要给的，告诉前端清空显示）
									userCallMap.put(assistUser.getUserId(), null);
								}
							}
							data.setUserCallMap(userCallMap);
						}
					}
					return data;
				}
			}
		}else {
			log.error("查询在线用户:{}异常..,返回数据：{}",userId,userData);
		}
		return null;
	}
	
	
	/**
	 * 判断用户是否具有协呼角色
	 * @param userId
	 * @return
	 */
	private boolean hasAssistRole(String userId) {
		List<SysRole> roleList = dataLocalCacheUtil.queryUserRole(userId);
		if(roleList!=null && !roleList.isEmpty()) {
			for(SysRole role : roleList) {
				//TODO 现在没有协呼角色，暂时拥有企业管理员的用户就可以做协呼
				if(3==role.getId()) {
					//协呼
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取企业下可以协呼的用户列表
	 * @param orgUserList
	 * @return
	 */
	private List<OnlineUser> getAssistUserList(String orgCode){
		List<OnlineUser> assistUserList = new ArrayList<OnlineUser>();
		ReturnData<List<OnlineUser>> userData = wsOnlineUserApi.queryOnlineUserByOrgCode(WsSenceEnum.montiorcall.toString(),orgCode);
		if(userData!=null && userData.success) {
			List<OnlineUser> orgUserList = userData.getBody();
			if(orgUserList!=null && !orgUserList.isEmpty()) {
				for(OnlineUser user:orgUserList) {
					if(user.isAssistCallUser()) {
						assistUserList.add(user);
					}
				}
			}
		}else {
			log.error("查询在线用户异常，企业：{}，返回结果：{}",orgCode,userData);
		}
		return assistUserList;
	}
	
	/**
	 * 获取企业下所有实时通话列表
	 * @param orgCode
	 * @return
	 */
	private List<CallInfo> queryOrgCallList(String orgCode) {
		//获取企业下所有用户的实时通话数据
		Map<String,List<CallInfo>> userCallMap = aiCacheService.queryCallsByOrg(orgCode);
		if(userCallMap!=null) {
			List<CallInfo> orgCallList = new ArrayList<CallInfo>();
			for(List<CallInfo> userCallList : userCallMap.values()) {
				//遍历数据
				orgCallList.addAll(userCallList);
			}
			return orgCallList;
		}
		return null;
	}
	
}
