package com.guiji.notice.controller;

import com.guiji.component.result.Result;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.constant.Constant;
import com.guiji.notice.entity.MessageSend;
import com.guiji.notice.service.NoticeSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class NoticeSendController implements INoticeSend {

    private final Logger logger = LoggerFactory.getLogger(NoticeSendController.class);
    @Autowired
    NoticeSendService noticeSendService;

//    private ExecutorService executor = Executors.newCachedThreadPool() ;

    @Override
    public Result.ReturnData sendMessage(@RequestBody MessageSend messageSend) {

        logger.info("--------get request sendMessage MessageSend[{}]",messageSend);

        if (messageSend != null && messageSend.getNoticeType() != null) {

            noticeSendService.sendMessage(messageSend);

            logger.info("==========end sendMessage userId[{}]",messageSend.getUserId());
            return Result.ok();
        } else {
            return Result.error(Constant.ERROR_PARAM);
        }

    }

}
