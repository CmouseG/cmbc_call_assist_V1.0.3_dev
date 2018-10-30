package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.vo.LinePort;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 15:54
 * @Description:
 */
@RestController
public class FsLineController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @ApiOperation(value = "获取所有的fsline对应的freeswitch对外端口列表")
    @GetMapping(value="fsoutlines")
    public List getLinePorts(){

/*        // 从Eureka获取所有的fsline服务列表
        List<String> serverList = ServerUtil.getInstances(discoveryClient,"gui-callCenter-fsline");
        // 调用fsline的获取基本信息接口，从里面提取fslineId、fsIp和fsOutPort，拼装后返回
        for(String server:serverList){
            FeignBuildUtil.feignBuilderTarget(xxx.calss,server);
        }*/

        LinePort linePort1 = new LinePort("xx","192.168.1.1:8081");
        LinePort linePort2 = new LinePort("yy","192.168.1.2:8082");
        LinePort linePort3 = new LinePort("zz","192.168.1.3:8083");

        List list = new ArrayList();
        list.add(linePort1);
        list.add(linePort2);
        list.add(linePort3);

        return list;
    }

}
