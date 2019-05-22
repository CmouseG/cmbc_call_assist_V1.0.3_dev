package com.guiji.api.controller;

import com.guiji.api.vo.BotenceVo;
import com.guiji.common.GenericRo;
import com.guiji.component.result.Result;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.robot.api.CustAiAccountRemote;
import com.guiji.robot.model.CustTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * robot相关api
 */
@RestController
@RequestMapping("/thirdApi")
public class RobotApiController {

    @Autowired
    CustAiAccountRemote custAiAccountRemote;

    /**
     * 获取话术列表
     * @param ro
     * @return
     */
    @RequestMapping("/getBotence")
    public BotenceVo getBotence(@RequestBody GenericRo ro) {

        Result.ReturnData<List<CustTemplateVo>> listReturnData = custAiAccountRemote.queryCustTemplateList(ro.getUserId());

        if (listReturnData != null && listReturnData.success) {
            List<CustTemplateVo> body = listReturnData.getBody();

            if (!CollectionUtils.isEmpty(body)) {
                StringBuffer res = new StringBuffer();

                body.forEach(obj -> {
                    res.append(obj.getTemplateId());
                    res.append("^");
                    res.append(obj.getTemplateName());
                    res.append("^");
                    res.append(obj.getAgentFlag()?1:0);
                    res.append("|");

                });

                BotenceVo vo = new BotenceVo();

                vo.setBotenceList(res.substring(0, res.length() - 1));

                return vo;
            }
        }

        throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);

    }

}
