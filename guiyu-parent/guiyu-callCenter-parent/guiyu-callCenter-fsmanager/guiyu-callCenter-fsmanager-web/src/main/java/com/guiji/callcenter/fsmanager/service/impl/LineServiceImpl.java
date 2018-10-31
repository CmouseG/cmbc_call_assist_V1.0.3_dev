package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.fsmanager.entity.DialplanEntity;
import com.guiji.callcenter.fsmanager.entity.GatewayEntity;
import com.guiji.callcenter.fsmanager.service.LineService;
import com.guiji.callcenter.fsmanager.util.FileUtil;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class LineServiceImpl implements LineService {

    /**
     * 增加线路
     * @param filePath
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Boolean addLineinfos(String filePath, LineInfo  request) throws Exception {
       String filePathDialplan = filePath+"01_"+request.getLineId()+".xml";
       String filePathGateway = filePath+"gw_"+request.getLineId()+".xml";
        //判断有无同名的拨号方案和网关存在，防止重名覆盖
        if(FileUtil.isExist(filePathDialplan)||FileUtil.isExist(filePathGateway)){
            return false;
        }
        buildGateway(filePathGateway, request);
        buildDialplan(filePathDialplan, request);
        return true;
    }

    @Override
    public void editLineinfos(String filePath,String lineId, LineInfo request)throws Exception{
        String filePathDialplan = filePath+"01_"+lineId+".xml";
        String filePathGateway = filePath+"gw_"+lineId+".xml";
        //先删除原来的配置文件
        FileUtil.delete(filePathDialplan);
        FileUtil.delete(filePathGateway);
        request.setLineId(lineId);
        //生成新的文件
        buildGateway(filePathGateway, request);
        buildDialplan(filePathDialplan, request);
    }

    /**
     * 删除线路
     * @param filePath
     * @param lineId
     */
    @Override
    public void deleteLineinfos(String filePath,String lineId){
        FileUtil.delete(filePath+"01_"+lineId+".xml");
        FileUtil.delete(filePath+"gw_"+lineId+".xml");
    }


    @Override
    public List<LineXmlnfo> linexmlinfos(String filePath,String lineId) {
        String filePathDialplan = filePath+"01_"+lineId+".xml";
        String filePathGateway = filePath+"gw_"+lineId+".xml";
        List<LineXmlnfo> list = new ArrayList<LineXmlnfo>();
        try {
            if (FileUtil.isExist(filePathDialplan)) {
                LineXmlnfo info = new LineXmlnfo();
                info.setConfigType("dialplan");
                info.setFileName("01_" + lineId + ".xml");
                String base = FileUtil.fileToBase64(filePathDialplan);
                info.setFileData(base);
                list.add(info);
            }
            if (FileUtil.isExist(filePathGateway)) {
                LineXmlnfo info = new LineXmlnfo();
                info.setConfigType("gateway");
                info.setFileName("gw_" + lineId + ".xml");
                String base = FileUtil.fileToBase64(filePathGateway);
                info.setFileData(base);
                list.add(info);
            }
        }catch (Exception e){

        }

        return list;
    }

    @Override
    public List<LineXmlnfo> linexmlinfosAll(String filepath) {
        List<LineXmlnfo> list = new ArrayList<LineXmlnfo>();
        try {
            File[] files = new File(filepath).listFiles();
            for (File file : files) {
                LineXmlnfo info = new LineXmlnfo();
                String fileName = file.getName();
                info.setFileName(fileName);
                if (fileName.indexOf("gw_") > 0) {
                    info.setConfigType("gateway");
                } else {
                    info.setConfigType("dialplan");
                }
                String base = FileUtil.fileToBase64(fileName);
                info.setFileData(base);
                list.add(info);
            }
        }catch (Exception e){

        }
        return list;

    }


    /**
     * 生成网关
     * @param filePath
     * @param request
     */
    public void buildGateway(String filePath, LineInfo  request)throws Exception{
        LinkedHashMap<String, String> gatewayMap = new LinkedHashMap<String, String>();
        GatewayEntity include = new GatewayEntity();
        GatewayEntity.Gateway gateway = include.new Gateway();
        gateway.setName(request.getLineId());

        gatewayMap.put("username", "user");
        gatewayMap.put("password", "pwd");
        String realm=request.getSipIp()+":"+request.getSipPort();
        gatewayMap.put("realm", realm);
        gatewayMap.put("from-domain", realm);
        if (request.getCallerNum() != null && !request.getCallerNum().isEmpty()) {
            gatewayMap.put("caller-id-in-from", "true");
        }
        gatewayMap.put("expire-seconds","600" );
        gatewayMap.put("register", "false");

        LinkedHashSet<GatewayEntity.Param> ParamSet = new LinkedHashSet<GatewayEntity.Param>();
        for (Object key : gatewayMap.keySet()) {
            GatewayEntity.Param param = include.new Param();
            if (gatewayMap.get(key).trim().length() > 0) {
                param.setName(key.toString());
                param.setValue(gatewayMap.get(key));
                ParamSet.add(param);
            }
        }
        gateway.setParam(ParamSet);
        include.setGateway(gateway);
        File file = new File(filePath);
        buildxml2(include,file);
    }

    /**
     * 生成拨号方案
     * @param filePath
     * @param request
     * @throws Exception
     */
    public void buildDialplan(String filePath, LineInfo  request) throws Exception{
        DialplanEntity include = new DialplanEntity();
        DialplanEntity.Extension extension = include.new Extension();
        extension.setName(request.getLineId() + "_Extension");

        DialplanEntity.Condition condition = include.new Condition();
        condition.setField("caller_id_name");
        condition.setExpression("^" + request.getLineId() + "\\d{0,2}$");

        LinkedHashSet<DialplanEntity.Action> ActionSet = new LinkedHashSet<DialplanEntity.Action>();
        DialplanEntity.Action action = include.new Action();
        action.setData("ringback=${us-ring}");
        action.setApplication("set");
        ActionSet.add(action);

        if(request.getCodec()!=null&&request.getCodec().trim().isEmpty()){
            DialplanEntity.Action action1 = include.new Action();
            action1.setData("nolocal:absolute_codec_string=G729");
            action1.setApplication("export");
            ActionSet.add(action1);
        }

        if (request.getCallerNum() != null && !request.getCallerNum().trim().isEmpty()) {
            DialplanEntity.Action action1 = include.new Action();
            action1.setData("callerselector.lua " + request.getCallerNum());
            action1.setApplication("lua");
            ActionSet.add(action1);

            DialplanEntity.Action action2 = include.new Action();
            action2.setData("info final callerid:${effective_caller_id_number}");
            action2.setApplication("log");
            ActionSet.add(action2);
        }
        DialplanEntity.Action action2 = include.new Action();
        if (request.getCalleePrefix() != null && !request.getCalleePrefix().trim().isEmpty()) {
            action2.setData("sofia/gateway/gw_" + request.getLineId() + "/" + request.getCalleePrefix().trim() + "${destination_number}");
        } else {
            action2.setData("sofia/gateway/gw_" + request.getLineId() + "/${destination_number}");
        }
        action2.setApplication("bridge");
        ActionSet.add(action2);

        condition.setAction(ActionSet);
        extension.setCondition(condition);
        include.setExtension(extension);
        File file = new File(filePath);
        buildxml(include, file);

    }

    /**
     * 生成xml
     *
     * @param include
     * @param file
     * @throws Exception
     */
    public void buildxml(DialplanEntity include, File file) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(DialplanEntity.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        jaxbMarshaller.marshal(include, file);
        jaxbMarshaller.marshal(include, System.out);
    }

    /**
     * 生成xml
     *
     * @param include
     * @param file
     * @throws Exception
     */
    public void buildxml2(GatewayEntity include, File file) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(GatewayEntity.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        jaxbMarshaller.marshal(include, file);
        jaxbMarshaller.marshal(include, System.out);
    }

}
