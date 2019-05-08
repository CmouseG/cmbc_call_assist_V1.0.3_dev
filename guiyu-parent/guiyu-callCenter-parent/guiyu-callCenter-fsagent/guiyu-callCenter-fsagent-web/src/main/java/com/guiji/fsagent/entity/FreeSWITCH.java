package com.guiji.fsagent.entity;

import com.guiji.fsagent.fs.mod.ModGlobal;
import com.guiji.fsagent.fs.mod.ModVerto;
import com.guiji.fsagent.manager.FsEslClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Data
@Slf4j
public class FreeSWITCH {
    private final Logger logger = LoggerFactory.getLogger(FreeSWITCH.class);
    private String fsName;
    private String baseDir;
    private String cdrDir;
    private String logDir;
    private String dbDir;
    private String confDir;
    private String fscli;
    private String scripts;
    private String dialplan;
    private String gateway;
    private GlobalVar globalVar;

    private FsEslClient fsEslClient;

    private String fsEslPort;

    private String fsEslPwd;

    private ModGlobal modGlobal;
    private ModVerto modVerto;

    public FreeSWITCH( String baseDir){
        this.baseDir = baseDir;
        this.confDir = baseDir + "/conf";
        this.cdrDir = baseDir + "/log";
        this.cdrDir = baseDir + "/log/cdr-csv";
        this.dbDir = baseDir + "/db";
        this.fscli = baseDir + "/fscli";
        this.scripts = baseDir + "/conf/scripts";
        this.dialplan =baseDir + "/conf/dialplan/default/";
        this.gateway = baseDir+"/conf/sip_profiles/external/";
        initFsManager();

        modGlobal = new ModGlobal(this);
        modVerto = new ModVerto(this);
    }

    public GlobalVar getGlobalVar(){
        if(globalVar == null){
            initGlobalVar();
        }

        return globalVar;
    }

    /**
     * 初始化全局变量
     */
    private void initGlobalVar() {
        globalVar = new GlobalVar();
        globalVar.setDomain(fsEslClient.execute("global_getvar domain"));
        globalVar.setExternal_sip_port(fsEslClient.execute("global_getvar external_sip_port"));
        globalVar.setGc_docker_ip(fsEslClient.execute("global_getvar gc_docker_ip"));
        globalVar.setInternal_sip_port1(fsEslClient.execute("global_getvar internal_sip_port1"));
        globalVar.setInternal_sip_port(fsEslClient.execute("global_getvar internal_sip_port"));
        globalVar.setExt_sip_ip(fsEslClient.execute("global_getvar ext_sip_ip"));
        logger.info("freeswitch[{}]全局变量为[{}]", fsName, globalVar.toString());
    }

    /**
     * 从配置文件中读取esl相关信息，并初始化FsManager
     */
    private void initFsManager() {
        String eslPort = "";
        String elsPwd = "";
        File file = new File(this.confDir + "/autoload_configs/event_socket.conf.xml");

        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(file);
            Element rootElement = doc.getRootElement();
            Element settings = rootElement.getChild("settings");
            List<Element> children = settings.getChildren();
            for(Element ele: children){
                Attribute attr = ele.getAttribute("name");
                if("listen-port".equals(attr.getValue())){
                    eslPort = ele.getAttribute("value").getValue();
                }else if("password".equals(attr.getValue())){
                    elsPwd = ele.getAttribute("value").getValue();
                }
            }
        } catch (Exception e) {
            logger.warn("在读取event_socket.conf.xml文件出现异常, fs:"+fsName, e);
        }
        fsEslPort=eslPort;
        fsEslPwd = elsPwd;
        log.info("获取esl配置，port[{}], pwd[{}]", eslPort, elsPwd);
        fsEslClient = new FsEslClient("127.0.0.1", eslPort, elsPwd);
    }


    public String execute(String cmd){
        return fsEslClient.execute(cmd);
    }

    public String executeAsync(String cmd){
        return fsEslClient.executeAsync(cmd);
    }
    public List<String> executeapi(String cmd){
        return fsEslClient.api(cmd);
    }
}
