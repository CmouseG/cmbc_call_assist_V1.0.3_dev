package com.guiji.fsagent.manager;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.util.IPUtil;
import com.guiji.fsagent.util.LineOperUtil;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.api.ILineOperation;
import com.guiji.fsmanager.api.ISimCard;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import com.guiji.fsmanager.entity.SimCardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化fsagent服务，连接esl，加载所有的line文件
 */
@Slf4j
@Component
public class ApplicationInit {
    @Autowired
    IFsResource iFsResource;
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    ILineOperation lineOperation;
    @Autowired
    ISimCard simCard;
//    @Autowired
//    IAgentGroup agentGroup;
    private FreeSWITCH freeSwitch;

    /**
     * 在系统启动完成，需要进行初始化，包括以下内容：
     * 1、连接freeswitch ESL
     * 2、从fsmanager下载所有的line
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            String serviceId = eurekaManager.getInstanceId();
            freeSwitch = new FreeSWITCH(fsConfig.getHomeDir());
            boolean isInternalUse  = fsConfig.isInternalUse();
            if(!isInternalUse){
                String extIp = IPUtil.getExtIp();
                log.info("当前为公网部署，需要设置rtp地址为公网地址为[{}]", extIp);
                freeSwitch.getModGlobal().setExtRtpIp(extIp);
                freeSwitch.getModGlobal().setExtSipIp(extIp);
                freeSwitch.getModGlobal().reloadSofia();
                if(fsConfig.getRole().equals(Constant.FSAGENT_ROLE_TOAGENT)){
                    log.info("开始设置verto的公网ip[{}]", extIp);
                    freeSwitch.getModVerto().setExtRtpIp(extIp);
                    freeSwitch.getModVerto().reload();
//                    //调用toagentserver的initCallcenter接口，将数据库中的数据加载到freeswitch中
//                    agentGroup.initCallcenter();
                }
            }else{
                log.info("当前为内网部署，没有必要设置公网ip地址");
            }

            if (fsConfig.getRole().equals(Constant.FSAGENT_ROLE_LINE_TRUNK) || fsConfig.getRole().equals(Constant.FSAGENT_ROLE_LINE_REGISTER)) {//如果是line角色的freeswitch，需要init自己的线路到本地
                log.info("服务[{}]为[{}]角色，需加载线路信息到本地", serviceId, fsConfig.getRole());
                reloadLine(serviceId);
            }else if(fsConfig.getRole().equals(Constant.FSAGENT_ROLE_LINE_SIMCARD)){
                log.info("服务[{}]为line_sim角色，需加载线路信息和账号信息到本地");
                reloadLine(serviceId);
                reloadAccount(serviceId);
            }
        } catch (Exception e) {
            log.warn("初始化fsagent出现异常", e);
            //TODO: 报警
        }
    }

    public FreeSWITCH getFreeSwitch() {
        return freeSwitch;
    }

    /**
     * 启动时执行，下载所有的line文件
     */
    private void reloadLine(String serviceId) {
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperation.linexmlinfosByFsagentId(serviceId);
        if (!result.getCode().equals(Constant.SUCCESS_COMMON) || result.getBody() == null) {
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS);
        }
        List<LineXmlnfoVO> lineList = result.getBody();
        LineOperUtil.ergodicLine(lineList, freeSwitch, fsConfig.getRole());
    }

    /**
     * 启动时执行，加载创建sim卡网关的账号
     */
    private void reloadAccount(String serviceId) {
        Result.ReturnData<List<SimCardVO>> result = simCard.getGatewayAccount(serviceId);
        if (!result.getCode().equals(Constant.SUCCESS_COMMON) || result.getBody() == null) {
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS);
        }
        List<SimCardVO> simCardVOList = result.getBody();
        if(simCardVOList!=null&&simCardVOList.size()>0){
            for (SimCardVO simCardVO :simCardVOList) {
                createAccount(simCardVO);
            }
        }
    }

    /**
     * 创建用户
     * @param simCardVO
     */
    public void createAccount(SimCardVO simCardVO){
        int startCount = simCardVO.getStartCount();
        int countsStep = simCardVO.getCountsStep();
        int startPwd = simCardVO.getStartPwd();
        int pwdStep = simCardVO.getPwdStep();
        int countNum = simCardVO.getCountNum();
        String cmd1 = String.format("lua create_user.lua %s %s", startCount, startPwd);
        freeSwitch.executeAsync(cmd1);//创建用户
        for (int i = 1; i < countNum; i++) {
            startCount = startCount + countsStep;
            startPwd = startPwd + pwdStep;
            String cmd = String.format("lua create_user.lua %s %s", startCount, startPwd);
            freeSwitch.executeAsync(cmd);//创建用户
        }
    }

}
