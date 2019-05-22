package com.guiji.api.controller;

import com.guiji.api.vo.LineListVo;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.api.VoipMarketRemote;
import com.guiji.clm.model.SimLineVo;
import com.guiji.clm.model.SipLineVO;
import com.guiji.common.GenericRo;
import com.guiji.component.result.Result;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 线路市场相关api
 */
@RestController
@RequestMapping("/thirdApi")
public class LineMarketApiController {


    @Autowired
    LineMarketRemote lineMarketRemote;

    @Autowired
    VoipMarketRemote voipMarketRemote;

    /**
     * @param lineListRo
     * @return com.guiji.api.vo.LineListVo
     * @author qinghua 2019/4/18
     * @time 21:20
     * @method getLineList
     * @version V1.0.0
     * @description 获取线路列表，（sip和sim）
     */
    @RequestMapping("/getCallLine")
    public LineListVo getLineList(@RequestBody GenericRo lineListRo) {

        Long userId = lineListRo.getUserId();

        Result.ReturnData<List<SipLineVO>> sipData = lineMarketRemote.queryUserSipLineList(userId.toString());

        StringBuffer res = new StringBuffer();

        if (sipData != null && sipData.success) {
            if (!CollectionUtils.isEmpty(sipData.getBody())) {
                sipData.getBody().forEach(obj -> {
                    res.append(obj.getLineId());
                    res.append("^");
                    res.append(obj.getLineName());
                    res.append("^");
                    //sim线路
                    res.append(1);
                    res.append("|");
                });
            }
        }

        Result.ReturnData<List<SimLineVo>> simData = voipMarketRemote.querySimLineInfo(userId);

        if (simData != null && simData.success) {
            if (!CollectionUtils.isEmpty(simData.getBody())) {
                simData.getBody().forEach(obj -> {
                    res.append(obj.getLineId());
                    res.append("^");
                    res.append(obj.getLineName());
                    res.append("^");
                    //sim线路
                    res.append(2);
                    res.append("|");
                });
            }
        }

        if (res.length() > 0) {

            LineListVo lineListVo = new LineListVo();

            lineListVo.setLineList(res.substring(0, res.length() - 1));

            return lineListVo;

        } else {
            throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);
        }

    }

}
