package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.dao.LineConfigMapper;
import com.guiji.callcenter.dao.entity.LineConfig;
import com.guiji.callcenter.dao.entity.LineConfigExample;
import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.config.GatewayConfig;
import com.guiji.callcenter.fsmanager.entity.DialplanVO;
import com.guiji.callcenter.fsmanager.entity.GatewayVO;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.LineService;
import com.guiji.callcenter.fsmanager.util.XmlUtil;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ILineOperate;
import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import com.guiji.utils.FeignBuildUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LineServiceImpl implements LineService {
    private final Logger logger = LoggerFactory.getLogger(LineServiceImpl.class);

    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    LineConfigMapper lineConfigMapper;
    @Autowired
    GatewayConfig gatewayConfig;
    /**
     * 增加线路
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public boolean addLineinfos(LineInfoVO  request)  {
        String lineId = request.getLineId();
        //查询数据库，看有无重名
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(lineId);
        List<LineConfig>  list =lineConfigMapper.selectByExample(example);
        if(list.size()>0){
            return false;
        }
        String gatewayxml = buildGateway( request);
        LineConfig recordgw = new LineConfig();
        recordgw.setLineId(lineId);
        recordgw.setFileType(Constant.CONFIG_TYPE_GATEWAY);
        recordgw.setFileName("gw_"+lineId+".xml");
        recordgw.setFileData(gatewayxml);
        lineConfigMapper.insert(recordgw);

        String dialplanxml = buildDialplan(request);
        LineConfig recorddl = new LineConfig();
        recorddl.setLineId(lineId);
        recorddl.setFileType(Constant.CONFIG_TYPE_DIALPLAN);
        recorddl.setFileName("01_"+lineId+".xml");
        recorddl.setFileData(dialplanxml);
        lineConfigMapper.insert(recorddl);
        //调用通知fsagent更新方法
        updatenotify(lineId);
        return true;
    }

    @Override
    public void editLineinfos(String lineId, LineInfoVO request){
        Map<String ,String> map = new HashMap<String,String>();
        map.put(Constant.CONFIG_TYPE_DIALPLAN,"01_"+lineId+".xml");
        map.put(Constant.CONFIG_TYPE_GATEWAY,"gw_"+lineId+".xml");
        request.setLineId(lineId);
        for (String key : map.keySet()) {
            String fileName = map.get(key);
            LineConfig record = new LineConfig();
            record.setLineId(request.getLineId());
            record.setFileType(key);
            record.setFileName(fileName);
            if(key.equals(Constant.CONFIG_TYPE_DIALPLAN)){
                record.setFileData(buildDialplan(request));
                LineConfigExample example = new LineConfigExample();
                LineConfigExample.Criteria criteria = example.createCriteria();
                criteria.andLineIdEqualTo(request.getLineId());
                criteria.andFileTypeEqualTo(Constant.CONFIG_TYPE_DIALPLAN);
                lineConfigMapper.updateByExampleSelective(record,example);
            }else{
                record.setFileData( buildGateway(request));
                LineConfigExample example = new LineConfigExample();
                LineConfigExample.Criteria criteria = example.createCriteria();
                criteria.andLineIdEqualTo(request.getLineId());
                criteria.andFileTypeEqualTo(Constant.CONFIG_TYPE_GATEWAY);
                lineConfigMapper.updateByExampleSelective(record,example);
            }
        }
        //调用通知fsagent更新方法
        updatenotify(lineId);
    }

    /**
     * 删除线路
     * @param lineId
     */
    @Override
    public void deleteLineinfos(String lineId){
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(lineId);
        lineConfigMapper.deleteByExample(example);
        deletenotify(lineId);
    }


    @Override
    public List<LineXmlnfoVO> linexmlinfos(String lineId) {
        XmlUtil util = new XmlUtil();
        List<LineXmlnfoVO> listLine = new ArrayList<LineXmlnfoVO>();
        //查询数据库
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(lineId);
        List<LineConfig> list =lineConfigMapper.selectByExample(example);

        for (LineConfig config:list) {
            LineXmlnfoVO lineXmlnfo = new LineXmlnfoVO();
            lineXmlnfo.setConfigType(config.getFileType());
            lineXmlnfo.setFileName(config.getFileName());
            lineXmlnfo.setFileData(util.getBase64(config.getFileData()));
            listLine.add(lineXmlnfo);
        }
        return listLine;
    }

    @Override
    public List<LineXmlnfoVO> linexmlinfosAll() {
        XmlUtil util = new XmlUtil();
        List<LineXmlnfoVO> listLine = new ArrayList<LineXmlnfoVO>();
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        List<LineConfig> list =lineConfigMapper.selectByExample(example);
        for (LineConfig config:list) {
            LineXmlnfoVO lineXmlnfo = new LineXmlnfoVO();
            lineXmlnfo.setConfigType(config.getFileType());
            lineXmlnfo.setFileName(config.getFileName());
            lineXmlnfo.setFileData(util.getBase64(config.getFileData()));
            listLine.add(lineXmlnfo);
        }
        return listLine;

    }


    /**
     * 生成网关
     * @param request
     */
    public String buildGateway(LineInfoVO  request){
        LinkedHashMap<String, String> gatewayMap = new LinkedHashMap<String, String>();
        GatewayVO include = new GatewayVO();
        GatewayVO.Gateway gateway = new GatewayVO.Gateway();
        gateway.setName("gw_"+request.getLineId());
        gatewayMap.put("username", gatewayConfig.getUsername());
        gatewayMap.put("password", gatewayConfig.getPassword());
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

        XmlUtil util  = new XmlUtil();

        return util.buildxml(include);
    }

    /**
     * 生成拨号方案
     * @param request
     * @throws Exception
     */
    public String buildDialplan(LineInfoVO  request){
        DialplanVO include = new DialplanVO();
        DialplanVO.Extension extension = new DialplanVO.Extension();
        extension.setName(request.getLineId() + "_Extension");

        DialplanVO.Condition condition = new DialplanVO.Condition();
        condition.setField("caller_id_name");
        condition.setExpression("^" + request.getLineId() + "$");

        LinkedHashSet<DialplanVO.Action> ActionSet = new LinkedHashSet<DialplanVO.Action>();
        DialplanVO.Action action = new DialplanVO.Action();
        action.setData("ringback=${us-ring}");
        action.setApplication("set");
        ActionSet.add(action);

        if(request.getCodec()!=null&&request.getCodec().trim().isEmpty()){
            DialplanVO.Action action1 = new DialplanVO.Action();
            action1.setData("nolocal:absolute_codec_string="+request.getCodec());
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

        XmlUtil util  = new XmlUtil();
        return util.buildxml(include);
    }

    /**
     * 通知fsagent更新配置文件
     * @param lineId
     */
    public void updatenotify(String lineId){
        //通知所有的fsagent服务更新线路
        List<String> serverList =  eurekaManager.getInstances(Constant.SERVER_NAME_FSAGENT);
        for(String server:serverList){
            ILineOperate lineOperateApi = FeignBuildUtil.feignBuilderTarget(ILineOperate.class,Constant.PROTOCOL +server);
            //调用fsagent通知更新接口
            Result.ReturnData<Boolean> result = lineOperateApi.updatenotify("line",lineId);
            if(!result.getCode().equals("0")){
                logger.info("通知[{}]这个fsagent更新接口失败",server);
                //TODO --告警
            }
        }
    }
    /**
     * 通知fsagent删除配置文件
     * @param lineId
     */
    public void deletenotify(String lineId){
        //通知所有的fsagent服务更新线路
        List<String> serverList =  eurekaManager.getInstances(Constant.SERVER_NAME_FSAGENT);
        for(String server:serverList){
            ILineOperate lineOperateApi = FeignBuildUtil.feignBuilderTarget(ILineOperate.class,Constant.PROTOCOL +server);
            //调用fsagent删除线路接口
            Result.ReturnData<Boolean> result = lineOperateApi.deleteLineinfos(lineId);
            if(!result.getCode().equals("0")){
                logger.info("通知[{}]这个fsagent删除接口失败",server);
                //TODO --告警
            }
        }
    }

}
