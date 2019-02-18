package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.NoticeSendLabel;
import com.guiji.ccmanager.api.INoticeLabel;
import com.guiji.ccmanager.service.NoticeLabelService;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Validated
@RestController
public class NoticeLabelController implements INoticeLabel {

    private final Logger logger = LoggerFactory.getLogger(NoticeLabelController.class);

    @Autowired
    NoticeLabelService noticeLabelService;


    @Override
    @ApiOperation(value = "查询意向标签")
    @GetMapping(value = "queryNoticeIntent")
    public Result.ReturnData<String> queryNoticeIntent(@NotEmpty(message="企业的组织编码不能为空") @RequestParam("orgCode") String orgCode) {

        String label = noticeLabelService.queryNoticeIntent(orgCode);
        return Result.ok(label);
    }

    @Override
    @ApiOperation(value = "修改意向标签,勾选的标签以逗号拼接传递到labels字段")
    @GetMapping(value = "updateNoticeIntent")
    public Result.ReturnData updateNoticeIntent(@NotEmpty(message="企业的组织编码不能为空") @RequestParam("orgCode") String orgCode,
                                                @NotNull(message="标签不能不传")  @RequestParam("labels") String labels) {

        logger.info("----get updateNoticeIntent request labels[{}],orgCode[{}]",labels,orgCode);
        noticeLabelService.updateNoticeIntent(labels,orgCode);
        return Result.ok();
    }


}
