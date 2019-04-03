package com.guiji.calloutserver.manager;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 20:16
 * @Project：guiyu-parent
 * @Description:
 */
@Component
public class EurekaManager {
    @Autowired
    Registration registration;

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @Value("${spring.application.name}")
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
}
