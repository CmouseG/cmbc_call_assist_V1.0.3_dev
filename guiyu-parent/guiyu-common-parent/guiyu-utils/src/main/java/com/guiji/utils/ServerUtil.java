package com.guiji.utils;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 14:26
 * @Description:
 */
public class ServerUtil {

    /**
     *获取自身服务的ip和端口
     * @param registration
     * @return
     */
    public static String getUrlSelf(Registration registration) {
        return registration.getHost() + ":" + registration.getPort();
    }

    /**
     * 获取服务实例id,主要由ip地址加端口组成
     * @param registration
     * @return
     */
    public static String getInstanceId(Registration registration){
        return ((EurekaRegistration) registration).getInstanceConfig().getInstanceId();
    }

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
