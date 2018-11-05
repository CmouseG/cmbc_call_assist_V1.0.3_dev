package com.guiji.fsagent.service.impl;

import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.entity.FsInfo;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.entity.GlobalVar;
import com.guiji.fsagent.manager.FSService;
import com.guiji.fsagent.manager.FsEslClient;
import com.guiji.utils.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service
public class FsStateServiceImpl {

    @Autowired
    FSService fsService;
    @Autowired
    Registration registration;

    public Boolean ishealthy() {
        Boolean healthy=true;
        //1、查FreeSWITCH的esl端口是否处于开启状态
        FreeSWITCH fs = fsService.getFreeSwitch();
        try {

            Socket socket = new Socket("localhost" , Integer.parseInt(fs.getFsEslPort()));
            if(!socket.isConnected()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //2、使用fs_cli -x执行status命令，并获取结果
        FsEslClient fsEslClient = fs.getFsEslClient();
        String result = fsEslClient.execute("status");
        return healthy;
    }

    public FsInfoVO fsinfo() {
       String agentId = ServerUtil.getInstanceId(registration);
        FreeSWITCH fs = fsService.getFreeSwitch();
        GlobalVar globalVar = new GlobalVar();
        FsInfoVO fsinfo = new FsInfoVO();
        fsinfo.setFsAgentId(agentId);
        fsinfo.setFsIp(globalVar.getDomain());
        fsinfo.setFsInPort(globalVar.getInternal_sip_port());
        fsinfo.setFsOutPort(globalVar.getExternal_sip_port());
        fsinfo.setFsEslPort(fs.getFsEslPort());
        fsinfo.setFsEslPwd(fs.getFsEslPwd());
        return fsinfo;
    }

}
