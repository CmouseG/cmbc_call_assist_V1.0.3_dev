package com.guiji.utils;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 14:26
 * @Description:
 */
public class ServerUtil {

    /**
     * 通过serviceid获取该服务实例列表
     * @param discoveryClient
     * @param serviceId
     * @return
     */
    public static List<String> getInstances(DiscoveryClient discoveryClient,String serviceId) {
        List<String> serverList = new ArrayList<String>();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceId);
        for (ServiceInstance service : services) {
            String host = service.getHost();
            int port = service.getPort();
            serverList.add(host + ":"+ port);
        }
        return serverList;
    }

}
