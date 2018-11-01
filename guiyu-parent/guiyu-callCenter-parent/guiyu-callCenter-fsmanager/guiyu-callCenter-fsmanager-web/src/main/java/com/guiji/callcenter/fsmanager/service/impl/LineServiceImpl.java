package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.dao.LineConfigMapper;
import com.guiji.callcenter.dao.entity.LineConfig;
import com.guiji.callcenter.dao.entity.LineConfigExample;
import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.entity.DialplanVO;
import com.guiji.callcenter.fsmanager.entity.GatewayVO;
import com.guiji.callcenter.fsmanager.service.LineService;
import com.guiji.callcenter.fsmanager.util.FileUtil;
import com.guiji.callcenter.fsmanager.util.XmlUtil;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class LineServiceImpl implements LineService {
    @Autowired
    LineConfigMapper lineConfigMapper;

    /**
     * 增加线路
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Boolean addLineinfos(LineInfo  request) throws Exception {
        //查询数据库，看有无重名
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(request.getLineId());
        List<LineConfig>  list =lineConfigMapper.selectByExample(example);
        if(list.size()>0){
            return false;
        }


        String gatewayxml = buildGateway( request);
        LineConfig recordgw = new LineConfig();
        recordgw.setLineId(request.getLineId());
        recordgw.setFileType(Constant.CONFIG_TYPE_GATEWAY);
        recordgw.setFileName("gw_"+request.getLineId()+".xml");
        recordgw.setFileData(gatewayxml);
        lineConfigMapper.insert(recordgw);

        String dialplanxml = buildDialplan(request);
        LineConfig recorddl = new LineConfig();
        recorddl.setLineId(request.getLineId());
        recorddl.setFileType(Constant.CONFIG_TYPE_DIALPLAN);
        recorddl.setFileName("01_"+request.getLineId()+".xml");
        recorddl.setFileData(gatewayxml);
        lineConfigMapper.insert(recorddl);


        return true;
    }

    @Override
    public void editLineinfos(String filePath,String lineId, LineInfo request)throws Exception{
        //先删除在

//
//        String filePathDialplan = filePath+"01_"+lineId+".xml";
//        String filePathGateway = filePath+"gw_"+lineId+".xml";
//        //先删除原来的配置文件
//        FileUtil.delete(filePathDialplan);
//        FileUtil.delete(filePathGateway);
        request.setLineId(lineId);
        //生成新的文件
        buildGateway( request);
        buildDialplan(request);

        String gatewayxml = buildGateway( request);
        String dialplanxml = buildDialplan(request);


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
                info.setConfigType(Constant.CONFIG_TYPE_DIALPLAN);
                info.setFileName("01_" + lineId + ".xml");
                String base = FileUtil.fileToBase64(filePathDialplan);
                info.setFileData(base);
                list.add(info);
            }
            if (FileUtil.isExist(filePathGateway)) {
                LineXmlnfo info = new LineXmlnfo();
                info.setConfigType(Constant.CONFIG_TYPE_GATEWAY);
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
                    info.setConfigType(Constant.CONFIG_TYPE_GATEWAY);
                } else {
                    info.setConfigType(Constant.CONFIG_TYPE_DIALPLAN);
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
     * @param request
     */
    public String buildGateway(LineInfo  request)throws Exception{
        LinkedHashMap<String, String> gatewayMap = new LinkedHashMap<String, String>();
        GatewayVO include = new GatewayVO();
        GatewayVO.Gateway gateway = new GatewayVO.Gateway();
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

        LinkedHashSet<GatewayVO.Param> ParamSet = new LinkedHashSet<GatewayVO.Param>();
        for (Object key : gatewayMap.keySet()) {
            GatewayVO.Param param = new  GatewayVO.Param();
            if (gatewayMap.get(key).trim().length() > 0) {
                param.setName(key.toString());
                param.setValue(gatewayMap.get(key));
                ParamSet.add(param);
            }
        }
        gateway.setParam(ParamSet);
        include.setGateway(gateway);
        return XmlUtil.buildxml(include);
    }

    /**
     * 生成拨号方案
     * @param request
     * @throws Exception
     */
    public String buildDialplan(LineInfo  request) throws Exception{
        DialplanVO include = new DialplanVO();
        DialplanVO.Extension extension = new DialplanVO.Extension();
        extension.setName(request.getLineId() + "_Extension");

        DialplanVO.Condition condition = new DialplanVO.Condition();
        condition.setField("caller_id_name");
        condition.setExpression("^" + request.getLineId() + "\\d{0,2}$");

        LinkedHashSet<DialplanVO.Action> ActionSet = new LinkedHashSet<DialplanVO.Action>();
        DialplanVO.Action action = new DialplanVO.Action();
        action.setData("ringback=${us-ring}");
        action.setApplication("set");
        ActionSet.add(action);

        if(request.getCodec()!=null&&request.getCodec().trim().isEmpty()){
            DialplanVO.Action action1 = new DialplanVO.Action();
            action1.setData("nolocal:absolute_codec_string=G729");
            action1.setApplication("export");
            ActionSet.add(action1);
        }

        if (request.getCallerNum() != null && !request.getCallerNum().trim().isEmpty()) {
            DialplanVO.Action action1 =new DialplanVO.Action();
            action1.setData("callerselector.lua " + request.getCallerNum());
            action1.setApplication("lua");
            ActionSet.add(action1);

            DialplanVO.Action action2 = new DialplanVO.Action();
            action2.setData("info final callerid:${effective_caller_id_number}");
            action2.setApplication("log");
            ActionSet.add(action2);
        }
        DialplanVO.Action action2 = new DialplanVO.Action();
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
        return XmlUtil.buildxml(include);
    }

}
