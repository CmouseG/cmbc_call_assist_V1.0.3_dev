package com.guiji.fsagent.fs.mod;

import com.guiji.fsagent.entity.FreeSWITCH;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: 魏驰
 * @Date: 2019/4/1 16:45
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
public class ModVerto extends FsMod{
    FreeSWITCH freeSWITCH;

    public ModVerto(FreeSWITCH freeSWITCH){
        this.freeSWITCH = freeSWITCH;
        this.setFilePath(freeSWITCH.getConfDir() + "/autoload_configs/verto.conf.xml");
    }

    public void setExtRtpIp(String extRtpIp){
        log.info("开始设置公网verto地址[{}], 配置文件为[{}]", extRtpIp, this.getFilePath());
        setConfig("ext-rtp-ip", extRtpIp);
    }

    @Override
    public void reload(){
        freeSWITCH.execute("reload mod_verto");
    }
}
