package com.guiji.fsagent.entity;


import com.guiji.fsagent.manager.FsEslClient;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;


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

    public FreeSWITCH( String baseDir){
        this.baseDir = baseDir;
        this.confDir = baseDir + "/conf";
        this.cdrDir = baseDir + "/log";
        this.cdrDir = baseDir + "/log/cdr-csv";
        this.dbDir = baseDir + "/db";
        this.fscli = baseDir + "/fscli";
        this.scripts = baseDir + "/conf/scripts";
        this.dialplan =baseDir + "/conf/dialplan/default";
        this.gateway = baseDir+"/conf/sip_profiles/external";
        initFsManager();
        initGlobalVar();
    }

    public Logger getLogger() {
        return logger;
    }

    public String getFsName() {
        return fsName;
    }

    public void setFsName(String fsName) {
        this.fsName = fsName;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getCdrDir() {
        return cdrDir;
    }

    public void setCdrDir(String cdrDir) {
        this.cdrDir = cdrDir;
    }

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public String getDbDir() {
        return dbDir;
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public String getConfDir() {
        return confDir;
    }

    public void setConfDir(String confDir) {
        this.confDir = confDir;
    }

    public String getFscli() {
        return fscli;
    }

    public void setFscli(String fscli) {
        this.fscli = fscli;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    public String getDialplan() {
        return dialplan;
    }

    public void setDialplan(String dialplan) {
        this.dialplan = dialplan;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public GlobalVar getGlobalVar() {
        return globalVar;
    }

    public void setGlobalVar(GlobalVar globalVar) {
        this.globalVar = globalVar;
    }

    public FsEslClient getFsEslClient() {
        return fsEslClient;
    }

    public void setFsEslClient(FsEslClient fsEslClient) {
        this.fsEslClient = fsEslClient;
    }

    public String getFsEslPort() {
        return fsEslPort;
    }

    public void setFsEslPort(String fsEslPort) {
        this.fsEslPort = fsEslPort;
    }

    public String getFsEslPwd() {
        return fsEslPwd;
    }

    public void setFsEslPwd(String fsEslPwd) {
        this.fsEslPwd = fsEslPwd;
    }

    /**
     * 初始化全局变量
     */
    private void initGlobalVar() {
        globalVar = new GlobalVar();
        globalVar.setDomain(fsEslClient.execute("global_getvar domain"));
        globalVar.setExternal_sip_port(fsEslClient.execute("global_getvar external_sip_port"));
        globalVar.setGc_docker_ip(fsEslClient.execute("global_getvar gc_docker_ip"));
        globalVar.setInternal_sip_port(fsEslClient.execute("global_getvar internal_sip_port1"));

        logger.debug("freeswitch[{}]全局变量为[{}]", fsName, globalVar.toString());
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
        fsEslClient = new FsEslClient("127.0.0.1", eslPort, elsPwd);
    }

//    public void rotateCdr(){
//        try {
//            fsEslClient.rotateCdr();
//        }catch (Exception ex){
//            logger.warn("在回卷[{}]cdr时出现异常" + ex.getMessage(), fsName);
//        }
//    }
//
//    /**
//     * 获取所有回卷后的cdr文件
//     * @return
//     */
//    public List<String> getRotatedCdrFiles(){
//        List<String> cdrFiles = new ArrayList<>();
//
//        //获取freeswitch所有的cdr文件
//        File cdrFile = new File(this.cdrDir);
//        //只获取csv文件
//        if(cdrFile.exists()){
//            File[] fileList = cdrFile.listFiles((dir, name) -> name.startsWith("Master.csv.") && !name.equals("Master.csv") ? true : false);
//            if(fileList!=null){
//                for(File f: fileList){
//                    cdrFiles.add(f.getAbsolutePath());
//                }
//            }
//        }
//
//        return cdrFiles;
//    }

    public String execute(String cmd){
        return fsEslClient.execute(cmd);
    }

    public List<String> executeapi(String cmd){
        return fsEslClient.api(cmd);
    }
}
