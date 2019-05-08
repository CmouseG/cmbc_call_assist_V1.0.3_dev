package com.guiji.fs;

import com.guiji.fs.module.ModCallCenter;
import com.guiji.fs.module.ModVerto;
import com.guiji.fs.pojo.GlobalVar;
import com.guiji.fs.xmlpojo.XConfiguration;
import com.guiji.fsmanager.entity.FsBindVO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
public class FreeSWITCH {
    private final Logger logger = LoggerFactory.getLogger(FreeSWITCH.class);

    private ModCallCenter callCenter;
    private ModVerto modVerto;
    private XConfiguration eventSocketConfig;
    private GlobalVar globalVar;
    private FsEslClient fsEslClient;
    private FsEventHandler fsEventHandler;
    private String configPath;


    public FreeSWITCH(FsBindVO fsBindInfo, FsEventHandler fsEventHandler,String configPath){
        this.fsEventHandler = fsEventHandler;
        this.configPath = configPath;
        initFsManager(fsBindInfo);
        initGlobalVar();
        callCenter = new ModCallCenter(this);
        modVerto = new ModVerto(this);
    }

    /**
     * 初始化全局变量
     */
    private void initGlobalVar() {
        globalVar = new GlobalVar();
        globalVar.setDomain(fsEslClient.execute("global_getvar domain"));
        globalVar.setExternal_sip_port(fsEslClient.execute("global_getvar external_sip_port"));
        globalVar.setGc_docker_ip(fsEslClient.execute("global_getvar gc_docker_ip"));
        globalVar.setInternal_sip_port(fsEslClient.execute("global_getvar internal_sip_port"));
        globalVar.setVerto_wss_port(fsEslClient.execute("global_getvar verto_wss_port"));
        globalVar.setDefault_password(fsEslClient.execute("global_getvar default_password"));

        logger.info("freeswitch全局变量为[{}]", globalVar.toString());
    }

    /**
     * 从配置文件中读取esl相关信息，并初始化FsManager
     */
    private void initFsManager(FsBindVO fsBindInfo) {
        String fsAgentAddr = fsBindInfo.getFsAgentAddr();
        if(fsAgentAddr.contains(":")){
            fsAgentAddr = fsAgentAddr.split(":")[0];
        }
        fsEslClient = new FsEslClient( fsAgentAddr,fsBindInfo.getFsEslPort(), fsBindInfo.getFsEslPwd(), fsEventHandler);
        fsEslClient.getFsClient();
    }

    public void reloadXml(){
       execute("reloadxml") ;
    }

    public String execute(String cmd){
        return fsEslClient.execute(cmd);
    }

    public String  executeAsync(String cmd){
        return fsEslClient.executeAsync(cmd);
    }
    public List<String> api(String cmd){
        return fsEslClient.api(cmd);
    }


}
