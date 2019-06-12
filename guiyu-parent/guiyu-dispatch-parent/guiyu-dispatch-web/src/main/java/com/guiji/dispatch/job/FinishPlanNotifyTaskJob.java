package com.guiji.dispatch.job;


import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.enums.BatchNotifyStatusEnum;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.vo.TotalBatchPlanCountVo;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.enm.NoticeType;
import com.guiji.notice.entity.MessageSend;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.wechat.vo.SendMsgReqVO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 定时任务，每小时执行一次
 * 通知已完成的任务批次
 */
@JobHandler(value="finishPlanNotifyTaskJob")
@Component
public class FinishPlanNotifyTaskJob extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(FinishPlanNotifyTaskJob.class);

    @Autowired
    private DispatchPlanBatchMapper batchMapper;

    @Autowired
    private DispatchPlanMapper planMapper;

    @Autowired
    private GetApiService getApiService;

    @Autowired
    private INoticeSend sendMsg;

    @Value("${weixin.templateId}")
    String weixinTemplateId;
    @Value("${weixin.appid}")
    String weixinAppid;
    @Value("${weixin.pagePath.mainUrl}")
    String mainUrl;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        logger.info("开始执行已完成任务站内信、短信通知定时任务  begin");
        Date time = new Date();
        //获取拨打日期
        Integer today = Integer.valueOf(DateTimeUtils.getDateString(time, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SPLICING));
        //获取当前开始、结束时间
        Date beginTime = DateTimeUtils.getDayStartTime(time);
        Date endTime = DateTimeUtils.getDayEndTime(time);

        //获取批次表的当天批次数据
        List<Integer> userIdList = batchMapper.queryBatchUserIdByTime(beginTime, endTime);
        if(null != userIdList && userIdList.size()>0) {
            Set<Integer> orgIdList = new HashSet<Integer>();
            for(Integer userId: userIdList){
                orgIdList.add(getApiService.getOrgIdByUser(userId+""));
            }
            //获取计划表中完成数量
            List<TotalBatchPlanCountVo> totalList = new ArrayList<TotalBatchPlanCountVo>();
            for(Integer orgId: orgIdList){
                //统计未通知的用户、批次计划各种状态数量
                List<TotalBatchPlanCountVo> totalBatch = planMapper.totalNoNotifyPlanByOrg(orgId, today);
                totalList.addAll(totalBatch);
            }
            //通知
            if(null != totalList && totalList.size()>0){
                for(TotalBatchPlanCountVo totalData : totalList){
                    //消息通知
                    MessageSend msg = this.sendMsgNotify(totalData);
                    if(null != msg) {//变更批次状态, 已通知
                        batchMapper.updNotifyStatusByBatch(totalData.getBatchId(), BatchNotifyStatusEnum.SUCCESS.getStatus());
                    }
                }
            }

        }
        logger.info("开始执行已完成任务站内信、短信通知定时任务  end");
        return ReturnT.SUCCESS;
    }

    /**
     * 消息通知
     * @param totalData
     * @return
     */
    protected MessageSend sendMsgNotify(TotalBatchPlanCountVo totalData) {
        MessageSend send = null;
        try {
            //统计计划数量(已完成，计划中，暂停中，停止中)
            if (null != totalData &&
                    totalData.getFinishCount()>0 && totalData.getDoingCount() == 0) {
                logger.info("用户:{} 批次:{}已完成，开始通知", totalData.getUserId(), totalData.getBatchId());
                send = new MessageSend();
                send.setUserId(Long.valueOf(totalData.getUserId()));
                send.setNoticeType(NoticeType.task_finish);
                String addTime = DateUtil.formatDatetime(totalData.getAddBatchTime());
                send.setSmsContent("您在" + addTime + "创建的" + totalData.getFinishCount() + "通号码的外呼任务已完成，请登录系统查看外呼结果");
                send.setMailContent("您在" + addTime + "创建的" + totalData.getFinishCount() + "通号码的外呼任务已完成，点击查看外呼结果");
                send.setEmailContent("您在" + addTime + "创建的" + totalData.getFinishCount() + "通号码的外呼任务已完成，请登录系统查看外呼结果");
                send.setEmailSubject("任务完成");
                // 微信
                send.setWeixinTemplateId(weixinTemplateId);
                send.setWeixinPagePath(mainUrl);
                send.setWeixinAppId(weixinAppid);
                HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
                //	map.put("keyword1", new SendMsgReqVO.Item(dispatchPlan.getUserId(), null));
                map.put("keyword2", new SendMsgReqVO.Item("任务完成", null));
                map.put("keyword3", new SendMsgReqVO.Item("您的外呼任务已完成哦！请登录系统查看外呼结果", null));
                send.setWeixinData(map);

                logger.info("当前批次结束,通知结束消息:{}", JsonUtils.bean2Json(send));
                sendMsg.sendMessage(send);
            }
        }catch(Exception e){
            logger.error("消息通知异常", e);
        }

        return send;
    }
}
