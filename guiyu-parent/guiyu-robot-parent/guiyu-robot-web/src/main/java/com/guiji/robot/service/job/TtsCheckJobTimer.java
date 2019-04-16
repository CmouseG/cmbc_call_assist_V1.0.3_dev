package com.guiji.robot.service.job;

import com.alibaba.fastjson.JSON;
import com.guiji.ai.api.IAi;
import com.guiji.ai.bean.TtsRsp;
import com.guiji.component.result.Result;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.TtsWavHisMapper;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.dao.entity.TtsWavHisExample;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.impl.AiNewTransService;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: TtsCheckJobTimer
 * @Description: tts查证服务
 * @date 2019年1月9日 下午3:20:43
 */
@Component
@JobHandler(value = "ttsCheckJobTimer")
public class TtsCheckJobTimer extends IJobHandler {
    @Autowired
    TtsWavHisMapper ttsWavHisMapper;
    @Autowired
    ITtsWavService iTtsWavService;
    @Autowired
    AiNewTransService aiNewTransService;
    @Autowired
    IAi iAi;

    /**
     * TTS查证服务
     * 处理TTS合成超过10分钟仍是处理中的数据
     */
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        long beginTime = System.currentTimeMillis();
        XxlJobLogger.log("定时任务，TTS查证...");
        //查询TTS数据超过10分钟的数据且状态仍为进行中的数据，主动发起查证
        TtsWavHisExample example = new TtsWavHisExample();
        //获取10分钟前时间
        Date tenMinBeforeDate = this.getMinFromCurrent(-10);
        example.createCriteria().andStatusEqualTo(RobotConstants.TTS_STATUS_P).andErrorTryNumLessThanOrEqualTo(3).andCrtTimeLessThanOrEqualTo(tenMinBeforeDate);
        //查询tts本地失败，且尝试次数小于<=3的数据重试
        List<TtsWavHis> ttsWavHisList = ttsWavHisMapper.selectByExample(example);
        if (ListUtil.isNotEmpty(ttsWavHisList)) {
            //需要TTS内部处理的数据
            List<TtsCallback> ttsCallbackList = new ArrayList<TtsCallback>();
            for (TtsWavHis ttsWavHis : ttsWavHisList) {
                String busiId = ttsWavHis.getBusiId();
                if (StrUtils.isNotEmpty(busiId)) {
                    //业务编号不为空，开始到AI服务进行TTS查证
                    Result.ReturnData<TtsRsp> ttsRspData = iAi.getResultByBusId(busiId);
                    if (ttsRspData != null && RobotConstants.RSP_CODE_SUCCESS.equals(ttsRspData.getCode()) && ttsRspData.getBody() != null) {
                        TtsRsp ttsRspVO = ttsRspData.getBody();
                        int status = ttsRspVO.getStatus();    //TTS查证接口返回状态
                        int statusInt = 99;
                        //查证接口只处理终态，成功、失败
                        if (RobotConstants.TTS_INTERFACE_DOING == status) {
                            //将状态转为内部完成状态
                            statusInt = RobotConstants.TTS_STATUS_S;
                        } else if (RobotConstants.TTS_INTERFACE_FAIL == status) {
                            //将状态转为内部失败状态
                            statusInt = RobotConstants.TTS_STATUS_F;
                        } else {
                            XxlJobLogger.log("数据{}调研TTS接口状态{}不是终态,continue", busiId, status);
                            continue;
                        }
                        /** 开始保存TTS返回结果  **/
                        TtsCallbackHis ttsCallbackHis = new TtsCallbackHis();
                        ttsCallbackHis.setBusiId(busiId);
                        ttsCallbackHis.setTemplateId(ttsWavHis.getTemplateId());
                        if (ttsRspVO.getAudios() != null) {
                            //将消息转未JSON报文
                            String jsonData = JSON.toJSONString(ttsRspVO.getAudios());
                            ttsCallbackHis.setTtsJsonData(jsonData);
                        }
                        ttsCallbackHis.setStatus(statusInt);
                        String errorMsg = ttsRspVO.getStatusMsg();
                        if (StrUtils.isNotEmpty(errorMsg) && errorMsg.length() > 1024) {
                            //如果异常信息超长，截取下
                            errorMsg = errorMsg.substring(0, 1024);
                        }
                        ttsCallbackHis.setErrorMsg(errorMsg);
                        //新开事务保存
                        aiNewTransService.recordTtsCallback(ttsCallbackHis);
                        /** 开始TTS本次处理  **/
                        TtsCallback ttsCallback = new TtsCallback();
                        BeanUtil.copyProperties(ttsCallbackHis, ttsCallback);
                        ttsCallback.setAudios(ttsRspVO.getAudios());
                        ttsCallbackList.add(ttsCallback);
                    }
                }
                ttsWavHis.setErrorTryNum((ttsWavHis.getErrorTryNum() == null ? 0 : ttsWavHis.getErrorTryNum()) + 1);    //重试次数+1
                aiNewTransService.recordTtsWav(ttsWavHis);
            }
            if (ListUtil.isNotEmpty(ttsWavHisList)) {
                XxlJobLogger.log("本次需要异步处理的TTS数据量为{}条", ttsWavHisList.size());
                //异步处理TTS数据
                iTtsWavService.asynTtsCallback(ttsCallbackList);
            }
        }
        long endTime = System.currentTimeMillis();
        XxlJobLogger.log("定时任务，用时{}S,[TTS查证]完成...", (endTime - beginTime) / 1000);
        return SUCCESS;
    }

    /**
     * 获取当前时间的N分钟前或者后的日期
     * getMinFromCurrent
     *
     * @param min
     * @return
     */
    private Date getMinFromCurrent(int min) {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, min);// 当前时间几分钟前或者后
        Date beforeD = beforeTime.getTime();
        return beforeD;
    }
}
