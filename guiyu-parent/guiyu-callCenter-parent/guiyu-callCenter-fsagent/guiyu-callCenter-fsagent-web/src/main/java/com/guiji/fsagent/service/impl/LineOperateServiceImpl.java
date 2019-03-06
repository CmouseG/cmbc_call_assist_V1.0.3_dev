package com.guiji.fsagent.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
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
    public boolean updatenotify(String lineId) {
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperApiFeign.linexmlinfos(lineId);
        if(!result.getCode().equals("0")){
            logger.warn("请求fsmanager获取线路接口返回出错:"+result.getCode());
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS);
        }
        List<LineXmlnfoVO>  lineList = result.getBody();
        //获取fs对象
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
            for(LineXmlnfoVO line:lineList){
                if(line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)){
                    Base64Util.base64ToFile(line.getFileData(),fs.getDialplan()+"01_"+lineId+".xml");
                }else if(line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)){
                    Base64Util.base64ToFile(line.getFileData(),fs.getGateway()+"gw_"+lineId+".xml");;
                }
            }
        //执行esl命令重载网关
        fs.execute("sofia profile external killgw gw_"+ lineId);
        fs.execute("sofia profile external rescan reloadxml");
        return true;
    }

    @Override
    public boolean updatenotifybatch(String lineIds) {
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperApiFeign.batchlinexmlinfosAll(lineIds);
        if(!result.getCode().equals("0")){
            logger.warn("请求fsmanager获取线路接口返回出错:"+result.getCode());
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS);
        }
        List<LineXmlnfoVO>  lineList = result.getBody();
        //获取fs对象
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
        for(LineXmlnfoVO line:lineList){
            if(line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)){
                Base64Util.base64ToFile(line.getFileData(),fs.getDialplan()+line.getFileName());
            }else if(line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)){
                Base64Util.base64ToFile(line.getFileData(),fs.getGateway()+line.getFileName());
                //执行esl命令重载网关
                String[] values = line.getFileName().split("\\.");
                fs.execute("sofia profile external killgw "+ values[0]);
            }
        }
        fs.execute("sofia profile external rescan reloadxml");
        return true;
    }
}
