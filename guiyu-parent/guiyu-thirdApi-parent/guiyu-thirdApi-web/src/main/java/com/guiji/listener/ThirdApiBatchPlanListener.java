package com.guiji.listener;

import com.guiji.api.ro.BatchPlanListRo;
import com.guiji.api.vo.CallDetailsVo;
import com.guiji.cfg.ThirdApiMqConfig;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IThirdApiOut;
import com.guiji.dispatch.model.CallPlanDetailRecordVO;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ThirdApiMqListener
 * @Description: 异步回调信息监听
 */
@Component
@RabbitListener(queues = ThirdApiMqConfig.BATCH_PLAN_TASK)
public class ThirdApiBatchPlanListener {

    @Autowired
    QueueSender queueSender;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 监听ROBOT队列消息，目前主要处理：
     * 1、
     *
     * @param message
     */
    @RabbitHandler
    public void process(String message) {
        BatchPlanListRo batchPlanListRo = JsonUtils.json2Bean(message, BatchPlanListRo.class);

        MqMessage mqMessage = new MqMessage();

        try {
            Long userId = batchPlanListRo.getUserId();
            String batchName = batchPlanListRo.getBatchName();

            int searchSize = 1000;

            int currPage = batchPlanListRo.getPage();
            int pageSize = batchPlanListRo.getPageNum();

            if (currPage < 0) {
                currPage = 1;
            }

            if(pageSize < searchSize) {
                searchSize = pageSize;
            }

            int searchPage = (currPage - 1) * pageSize / searchSize;

            boolean firstFlag = true;
            int batchCount = 0;
            int totalPage = 0;

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < (pageSize % searchSize == 0 ? pageSize / searchSize : pageSize / searchSize + 1); i++) {
                searchPage = searchPage + 1;

                Result.ReturnData<Page<CallPlanDetailRecordVO>> calldetail = iThirdApiOut.getCalldetailForApi(userId, batchName, searchPage, searchSize);

                if (calldetail != null && "0".equals(calldetail.getCode())) {

                    if (firstFlag) {
                        batchCount = calldetail.getBody().getTotalRecord();
                        totalPage = batchCount % pageSize == 0 ? batchCount / pageSize : batchCount / pageSize + 1;
                        firstFlag = false;
                    }

                    List<CallPlanDetailRecordVO> records = calldetail.getBody().getRecords();

                    if (!CollectionUtils.isEmpty(records)) {
                        convert(records, result);
                    }
                }
            }
            if (result.length() > 1) {
                result.deleteCharAt(result.length() - 1);
            }

            CallDetailsVo callDetailsVo = new CallDetailsVo();

            callDetailsVo.setBatchCount(batchCount);
            callDetailsVo.setCallList(result.toString());
            callDetailsVo.setPage(currPage);
            callDetailsVo.setPageNum(pageSize);
            callDetailsVo.setTotalPage(totalPage);
            callDetailsVo.setBatchName(batchName);

            mqMessage.setBody(JsonUtils.bean2Json(callDetailsVo));
            mqMessage.setNotifyUrl(batchPlanListRo.getNotifyUrl());
            mqMessage.setUserId(batchPlanListRo.getUserId());

            queueSender.send(ThirdApiMqConfig.NOTIFY_QUEUE, JsonUtils.bean2Json(mqMessage));
        } catch (Exception ex) {
            logger.info("error data : {}", message);
            logger.error("error message : {}", ex.getMessage());
        }


    }

    @Autowired
    IThirdApiOut iThirdApiOut;

    /**
     * 将记录转换为api所需要的格式
     *
     * @param records
     * @param result
     */
    void convert(List<CallPlanDetailRecordVO> records, StringBuilder result) {
        for (CallPlanDetailRecordVO vo : records) {

            // "accurateIntent", "addDate", "answerTime", "billSec", "callStartTime",
            // "callTime", "detailList", "duration", "endTime", "freason",
            // "hangupDirection", "hangupTime", "reason", "remarks"

            // accurateIntent
            result.append(StringUtils.isEmpty(vo.getAccurateIntent​()) ? "" : vo.getAccurateIntent​());
            result.append("^");

            // addDate
            result.append(DateUtil.dateToString(vo.getCreateTime(), "yyyy-MM-dd"));
            result.append("^");

            // answerTime
            result.append(vo.getAnswerTime().getTime());
            result.append("^");

            //billSec
            result.append(vo.getBillSec() == null ? 0 : vo.getBillSec());
            result.append("^");

            //callStartTime
            result.append(vo.getCallStartTime() == null ? "" : vo.getCallStartTime().getTime());
            result.append("^");

            int dur = (vo.getDuration() == null ? 0 : vo.getDuration());

            long f = DateUtil.parseDate("2019-05-03").getTime() + dur*1000;

            //callTime
            result.append(DateUtil.dateToString(new Date(f), "HH:mm:ss"));
            result.append("^");

            //detailList
            result.append("^");

            //duration
            result.append(vo.getDuration() == null ? 0 : vo.getDuration());
            result.append("^");

            //freason;
            result.append(StringUtils.isEmpty(vo.getFreason()) ? "" : vo.getFreason());
            result.append("^");

            //hangupDirection 挂断方
            result.append(vo.getHangupDirection() == null ? "" : vo.getHangupDirection());
            result.append("^");

            //hangupTime
            result.append(vo.getHangupTime() == null ? "" : DateUtil.dateToString(vo.getHangupTime(), "yyyy-MM-dd HH:mm:ss"));
            result.append("^");

            //reason
            result.append(StringUtils.isEmpty(vo.getPhoneNum()) ? "" : vo.getPhoneNum());
            result.append("^");

            //reason
            result.append(StringUtils.isEmpty(vo.getReason()) ? "" : vo.getReason());
            result.append("^");

            //remarks
            result.append(StringUtils.isEmpty(vo.getRemarks()) ? "" : vo.getRemarks());

            result.append("|");
        }
    }

}
