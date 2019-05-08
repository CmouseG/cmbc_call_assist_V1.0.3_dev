package com.guiji.fsagent.fs.mod;

import com.guiji.fsagent.entity.FreeSWITCH;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: 魏驰
 * @Date: 2019/4/1 16:44
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
public class ModGlobal extends FsMod{
    FreeSWITCH freeSWITCH;

    public ModGlobal(FreeSWITCH freeSWITCH){
        this.freeSWITCH = freeSWITCH;
        this.setFilePath(freeSWITCH.getConfDir() + "/vars.xml");
    }

    public void setExtRtpIp(String extRtpIp){
        log.info("设置全局变量，ext_rtp_ip->[{}]", extRtpIp);
        setConfig("ext_rtp_ip", extRtpIp);
    }

    public void setExtSipIp(String extSipIp){
        log.info("设置全局变量，ext_sip_ip->[{}]", extSipIp);
        setConfig("ext_sip_ip", extSipIp);
    }

    public void reloadSofia(){
        freeSWITCH.execute("reloadxml");
        freeSWITCH.execute("reload mod_sofia");
    }

    @Override
    public void reload(){
        freeSWITCH.execute("reloadxml");
        //TODO： 需要重启freeswitch
    }
}
