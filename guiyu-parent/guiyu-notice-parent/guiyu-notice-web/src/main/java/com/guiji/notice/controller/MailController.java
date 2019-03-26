package com.guiji.notice.controller;

import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.notice.constant.Constant;
import com.guiji.notice.service.MailService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class MailController {

    private final Logger logger = LoggerFactory.getLogger(MailController.class);
    @Autowired
    MailService mailService;

    @ApiOperation(value = "站内信，查询站内信列表")
    @GetMapping(value = "queryMailList")
    public Result.ReturnData<Page<Map>> queryMailList(@RequestHeader String orgCode, @RequestHeader Long userId,
                                                      String isRead, String pageSize, String pageNo, String noticeType) {

        if (StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageNo)) {
            return Result.error(Constant.ERROR_PARAM);
        }
        int pageSizeInt = Integer.parseInt(pageSize);
        int pageNoInt = Integer.parseInt(pageNo);

        List<Map> list = mailService.queryMailList(userId, isRead, noticeType, pageSizeInt, pageNoInt);
        int total = mailService.countMailList(userId, isRead, noticeType);

        Page<Map> page = new Page<Map>();
        page.setPageNo(pageNoInt);
        page.setPageSize(pageSizeInt);
        page.setTotal(total);
        page.setRecords(list);

        return Result.ok(page);

    }


    @ApiOperation(value = "站内信，删除,id以逗号拼接")
    @Jurisdiction("newsCenter_newsMail_delete")
    @GetMapping(value = "deleteMailById")
    public Result.ReturnData deleteMailById(String id) {
        if (StringUtils.isBlank(id)) {
            return Result.error(Constant.ERROR_PARAM);
        }
        String[] ids = id.split(",");
        List<Integer> list = new ArrayList<>();
        for(String idStr:ids){
            list.add(Integer.valueOf(idStr));
        }
        mailService.deleteMailById(list);

        return Result.ok();

    }

    @ApiOperation(value = "查看站内信，将状态改为已读，返回站内信信息")
    @GetMapping(value = "readMailById")
    public Result.ReturnData readMailById(String id) {
        if (StringUtils.isBlank(id)) {
            return Result.error(Constant.ERROR_PARAM);
        }
        logger.info("----------get request readMailById id[{}]", id);
        Map map = mailService.readMailById(Integer.valueOf(id));

        return Result.ok(map);

    }


}
