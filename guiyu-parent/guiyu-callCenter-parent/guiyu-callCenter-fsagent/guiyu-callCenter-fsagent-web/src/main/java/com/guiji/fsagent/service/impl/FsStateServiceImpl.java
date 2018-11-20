package com.guiji.fsagent.service.impl;

import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.entity.GlobalVar;
import com.guiji.fsagent.manager.ApplicationInit;
import com.guiji.utils.ServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
public class FsStateServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(FsStateServiceImpl.class);

    @Autowired
    ApplicationInit pplicationInit;
    @Autowired
    Registration registration;

    public Boolean ishealthy()  {
        //1、查FreeSWITCH的esl端口是否处于开启状态
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
        Socket socket =null;
        try {
            socket = new Socket("localhost" , Integer.parseInt(fs.getFsEslPort()));
            if(!socket.isConnected()) {
                return false;
            }
        } catch (Exception e) {
            logger.info("检查本机某个端口出错",e);
            return false;
        }finally {
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.info("关闭socket异常",e);
                }
            }
        }
        return true;
    }

    public FsInfoVO fsinfo() {
       String agentId = ServerUtil.getInstanceId(registration);
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
        GlobalVar globalVar = fs.getGlobalVar();
        FsInfoVO fsinfo = new FsInfoVO();
        fsinfo.setFsAgentId(agentId);
        fsinfo.setFsIp(globalVar.getDomain());
        fsinfo.setFsInPort(globalVar.getInternal_sip_port1());
        fsinfo.setFsOutPort(globalVar.getExternal_sip_port());
        fsinfo.setFsEslPort(fs.getFsEslPort());
        fsinfo.setFsEslPwd(fs.getFsEslPwd());
        return fsinfo;
    }

}
