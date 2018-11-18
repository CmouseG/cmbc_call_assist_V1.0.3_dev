package ai.guiji.botsentence.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.guiji.botsentence.dao.entity.IntentionTask;
import ai.guiji.botsentence.service.IVoliceService;
import ai.guiji.botsentence.service.IntentionTaskService;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.model.Page;
import ai.guiji.component.model.ServerResult;


/**
 * @version V1.0
 * @ClassName: IntentionTaskController
 * @Description: 意向标签前后台逻辑处理类
 * @author: 张朋
 * @date 2018年8月14日 下午4:36:02
 */
@RestController
@RequestMapping(value = "intentionTask")
public class IntentionTaskController {

    private Logger logger = LoggerFactory.getLogger(IntentionTaskController.class);

    @Autowired
    IntentionTaskService intentionTaskService;

    @Autowired
    IVoliceService voliceService;

    /**
     * 根据话术流程编号查询当前话术流程的意向标签信息
     */
    @RequestMapping(value = "/queryIntentionList")
    public ServerResult<List<IntentionTask>> queryIntentionList(@JsonParam String processId) {
        logger.info("当前请求参数: " + processId);
        if (StringUtils.isBlank(processId)) {
            return ServerResult.createByErrorMessage("请求参数为空!");
        }
        List<IntentionTask> list = intentionTaskService.queryIntentionList(processId);
        return ServerResult.createBySuccess(list);
    }


    /**
     * 分页查询当前话术流程的意向标签信息
     */
    @RequestMapping(value = "/queryIntentionListByPage")
    public ServerResult<Page<IntentionTask>> queryIntentionListByPage(@JsonParam String processId, @JsonParam int pageSize, @JsonParam int pageNo) {
        logger.info("当前请求参数: processId= " + processId);
        logger.info("当前请求参数: pageSize= " + pageSize);
        logger.info("当前请求参数: pageNo = " + pageNo);
        if (StringUtils.isBlank(processId)) {
            return ServerResult.createByErrorMessage("请求参数为空!");
        }
        List<IntentionTask> list = intentionTaskService.queryIntentionListByPage(processId, pageSize, pageNo);

        Page<IntentionTask> page = new Page<IntentionTask>();
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);

        //计算总记录数量
        int totalNum = intentionTaskService.countIntentionNum(processId);
        page.setTotal(totalNum);
        page.setRecords(list);

        return ServerResult.createBySuccess(page);
    }

    /**
     * 更新话术意向标签信息
     *
     * @param intentionId
     * @param dialogue_times
     * @param keyWords
     * @return
     */
    @RequestMapping(value = "updateIntention")
    public ServerResult updateIntention(@JsonParam String intentionId, @JsonParam String dialogue_times, @JsonParam String keyWords, @RequestHeader Long userId) {
        intentionTaskService.updateIntention(intentionId, dialogue_times, keyWords,userId);
        return ServerResult.createBySuccess();
    }
}
