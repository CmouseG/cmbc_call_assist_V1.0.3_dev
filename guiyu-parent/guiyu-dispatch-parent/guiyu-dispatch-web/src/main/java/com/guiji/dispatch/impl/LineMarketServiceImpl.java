package com.guiji.dispatch.impl;

import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.exception.DispatchCodeExceptionEnum;
import com.guiji.dispatch.service.LineMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname LineMarketServiceImpl
 * @Description TODO
 * @Date 2019/5/21 17:40
 * @Created by qinghua
 */
@Service
public class LineMarketServiceImpl implements LineMarketService {

    @Autowired
    LineMarketRemote lineMarketRemote;

    @Override
    public DispatchBatchLine getByLineId(Integer lineId, Integer userId) {


        Result.ReturnData<SipLineVO> sipLineVOReturnData = lineMarketRemote.queryUserSipLineByLineId(userId.toString(), lineId);

        if(sipLineVOReturnData == null || !sipLineVOReturnData.success || sipLineVOReturnData.getBody() == null) {
            throw new BaseException(DispatchCodeExceptionEnum.NO_THIS_LINE.getErrorMsg(), DispatchCodeExceptionEnum.NO_THIS_LINE.getErrorCode());
        }

        SipLineVO vo = sipLineVOReturnData.getBody();

        DispatchBatchLine dispatchBatchLine = new DispatchBatchLine();

        dispatchBatchLine.setLineType(vo.getLineType());
        dispatchBatchLine.setLineName(vo.getLineName());
        dispatchBatchLine.setOvertarea(vo.getOvertArea());
        dispatchBatchLine.setUserId(Integer.valueOf(vo.getUserId()));
        dispatchBatchLine.setLineAmount(vo.getUnivalent());
        dispatchBatchLine.setLineId(vo.getLineId());

        return dispatchBatchLine;

    }
}
