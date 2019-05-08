package com.guiji.fsagent.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.entity.GlobalVar;
import com.guiji.fsagent.manager.ApplicationInit;
import com.guiji.fsagent.service.FsStateService;
import com.guiji.fsagent.util.PortStateUtil;
import com.guiji.utils.ServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Service;

@Service
public class FsStateServiceImpl implements FsStateService {
    private final Logger logger = LoggerFactory.getLogger(FsStateServiceImpl.class);

    @Autowired
    ApplicationInit aplicationInit;
    @Autowired
    Registration registration;
    @Autowired
    FsConfig fsConfig;
    @Override
    public String ishealthy() {
        //1、查FreeSWITCH的esl端口是否处于开启状态
        FreeSWITCH fs = aplicationInit.getFreeSwitch();
        boolean result = PortStateUtil.PortIsIn(fs.getFsEslPort());
        if (!result) {
            logger.info("freeswitch的esl端口没有开启，freeswitch异常");
            // TODO-- 报警
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS);
        }
        return fsConfig.getRole();
    }

    @Override
    public FsInfoVO fsinfo() {
        String agentId = ServerUtil.getInstanceId(registration);
        FreeSWITCH fs = aplicationInit.getFreeSwitch();
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
