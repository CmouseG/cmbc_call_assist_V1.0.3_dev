package com.guiji.fsagent.manager;

import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.util.Base64Util;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 启动时执行，下载所有的line文件
 */
@Component
public class ReloadLineManager {
    @Autowired
    ILineOper lineOperApiFeign;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    FSService fsService;
     @PostConstruct
    public void init() {
         Result.ReturnData<List<LineXmlnfoVO>> result = lineOperApiFeign.linexmlinfosAll();
         List<LineXmlnfoVO>  lineList = result.getBody();
         //获取fs对象
         FreeSWITCH fs = fsService.getFreeSwitch();
         for(LineXmlnfoVO line:lineList){
             if(line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)){
                 Base64Util.base64ToFile(line.getFileData(),fs.getDialplan()+line.getFileName());
             }else if(line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)){
                 Base64Util.base64ToFile(line.getFileData(),fs.getGateway()+line.getFileName());
                 //执行esl命令卸载网关
                 fs.execute("sofia profile external killgw "+line.getFileName());
             }
             //执行esl命令加载网关
             fs.execute("sofia profile external rescan reloadxml");
         }
    }
}
