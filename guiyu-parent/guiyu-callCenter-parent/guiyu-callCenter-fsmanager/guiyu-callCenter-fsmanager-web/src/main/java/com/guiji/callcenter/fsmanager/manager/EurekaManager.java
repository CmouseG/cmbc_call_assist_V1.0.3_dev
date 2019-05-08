package com.guiji.callcenter.fsmanager.manager;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.utils.FeignBuildUtil;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 20:16
 * @Project：guiyu-parent
 * @Description:
 */
@Component
@Slf4j
public class EurekaManager {
    @Autowired
    Registration registration;

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${eureka.instance.instance-id:}")
    private String instanceId;

    @Value("${spring.application.name:}")
    private String appName;

    /**
     * 获取当前实例id
     * @return
     */
    public String getInstanceId(){
        //return ((EurekaRegistration) registration).getInstanceConfig().getInstanceId();
        return instanceId;
    }

    /**
     * 获取当前应用名称
     * @return
     */
    public String getAppName(){
        return appName;
    }

    /**
     * 根据应用名称获取所有实例列表
     * @param appName
     * @return
     */
    public List<String> getInstances(String appName) {
        List<String> serverList = new ArrayList<>();
        List<ServiceInstance> services = discoveryClient.getInstances(appName);
        for (ServiceInstance service : services) {
            String host = service.getHost();
            int port = service.getPort();
            serverList.add(host + ":"+ port);
        }
        return serverList;
    }

    /**
     * 随机得到一个相关角色的fsagent
     * @return
     */
    public String  getAgentService(String agentRole){
        List<String> agentList = new ArrayList<>();
        //注册中心所有在线的fsagent
        List<String> serverList = getInstances(Constant.SERVER_NAME_FSAGENT);
        //遍历fsagent，将可用的simagent角色的fsagent都放入到simagentList中
        for (String server : serverList) {
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + server);
            try {
                //调用fsagent健康状态接口
                Result.ReturnData<String> result = iFsStateApi.ishealthy();
                if (result.getBody().equals(agentRole)){
                    agentList.add(server);
                }
            } catch (Exception e) {
                break;
            }
        }
        if(agentList.size()==0){
            return null;
        }
        Random random = new Random();
        int n = random.nextInt(agentList.size());
        return agentList.get(n);
    }

    /**
     * 根据fsIp和fsInPort得到simagent
     * @param fsIp
     * @param fsInPort
     * @return
     */
    public String  getSimAgentService(String fsIp,String fsInPort){
        //注册中心所有在线的fsagent
        List<String> serverList = getInstances(Constant.SERVER_NAME_FSAGENT);
        for (String server : serverList) {
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + server);
            try {
                //调用fsagent健康状态接口
                Result.ReturnData<String> result = iFsStateApi.ishealthy();
                if (result.getBody().equals(Constant.LINE_TYPE_SIMCARD)){
                    Result.ReturnData<FsInfoVO> resultFsInfo =  iFsStateApi.fsinfo();
                    FsInfoVO fsInfoVO = resultFsInfo.getBody();
                    if(fsInfoVO.getFsIp().equals(fsIp)&&fsInfoVO.getFsInPort().equals(fsInPort)){
                        return server;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
        log.warn("根据fsIp和fsInPort得到simagent失败，fsIp:[{}],fsInPort:[{}]",fsIp,fsInPort);
        return null;
    }


}
