package com.guiji.fsagent.manager;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.config.FsagentExceptionEnum;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.util.FileUtil;
import com.guiji.fsagent.util.LineOperUtil;
import com.guiji.fsmanager.api.ILineOperation;
import com.guiji.fsmanager.entity.LineMessageVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import com.guiji.fsmanager.entity.NoticeMessageVO;
import com.guiji.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RabbitListener(bindings=@QueueBinding(value=@Queue(value="${eureka.instance.instance-id:}",durable = "true"),exchange=@Exchange(value="${rabbit.general.fanout.exchange:}",type="fanout",durable = "true")))
@Slf4j
public class LineNoticeListener {
    @Autowired
    ApplicationInit applicationInit;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    ILineOperation lineOperation;

    /**
     * 监听线路通知队列消息，根据serviceId和通知类型进行操作
     *
     * @param message
     */
    @RabbitHandler
    public void process(String message) {
        try {
            NoticeMessageVO noticeMessageVO = JsonUtils.json2Bean(message, NoticeMessageVO.class);
            if(noticeMessageVO!=null&&noticeMessageVO.getMessageType().equals(Constant.LINE_NOTICE_MESSAGETYPE_LINE)){
                log.info("监听到线路通知[{}]",noticeMessageVO);
                String subMessage= noticeMessageVO.getData();
                LineMessageVO lineMessage = JsonUtils.json2Bean(subMessage, LineMessageVO.class);
                if (eurekaManager.getInstanceId().equals(lineMessage.getServiceId())) {
                    if (lineMessage.getType().equals(Constant.LINE_NOTICE_TYPE_DEL)) {
                        log.info("del line[{}]",lineMessage.getLineId());
                        delLine(lineMessage.getLineId());//删除线路
                    } else if (lineMessage.getType().equals(Constant.LINE_NOTICE_TYPE_LOAD)) {
                        log.info("load line[{}]",lineMessage.getLineId());
                        loadLine(lineMessage.getLineId());
                    }
                }
            }else if(noticeMessageVO!=null&&noticeMessageVO.getMessageType().equals(Constant.LINE_NOTICE_MESSAGETYPE_CONFIG)){

            }
        } catch (Exception e) {
            log.error("消息队列监听失败，错误信息{}", e.getMessage());
        }
    }

    /**
     * 删除线路
     *
     * @param lineId
     */
    public void delLine(String lineId) {
        //获取fs对象
        FreeSWITCH fs = applicationInit.getFreeSwitch();
        if (fsConfig.getRole().equals(Constant.FSAGENT_ROLE_LINE_SIMCARD)) { //如果是sim卡线路，只需要删除拨号方案即可
            FileUtil.delete(fs.getDialplan() + "01_" + lineId + ".xml");
        } else {//如果为trunk和register线路，需要删除拨号方案和网关并且卸载网关
            FileUtil.delete(fs.getDialplan() + "01_" + lineId + ".xml");
            FileUtil.delete(fs.getGateway() + "gw_" + lineId + ".xml");
            fs.execute("sofia profile external killgw gw_" + lineId);
        }
    }

    /**
     * 加载线路
     *
     * @param lineId
     */
    public void loadLine(String lineId) {
        FreeSWITCH freeSwitch = applicationInit.getFreeSwitch();
        //根据队列中的lineId去fsmanager中获取对应的线路文件
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperation.getLinexmlinfoByLineId(Integer.parseInt(lineId));
        if (!result.getCode().equals("0")||result.getBody()==null) {
            log.warn("请求fsmanager获取线路接口返回出错:" + result.getCode());
            throw new GuiyuException(FsagentExceptionEnum.EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS);
        }
        List<LineXmlnfoVO> lineList = result.getBody();
        LineOperUtil.ergodicLine(lineList, freeSwitch, fsConfig.getRole());
    }
}
