package com.guiji.fsagent.service.impl;

import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.manager.ApplicationInit;
import com.guiji.fsagent.service.LineOperateService;
import com.guiji.fsagent.util.Base64Util;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class LineOperateServiceImpl implements LineOperateService {
    private final Logger logger = LoggerFactory.getLogger(LineOperateServiceImpl.class);

    @Autowired
    ILineOper lineOperApiFeign;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    ApplicationInit pplicationInit;
    @Override
    public Result.ReturnData<Boolean> updatenotify(String lineId) {
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperApiFeign.linexmlinfos(lineId);
        List<LineXmlnfoVO>  lineList = result.getBody();
        //获取fs对象
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
        Base64Util util = new Base64Util();
        try {
            for(LineXmlnfoVO line:lineList){
                if(line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)){
                    util.base64ToFile(line.getFileData(),fs.getDialplan()+"01_"+lineId+".xml");
                }else if(line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)){
                    util.base64ToFile(line.getFileData(),fs.getGateway()+"gw_"+lineId+".xml");;
                }
            }
        } catch (IOException e) {
            logger.info("下载新得xml后转base64过程中失败",e);
            return Result.error(Constant.ERROR_CODE_BASE64_ERROR);
        }
        //执行esl命令重载网关
        fs.execute("sofia profile external killgw gw_"+ lineId);
        fs.execute("sofia profile external rescan reloadxml");
        return Result.ok();
    }
}
