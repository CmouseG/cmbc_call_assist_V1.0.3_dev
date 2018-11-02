package com.guiji.fsagent.controller;

import com.guiji.common.result.Result;
import com.guiji.fsagent.api.LineOperateApi;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.feign.ILineOperApiFeign;
import com.guiji.fsagent.manager.FSService;
import com.guiji.fsagent.util.Base64Util;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.fsmanager.entity.LineXmlnfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LineOperateController implements LineOperateApi {
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    ILineOperApiFeign lineOperApiFeign;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    FSService fsService;
    @Override
    public Result.ReturnData updatenotify(String type, String lineId) {
          if(!type.equals("line")){
              return Result.error("");
          }
         Result.ReturnData<List<LineXmlnfo>> result = lineOperApiFeign.linexmlinfos(lineId);
         List<LineXmlnfo>  lineList = result.getBody();
         //获取fs对象
         FreeSWITCH fs = fsService.getFreeSwitch();
         for(LineXmlnfo line:lineList){
             if(line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)){
                 Base64Util.base64ToFile(line.getFileData(),fs.getDialplan()+"01_"+lineId+".xml");;
             }else if(line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)){
                 Base64Util.base64ToFile(line.getFileData(),fs.getGateway()+"gw_"+lineId+".xml");;
             }
             //执行esl命令重载网关
             fs.execute("sofia profile external killgw gw_"+ lineId);
             fs.execute("sofia profile external rescan reloadxml");
         }
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<Boolean> deleteLineinfos(String lineId) {
        //获取fs对象
        FreeSWITCH fs = fsService.getFreeSwitch();
        FileUtil.delete(fs.getDialplan()+"01_"+lineId+".xml");
        FileUtil.delete(fs.getGateway()+"gw_"+lineId+".xml");
        fs.execute("sofia profile external killgw gw_"+ lineId);
        return Result.ok(true);
    }
}
