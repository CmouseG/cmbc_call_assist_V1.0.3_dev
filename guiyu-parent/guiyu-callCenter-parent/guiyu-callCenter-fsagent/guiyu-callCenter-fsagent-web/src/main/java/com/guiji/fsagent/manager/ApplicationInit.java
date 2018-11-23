package com.guiji.fsagent.manager;

import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.util.Base64Util;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(ApplicationInit.class);
    @Autowired
    IFsResource iFsResource;
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    ILineOper lineOperApiFeign;
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
            serviceId = serviceId.substring(0, serviceId.lastIndexOf(":"));
            freeSwitch = new FreeSWITCH(fsConfig.getHomeDir(), serviceId);
            ReloadLine();
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
    private void ReloadLine() {
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperApiFeign.linexmlinfosAll();
        List<LineXmlnfoVO> lineList = result.getBody();
        for (LineXmlnfoVO line : lineList) {
            String fileName = line.getFileName();
            if (line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)) {
                Base64Util.base64ToFile(line.getFileData(), freeSwitch.getDialplan() + fileName);
            } else if (line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)) {
                Base64Util.base64ToFile(line.getFileData(), freeSwitch.getGateway() + fileName);
                //执行esl命令卸载网关
                freeSwitch.execute("sofia profile external killgw " + fileName.substring(0, fileName.lastIndexOf(".")));
            }
        }
        //执行esl命令加载网关
        freeSwitch.execute("sofia profile external rescan reloadxml");
    }
}
