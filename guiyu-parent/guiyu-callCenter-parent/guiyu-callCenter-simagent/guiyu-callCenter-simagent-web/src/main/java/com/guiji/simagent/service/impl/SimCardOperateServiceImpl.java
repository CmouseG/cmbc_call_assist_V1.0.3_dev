package com.guiji.simagent.service.impl;

import com.guiji.simagent.config.FsConfig;
import com.guiji.simagent.entity.FreeSWITCH;
import com.guiji.simagent.entity.FsSipOprVO;
import com.guiji.simagent.entity.GlobalVar;
import com.guiji.simagent.entity.SimCardOprVO;
import com.guiji.simagent.manager.ApplicationInit;
import com.guiji.simagent.service.SimCardOperateService;
import com.guiji.simagent.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimCardOperateServiceImpl implements SimCardOperateService {
    @Autowired
    FsConfig fsConfig;
    @Autowired
    ApplicationInit pplicationInit;

    @Override
    public FsSipOprVO createGateway(SimCardOprVO simCardOprVO) {
        int startCount = simCardOprVO.getStartCount();
        int countsStep = simCardOprVO.getCountsStep();
        int startPwd = simCardOprVO.getStartPwd();
        int pwdStep = simCardOprVO.getPwdStep();
        int countNum = simCardOprVO.getCountNum();
        //获取fs对象
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
        String cmd1 = String.format("lua create_user.lua %s %s", startCount, startPwd);
        fs.executeAsync(cmd1);//创建用户
        for (int i = 1; i < countNum; i++) {
            startCount = startCount + countsStep;
            startPwd = startPwd + pwdStep;
            String cmd = String.format("lua create_user.lua %s %s", startCount, startPwd);
            fs.executeAsync(cmd);//创建用户
        }
        GlobalVar globalVar = fs.getGlobalVar();
        FsSipOprVO fsSipOprVO = new FsSipOprVO();
        fsSipOprVO.setSipIp(globalVar.getGc_docker_ip());
        fsSipOprVO.setSipPort(globalVar.getInternal_sip_port());
        return fsSipOprVO;
    }

    @Override
    public boolean deleteGateway(int startCount, int countsStep, int countNum) {
        FileUtil.delete(fsConfig.getHomeDir() + "/conf/directory/default/" + startCount + ".xml");
        for (int i = 1; i < countNum; i++) {
            startCount = startCount + countsStep;
            FileUtil.delete(fsConfig.getHomeDir() + "/conf/directory/default/" + startCount + ".xml");
        }
        return true;
    }
}

