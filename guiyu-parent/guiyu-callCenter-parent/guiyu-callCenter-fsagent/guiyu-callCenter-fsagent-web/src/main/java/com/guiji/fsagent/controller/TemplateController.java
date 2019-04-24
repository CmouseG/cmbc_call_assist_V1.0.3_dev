package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.TtsWav;
import com.guiji.fsagent.entity.WavLengthVO;
import com.guiji.fsagent.eventbus.handler.UploadRcordHandler;
import com.guiji.fsagent.service.TemplateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TemplateController implements ITemplate {
    private final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    TemplateService templateService;
    @Autowired
    UploadRcordHandler uploadRcordHandler;

    @Override
    public Result.ReturnData<Boolean> istempexist(@PathVariable(value = "tempId") String tempId) {
        logger.info("收到模板是否存在请求tempId[{}]", tempId);
        if (StringUtils.isBlank(tempId)) {
            logger.info("模板是否存在请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        return Result.ok(templateService.istempexist(tempId));
    }

    @Override
    public Result.ReturnData<List<TtsWav>> downloadttswav(@RequestParam("tempId") String tempId, @RequestParam("planUuid") String planUuid, @RequestParam("callId") String callId) {
        logger.info("收到下载tts话术录音请求tempId[{}],planUuid[{}]", tempId, planUuid);
        if (StringUtils.isBlank(tempId) || StringUtils.isBlank(planUuid)) {
            logger.info("下载tts话术录音请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        List<TtsWav> list = templateService.downloadttswav(tempId, planUuid, callId);
        logger.info("返回结果下载tts话术录音请求tempId[{}],callId[{}],list[{}]", tempId, planUuid, list);
        return Result.ok(list);
    }

    //    @Override
//    public Result.ReturnData<RecordVO> uploadrecord(@RequestBody RecordReqVO recordReqVO) {
//        if (StringUtils.isBlank(recordReqVO.getFileName()) || StringUtils.isBlank(recordReqVO.getBusiId()) ||
//                StringUtils.isBlank(recordReqVO.getBusiType()) || StringUtils.isBlank(recordReqVO.getSysCode())) {
//            logger.info("上传录音请求失败，参数错误，为null或空");
//            return Result.error(Constant.ERROR_CODE_PARAM);
//        }
//        logger.info("收到上传录音请求RecordReqVO[{}]", recordReqVO);
//        RecordVO result = templateService.uploadrecord(recordReqVO);
//        return Result.ok(result);
//    }
    @Override
    public Result.ReturnData<Boolean> uploadrecord(@RequestBody RecordReqVO recordReqVO) {
        if (StringUtils.isBlank(recordReqVO.getFileName()) || StringUtils.isBlank(recordReqVO.getBusiId()) ||
                StringUtils.isBlank(recordReqVO.getBusiType()) || StringUtils.isBlank(recordReqVO.getSysCode())) {
            logger.info("上传录音请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        logger.info("收到上传录音请求RecordReqVO[{}]", recordReqVO);

        uploadRcordHandler.handleUploadRecord(recordReqVO);

        return Result.ok();
    }

    @Override
    public Result.ReturnData<List<WavLengthVO>> getwavlength(@PathVariable(value = "tempId") String tempId) {
        logger.info("收到获取模板录音文件时长请求tempId[{}]", tempId);
        if (StringUtils.isBlank(tempId)) {
            logger.info("获取模板录音文件时长失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        List<WavLengthVO> list = templateService.getwavlength(tempId);
        if (list == null) {
            return Result.error(Constant.ERROR_CODE_NO_TEMP);
        }
        return Result.ok(list);
    }
}
