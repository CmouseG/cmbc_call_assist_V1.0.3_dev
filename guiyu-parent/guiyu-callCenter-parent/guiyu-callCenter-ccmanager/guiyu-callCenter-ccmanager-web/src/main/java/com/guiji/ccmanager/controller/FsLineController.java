//package com.guiji.ccmanager.controller;
//
//import com.guiji.ccmanager.constant.Constant;
//import com.guiji.ccmanager.vo.LinePort;
//import com.guiji.component.jurisdiction.Jurisdiction;
//import com.guiji.component.result.Result;
//import com.guiji.utils.FeignBuildUtil;
//import com.guiji.utils.ServerUtil;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/10/25 0025 15:54
// * @Description:
// */
//@RestController
//public class FsLineController {
//
//    private final Logger log = LoggerFactory.getLogger(FsLineController.class);
//
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @ApiOperation(value = "获取所有的fsline对应的freeswitch对外端口列表")
//    @Jurisdiction("callCenter_lineInfoList_detailLines")
//    @GetMapping(value="getFsOutLines")
//    public List getFsOutLines(){
//
//        log.info("get request getFsOutLines");
//
//       // 从Eureka获取所有的fsline服务列表
//        List<String> serverList = ServerUtil.getInstances(discoveryClient,Constant.SERVER_NAME_FSLINE);
//        // 调用fsline的获取基本信息接口，从里面提取fslineId、fsIp和fsOutPort，拼装后返回
//        List<LinePort> list = new ArrayList<LinePort>();
//        for(String server:serverList){
//            IFsLine fsLineApi = FeignBuildUtil.feignBuilderTarget(IFsLine.class,Constant.PROTOCOL +server);
//            Result.ReturnData<FsLineVO> result = fsLineApi.getFsInfo();
//            FsLineVO fsLineInfo = result.getBody();
//            LinePort linePort = new LinePort(fsLineInfo.getFsIp(),fsLineInfo.getFsOutPort());
//            list.add(linePort);
//        }
//        log.info("response success getFsOutLines");
//        return list;
//    }
//
//}
