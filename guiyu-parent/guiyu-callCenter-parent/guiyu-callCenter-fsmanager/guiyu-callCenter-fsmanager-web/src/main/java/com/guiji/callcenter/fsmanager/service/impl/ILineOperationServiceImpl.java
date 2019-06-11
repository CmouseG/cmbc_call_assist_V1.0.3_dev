package com.guiji.callcenter.fsmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.guiji.callcenter.dao.LineConfigMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.LineConfig;
import com.guiji.callcenter.dao.entity.LineConfigExample;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.callcenter.dao.entity.LineInfoExample;
import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.config.FsmanagerExceptionEnum;
import com.guiji.callcenter.fsmanager.entity.DialplanVO;
import com.guiji.callcenter.fsmanager.entity.GatewayVO;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.ILineOperationService;
import com.guiji.callcenter.fsmanager.service.ISimCardService;
import com.guiji.callcenter.fsmanager.util.XmlUtil;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsmanager.entity.*;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.FeignBuildUtil;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class ILineOperationServiceImpl implements ILineOperationService {
    @Autowired
    LineInfoMapper lineInfoMapper;
    @Autowired
    LineConfigMapper lineConfigMapper;
    @Autowired
    ISimCardService simCardService;
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    IAgentGroup agentGroupApiFeign;
    @Autowired
    ILineOperationService lineOperationService;
    @Value("${rabbit.general.fanout.exchange:}")
    private String fanoutExchange;

    @Resource
    private FanoutSender fanoutSender;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<LineInfoVO> batchAddLineInfo(List<OutLineInfoAddReq> outLineInfoAddReqList) {
        List<LineInfoVO> queryList = new ArrayList<>();
        for (OutLineInfoAddReq outLineInfoAddReq:outLineInfoAddReqList) {
            LineInfoVO lineInfoVO = new LineInfoVO();
            lineInfoVO.setLineId(lineOperationService.addLineInfo(outLineInfoAddReq)+"");
            BeanUtil.copyProperties(outLineInfoAddReq,lineInfoVO);
            queryList.add(lineInfoVO);
        }
        return queryList;
    }

    @Override
    public Integer addLineInfo(OutLineInfoAddReq outLineInfoAddReq) {
        //本地存储数据库lineinfo
        LineInfo lineInfo = new LineInfo();
        BeanUtil.copyProperties(outLineInfoAddReq,lineInfo);
        lineInfo.setCreateDate(DateUtil.getCurrentTime());
        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
        lineInfoMapper.insert(lineInfo);
        String agentService = null;
        if(outLineInfoAddReq.getLineType()== LineTypeEnum.TRUNK.ordinal()){  //ip对接的线路
            agentService = eurekaManager.getAgentService(Constant.LINE_TYPE_TRUNK);
            if(agentService==null){
                throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_NO_LINETRUNKAGENT);
            }
            trunkOrRegisterLine(lineInfo.getLineId()+"",outLineInfoAddReq,Constant.LINE_TYPE_TRUNK,agentService);
        }else if(outLineInfoAddReq.getLineType()== LineTypeEnum.REGISTER.ordinal()){ //注册模式的线路
            agentService = eurekaManager.getAgentService(Constant.LINE_TYPE_REGISTRE);
            if(agentService==null){
                throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_NO_LINEREGISTERAGENT);
            }
            trunkOrRegisterLine(lineInfo.getLineId()+"",outLineInfoAddReq,Constant.LINE_TYPE_REGISTRE,agentService);
        }else if(outLineInfoAddReq.getLineType()== LineTypeEnum.SIMCARD.ordinal()) { //sim卡的线路
            if (StringUtils.isBlank(outLineInfoAddReq.getCallerNum())) {//sim卡线路必须要有主叫号码
                throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_NOCALLER_LINESIM);
            }
            agentService = eurekaManager.getSimAgentService(outLineInfoAddReq.getSipIp(),outLineInfoAddReq.getSipPort());
            if (agentService == null) {
                throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_LINESIMAGENT_FAILE);
            }
            simCardLine(lineInfo.getLineId() + "", outLineInfoAddReq,agentService);
        }
        lineInfo.setFsagentId(agentService);
        lineInfoMapper.updateByPrimaryKey(lineInfo);
        //将线路lineId和fsagent信息存入到缓存中
        putToRedis(lineInfo.getLineId()+"",agentService,lineInfo.getCodec());
        //将加载线路的消息放入到队列中
        addQueue(lineInfo.getLineId()+"",agentService,Constant.LINE_NOTICE_TYPE_LOAD);
        return lineInfo.getLineId();
    }

    /**
     * 配置trunk或者register模式的线路
     * @param lineId
     * @param outLineInfoAddReq
     */
    public void trunkOrRegisterLine(String lineId ,OutLineInfoAddReq outLineInfoAddReq,String lineType,String serviceId){
        String gatewayxml = buildTrunkOrRegisterGateway(lineId, outLineInfoAddReq,lineType);
        LineConfig recordgw = new LineConfig();
        recordgw.setLineId(lineId);
        recordgw.setFileType(Constant.CONFIG_TYPE_GATEWAY);
        recordgw.setFileName("gw_"+lineId+".xml");
        recordgw.setFileData(gatewayxml);
        recordgw.setFsagentId(serviceId);
        lineConfigMapper.insert(recordgw);

        String dialplanxml = buildTrunkOrRegisterDialplan(lineId, outLineInfoAddReq);
        LineConfig recorddl = new LineConfig();
        recorddl.setLineId(lineId);
        recorddl.setFileType(Constant.CONFIG_TYPE_DIALPLAN);
        recorddl.setFileName("01_"+lineId+".xml");
        recorddl.setFileData(dialplanxml);
        recorddl.setFsagentId(serviceId);
        lineConfigMapper.insert(recorddl);
    }

    @Override
    public void updateLineInfo(OutLineInfoUpdateReq outLineInfoUpdateReq) {
        int lineId = outLineInfoUpdateReq.getLineId();
        LineInfo lineInfoDB = lineInfoMapper.selectByPrimaryKey(lineId);
        if(lineInfoDB==null){  //线路不存在
            throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_LINE_NOTEXIST);
        }
        OutLineInfoAddReq outLineInfoAddReq = new OutLineInfoAddReq ();
        BeanUtil.copyProperties(outLineInfoUpdateReq,outLineInfoAddReq);
        //本地更新数据库lineinfo
        LineInfo lineInfo = new LineInfo();
        BeanUtil.copyProperties(outLineInfoUpdateReq,lineInfo);
        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
        //如果线路对接模式切换
        if(lineInfoDB.getLineType()!=outLineInfoUpdateReq.getLineType()) {
            //1、给原来的线路fsagent发送删除线路的MQ
            addQueue(lineId + "", lineInfoDB.getFsagentId(), Constant.LINE_NOTICE_TYPE_DEL);
            //重新生成拨号方案和网关
            String agentService = null;
            if (outLineInfoAddReq.getLineType() == LineTypeEnum.TRUNK.ordinal()) {  //ip对接的线路
                agentService = eurekaManager.getAgentService(Constant.LINE_TYPE_TRUNK);
                if (agentService == null) {
                    throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_NO_LINETRUNKAGENT);
                }
                updateLineConfig(lineId + "", Constant.CONFIG_TYPE_GATEWAY, Constant.LINE_TYPE_TRUNK, outLineInfoAddReq, agentService);
                updateLineConfig(lineId + "", Constant.CONFIG_TYPE_DIALPLAN, Constant.LINE_TYPE_TRUNK, outLineInfoAddReq, agentService);
                lineInfo.setFsagentId(agentService);
            } else if (outLineInfoAddReq.getLineType() == LineTypeEnum.REGISTER.ordinal()) { //注册模式的线路
                agentService = eurekaManager.getAgentService(Constant.LINE_TYPE_REGISTRE);
                if (agentService == null) {
                    throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_NO_LINEREGISTERAGENT);
                }
                updateLineConfig(lineId + "", Constant.CONFIG_TYPE_GATEWAY, Constant.LINE_TYPE_REGISTRE, outLineInfoAddReq, agentService);
                updateLineConfig(lineId + "", Constant.CONFIG_TYPE_DIALPLAN, Constant.LINE_TYPE_REGISTRE, outLineInfoAddReq, agentService);
                lineInfo.setFsagentId(agentService);
              }
            //将新的线路lineId和fsagent信息存入到缓存中
            putToRedis(lineInfo.getLineId()+"",agentService,
                    StringUtils.isNotEmpty(lineInfo.getCodec()) ? lineInfo.getCodec(): lineInfoDB.getCodec());
            lineInfoMapper.updateByPrimaryKeySelective(lineInfo);
            //将加载线路的消息放入到队列中
            addQueue(lineInfo.getLineId()+"",agentService,Constant.LINE_NOTICE_TYPE_LOAD);
             //通知转人工服务切换绑定的线路信息
            agentGroupApiFeign.switchLineinfos(String.valueOf(lineId));
        }else{ //线路对接模式没有切换
            if(outLineInfoUpdateReq.getLineType()== LineTypeEnum.TRUNK.ordinal()){  //ip对接的线路
                updateLineConfig(lineId+"",Constant.CONFIG_TYPE_GATEWAY,Constant.LINE_TYPE_TRUNK,outLineInfoAddReq,null);
                updateLineConfig(lineId+"",Constant.CONFIG_TYPE_DIALPLAN,Constant.LINE_TYPE_TRUNK,outLineInfoAddReq,null);
            }else if(outLineInfoUpdateReq.getLineType()== LineTypeEnum.REGISTER.ordinal()){ //注册模式的线路
                updateLineConfig(lineId+"",Constant.CONFIG_TYPE_GATEWAY,Constant.LINE_TYPE_REGISTRE,outLineInfoAddReq,null);
                updateLineConfig(lineId+"",Constant.CONFIG_TYPE_DIALPLAN,Constant.LINE_TYPE_REGISTRE,outLineInfoAddReq,null);
            }else if(outLineInfoUpdateReq.getLineType()== LineTypeEnum.SIMCARD.ordinal()) { //sim卡的线路
                updateLineConfig(lineId+"",Constant.CONFIG_TYPE_DIALPLAN,Constant.LINE_TYPE_SIMCARD,outLineInfoAddReq,null);
            }
            lineInfoMapper.updateByPrimaryKeySelective(lineInfo);
            //将加载线路的消息放入到队列中
            addQueue(lineInfo.getLineId()+"",lineInfoDB.getFsagentId(),Constant.LINE_NOTICE_TYPE_LOAD);

            //codec有修改，刷新s缓存
            if(lineInfo.getCodec()!=null && !lineInfo.getCodec().equals(lineInfoDB.getCodec())){
                putToRedis(lineInfo.getLineId()+"",lineInfoDB.getFsagentId(),lineInfo.getCodec());
            }
        }
    }


    /**
     * 修改线路，并入库
     * @param lineId
     * @param fileType
     * @param lineType
     * @param outLineInfoAddReq
     */
    public void updateLineConfig(String lineId,String fileType,String lineType,OutLineInfoAddReq outLineInfoAddReq,String fsagentId){
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(lineId+"").andFileTypeEqualTo(fileType);
        List<LineConfig> list  =lineConfigMapper.selectByExample(example);
        if(list.size()>0){
            LineConfig lineConfig = list.get(0);
            String xmlFile = "";
            if(lineType.equals(Constant.LINE_TYPE_TRUNK)||lineType.equals(Constant.LINE_TYPE_REGISTRE)){
                if(fileType.equals(Constant.CONFIG_TYPE_GATEWAY)){
                    xmlFile=  buildTrunkOrRegisterGateway(lineId, outLineInfoAddReq,lineType);
                }else if(fileType.equals(Constant.CONFIG_TYPE_DIALPLAN)){
                    xmlFile= buildTrunkOrRegisterDialplan(lineId+"", outLineInfoAddReq);
                }
            }else if(lineType.equals(Constant.LINE_TYPE_SIMCARD)){
                xmlFile = buildSimCardkDialplan(lineId+"", outLineInfoAddReq);
            }
            lineConfig.setFileData(xmlFile);
            if(fsagentId!=null){
                lineConfig.setFsagentId(fsagentId);
            }
            lineConfigMapper.updateByPrimaryKey(lineConfig);
        }
    }

    @Override
    public void deleteLineInfo(Integer id) {
        LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(id);
        //本地删除数据库lineinfo记录
        lineInfoMapper.deleteByPrimaryKey(id);
        //本地删除数据库linfconfig
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(id+"");
        lineConfigMapper.deleteByExample(example);

        //通知toagentserver服务将坐席组和线路解绑
        log.info("通知转人工服务坐席组解绑线[{}]",id);
        //Result.ReturnData resultUntying =
        agentGroupApiFeign.untyingLineinfos(String.valueOf(id));
//        if(!resultUntying.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("通知转人工服务坐席组解绑线路失败,code:"+resultUntying.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_DEL_LINE_FAILE);
//        }
        //将删除消息放入到队列中
        addQueue(lineInfo.getLineId()+"",lineInfo.getFsagentId(),Constant.LINE_NOTICE_TYPE_DEL);
        //删除该线路id的缓存
        redisUtil.del("sipLineId_"+lineInfo.getLineId()+"");
    }

    @Override
    public FsLineInfoVO getFsInfoByLineId(Integer LineId) {
        FsLineInfoVO fsLineInfoVORedia = (FsLineInfoVO) redisUtil.get("sipLineId_"+LineId);
        if(fsLineInfoVORedia!=null){
            return fsLineInfoVORedia;
        }
        FsLineInfoVO fsLineInfoVO = new FsLineInfoVO();
        LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(LineId);
        if(lineInfo==null){
            log.info("线路在line_info表中不存在[{}]",LineId);
            throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_LINE_NOTEXIST);
        }
        IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + lineInfo.getFsagentId());
        Result.ReturnData<FsInfoVO> resultInfo = iFsStateApi.fsinfo();
        if(!resultInfo.getCode().equals(Constant.SUCCESS_COMMON)||resultInfo.getBody()==null){
            log.info("线路所在fsagent或者freeswitch服务异常，fsagent的serviceId:[{}]",lineInfo.getFsagentId());
            throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_LINEFSAGENT_FAILE);
        }
        FsInfoVO fsInfoVO = resultInfo.getBody();
        BeanUtil.copyProperties(fsInfoVO,fsLineInfoVO);
        fsLineInfoVO.setCodec(lineInfo.getCodec());
        //存入redis中
        redisUtil.set("sipLineId_"+LineId,fsLineInfoVO);
        return fsLineInfoVO;
    }

    @Override
    public List<LineXmlnfoVO> linexmlinfosByFsagentId(String serviceId) {
        List<LineXmlnfoVO> listLine = new ArrayList<LineXmlnfoVO>();
        //查询数据库
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andFsagentIdEqualTo(serviceId);
        List<LineConfig> list =lineConfigMapper.selectByExample(example);
        for (LineConfig config:list) {
            listLine.add(getLineXmlnfoVO(config));
        }
        return listLine;
    }

    @Override
    public List<LineXmlnfoVO> getLinexmlinfoByLineId(Integer LineId) {
        List<LineXmlnfoVO> listLine = new ArrayList<LineXmlnfoVO>();
        //查询数据库
        LineConfigExample example = new LineConfigExample();
        LineConfigExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(LineId+"");
        List<LineConfig> list =lineConfigMapper.selectByExample(example);
        for (LineConfig config:list) {
            listLine.add(getLineXmlnfoVO(config));
        }
        return listLine;
    }

    @Override
    public void batchDeleteLineInfo(List<Integer> lineIds) {
        for (Integer lineId:lineIds) {
            lineOperationService.deleteLineInfo(lineId);
            //删除该线路id的缓存
            redisUtil.del("sipLineId_"+lineId+"");
        }
    }

    @Override
    public List<LineInfoVO> batchAddSimLineInfo(String lineIp, String linePort, List<OutLineInfoAddReq> outLineInfoAddReqList) {
       String agentService = eurekaManager.getSimAgentService(lineIp,linePort);
        if (agentService == null) {
            throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_LINESIMAGENT_FAILE);
        }
        List<LineInfoVO> queryList = new ArrayList<>();
        for (OutLineInfoAddReq outLineInfoAddReq:outLineInfoAddReqList) {
            LineInfo lineInfoDB = new LineInfo();
            BeanUtil.copyProperties(outLineInfoAddReq,lineInfoDB);
            lineInfoDB.setCreateDate(DateUtil.getCurrentTime());
            lineInfoDB.setUpdateDate(DateUtil.getCurrentTime());
            lineInfoDB.setFsagentId(agentService);
            lineInfoMapper.insert(lineInfoDB);
            simCardLine(lineInfoDB.getLineId()+"",outLineInfoAddReq,agentService);
            LineInfoVO lineInfoVO = new LineInfoVO();
            lineInfoVO.setLineId(lineInfoDB.getLineId()+"");
            BeanUtil.copyProperties(outLineInfoAddReq,lineInfoVO);
            queryList.add(lineInfoVO);
            //将加载线路的消息放入到队列中
            addQueue(lineInfoDB.getLineId()+"",lineInfoDB.getFsagentId(),Constant.LINE_NOTICE_TYPE_LOAD);
            //将线路lineId和fsagent信息存入到缓存中
            putToRedis(lineInfoDB.getLineId()+"",lineInfoDB.getFsagentId(),lineInfoDB.getCodec());
        }
        return queryList;
    }

    /**
     * 拼装LineXmlnfoVO
     * @param config
     * @return
     */
    public LineXmlnfoVO getLineXmlnfoVO(LineConfig config){
        XmlUtil util = new XmlUtil();
        LineXmlnfoVO lineXmlnfo = new LineXmlnfoVO();
        lineXmlnfo.setConfigType(config.getFileType());
        lineXmlnfo.setFileName(config.getFileName());
        lineXmlnfo.setFileData(util.getBase64(config.getFileData()));
        return lineXmlnfo;
    }

    /**
     * 生成trunk或者注册模式线路的网关
     * @param lineId
     * @param outLineInfoAddReq
     * @return
     */
    public String buildTrunkOrRegisterGateway(String lineId,OutLineInfoAddReq outLineInfoAddReq,String lineType){
        LinkedHashMap<String, String> gatewayMap = new LinkedHashMap<>();
        GatewayVO include = new GatewayVO();
        GatewayVO.Gateway gateway = new GatewayVO.Gateway();
        gateway.setName("gw_"+lineId);
        gatewayMap.put("username", outLineInfoAddReq.getSipUser());
        gatewayMap.put("password", outLineInfoAddReq.getSipPwd());
        String realm;
        if(outLineInfoAddReq.getSipPort().equals("5060")){
            realm=outLineInfoAddReq.getSipIp();
        }else{
            realm=outLineInfoAddReq.getSipIp()+":"+outLineInfoAddReq.getSipPort();
        }
        gatewayMap.put("realm", realm);
        gatewayMap.put("from-domain", realm);
        if(!StringUtils.isBlank(outLineInfoAddReq.getCallerNum())){
            gatewayMap.put("caller-id-in-from", "true");
        }
        gatewayMap.put("expire-seconds","600" );
        if(lineType.equals(Constant.LINE_TYPE_REGISTRE)){
            gatewayMap.put("register", "true");
        }else if(lineType.equals(Constant.LINE_TYPE_TRUNK)){
            gatewayMap.put("register", "false");
        }
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
     * 生成trunk或者注册模式线路的拨号方案
     * @param lineId
     * @param outLineInfoAddReq
     * @return
     */
    public String buildTrunkOrRegisterDialplan(String lineId,OutLineInfoAddReq outLineInfoAddReq){
        DialplanVO include = new DialplanVO();
        DialplanVO.Extension extension = new DialplanVO.Extension();
        extension.setName(lineId + "_Extension");
        DialplanVO.Condition condition = new DialplanVO.Condition();
        condition.setField("caller_id_name");
        condition.setExpression("^" + lineId + "$");
        LinkedHashSet<DialplanVO.Action> ActionSet = new LinkedHashSet<DialplanVO.Action>();
        DialplanVO.Action action = new DialplanVO.Action();
        action.setData("ringback=${us-ring}");
        action.setApplication("set");
        ActionSet.add(action);
        if(!StringUtils.isBlank(outLineInfoAddReq.getCodec())){
            DialplanVO.Action action1 = new DialplanVO.Action();
            action1.setData("nolocal:absolute_codec_string="+outLineInfoAddReq.getCodec());
            action1.setApplication("export");
            ActionSet.add(action1);
        }
        if(!StringUtils.isBlank(outLineInfoAddReq.getCallerNum())){
            DialplanVO.Action action1 =new DialplanVO.Action();
            action1.setData("callerselector.lua " + outLineInfoAddReq.getCallerNum());
            action1.setApplication("lua");
            ActionSet.add(action1);

            DialplanVO.Action action2 = new DialplanVO.Action();
            action2.setData("info final callerid:${effective_caller_id_number}");
            action2.setApplication("log");
            ActionSet.add(action2);
        }
        DialplanVO.Action action2 = new DialplanVO.Action();
        if(!StringUtils.isBlank(outLineInfoAddReq.getCalleePrefix())){
            action2.setData("sofia/gateway/gw_" + lineId + "/" + outLineInfoAddReq.getCalleePrefix().trim() + "${destination_number}");
        }else{
            action2.setData("sofia/gateway/gw_" + lineId+ "/${destination_number}");
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
     * 配置simCard模式的线路
     * @param lineId
     * @param outLineInfoAddReq
     */
    public void simCardLine(String lineId ,OutLineInfoAddReq outLineInfoAddReq,String serviceId){
        String dialplanxml = buildSimCardkDialplan(lineId, outLineInfoAddReq);
        LineConfig recorddla = new LineConfig();
        recorddla.setLineId(lineId);
        recorddla.setFileType(Constant.CONFIG_TYPE_DIALPLAN);
        recorddla.setFileName("01_"+lineId+".xml");
        recorddla.setFileData(dialplanxml);
        recorddla.setFsagentId(serviceId);
        lineConfigMapper.insert(recorddla);
    }

    /**
     * 生成sim卡线路的拨号方案
     *
     * @param lineId
     * @param outLineInfoAddReq
     * @return
     */
    public String buildSimCardkDialplan(String lineId, OutLineInfoAddReq outLineInfoAddReq) {
//   <include>
//     <extension name="ssy_Extension">
//         <condition field="caller_id_name" expression="123">
//            <action application="set" data="ringback=${us-ring}"/>
//            <action application="export" data="nolocal:absolute_codec_string=PCMA"/>
//            <action application="set" data="callerselector.lua 1001000"/>
//            <action application="lua" data="sanhuigateway.lua 1001000" />
//            <action application="bridge" data="{sip_invite_req_uri=sip:${destination_number}@${account_address}}user/1001000@$${domain}"/>
//          </condition>
//     </extension>
//   </include>

        DialplanVO include = new DialplanVO();
        DialplanVO.Extension extension = new DialplanVO.Extension();
        extension.setName(lineId + "_Extension");
        DialplanVO.Condition condition = new DialplanVO.Condition();
        condition.setField("caller_id_name");
        condition.setExpression("^" + lineId + "$");
        LinkedHashSet<DialplanVO.Action> ActionSet = new LinkedHashSet<DialplanVO.Action>();
        DialplanVO.Action action = new DialplanVO.Action();
        action.setData("ringback=${us-ring}");
        action.setApplication("set");
        ActionSet.add(action);
        DialplanVO.Action action1 = new DialplanVO.Action();
        action1.setData("nolocal:absolute_codec_string=PCMA");
        action1.setApplication("export");
        ActionSet.add(action1);
        DialplanVO.Action action2 = new DialplanVO.Action();
        action2.setData("callerselector.lua " + outLineInfoAddReq.getCallerNum());
        action2.setApplication("lua");
        ActionSet.add(action2);
        DialplanVO.Action action3 = new DialplanVO.Action();
        action3.setData("sanhuigateway.lua " + outLineInfoAddReq.getCallerNum());
        action3.setApplication("lua");
        ActionSet.add(action3);
        DialplanVO.Action action4 = new DialplanVO.Action();
        action4.setData("{sip_invite_req_uri=sip:${destination_number}@${account_address}}user/" + outLineInfoAddReq.getCallerNum() + "@$${domain}");
        action4.setApplication("bridge");
        ActionSet.add(action4);
        condition.setAction(ActionSet);
        extension.setCondition(condition);
        include.setExtension(extension);
        XmlUtil util = new XmlUtil();
        return util.buildxml(include);
    }

    /**
     * 线路信息修改删除时，将通知消息放入队列中广播出去
     * @param lineId
     * @param serviceId
     * @param type
     */
    public void addQueue(String lineId,String serviceId,String type){
        NoticeMessageVO noticeMessageVO = new NoticeMessageVO();
        noticeMessageVO.setMessageType(Constant.LINE_NOTICE_MESSAGETYPE_LINE);
        LineMessageVO lineMessage = new LineMessageVO();
        lineMessage.setLineId(lineId);
        lineMessage.setServiceId(serviceId);
        lineMessage.setType(type);
        noticeMessageVO.setData(lineMessage.toString());
        fanoutSender.send(fanoutExchange, JSON.toJSONString(noticeMessageVO));
        log.info("线路变化的MQ通知消息:[{}]", JSON.toJSONString(noticeMessageVO));
    }

    /**
     * lineId和对应的frees witch信息到redis中
     *
     * @param lineId
     * @param serviceId
     */
    public void putToRedis(String lineId, String serviceId, String codec) {
        IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + serviceId);
        try{
            Result.ReturnData<FsInfoVO> resultInfo = iFsStateApi.fsinfo();
            if (resultInfo.getCode().equals(Constant.SUCCESS_COMMON) && resultInfo.getBody() != null) {
                FsLineInfoVO fsLineInfoVO = new FsLineInfoVO();
                FsInfoVO fsInfoVO = resultInfo.getBody();
                BeanUtil.copyProperties(fsInfoVO, fsLineInfoVO);
                fsLineInfoVO.setCodec(codec);
                redisUtil.set("sipLineId_"+lineId,fsLineInfoVO);
            }
        }catch (Exception e){
            log.error("线路：[{}]，获取freeswitch信息失败[{}],错误:[{}],清除缓存",lineId,serviceId,e.toString());
            redisUtil.del("sipLineId_"+lineId);
        }
    }

    /**
     * 加载所有的lineId和对应的frees witch信息到redis中
     */
    @Override
    public void initToRedis(){
        LineInfoExample example = new LineInfoExample();
        List<LineInfo> list = lineInfoMapper.selectByExample(example);
        if(list.size()>0){
            for (LineInfo lineInfo:list) {
                putToRedis(lineInfo.getLineId()+"",lineInfo.getFsagentId(),lineInfo.getCodec());
            }
        }
    }

}
