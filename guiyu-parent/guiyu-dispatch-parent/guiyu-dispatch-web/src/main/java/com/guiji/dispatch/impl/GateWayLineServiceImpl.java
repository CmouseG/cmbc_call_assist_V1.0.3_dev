package com.guiji.dispatch.impl;

import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.enums.GateWayLineStatusEnum;
import com.guiji.dispatch.service.GateWayLineService;
import com.guiji.dispatch.vo.GateWayLineOccupyVo;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网关线路Service
 */
@Service
public class GateWayLineServiceImpl implements GateWayLineService {

    private Logger logger = LoggerFactory.getLogger(GateWayLineServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置redis加入路由网关路线
     * @param lineList
     */
    @Override
    public void setGatewayLineRedis(List<DispatchBatchLine> lineList) {
        if (null != lineList && lineList.size() > 0) {
            for (DispatchBatchLine line : lineList) {
                Integer lineId = line.getLineId();
                try {
                    String gatewayLineKey = RedisConstant.RedisConstantKey.gatewayLineKey + lineId;
                    Object obj = redisUtil.get(gatewayLineKey);
                    if (null == obj) {
                        GateWayLineOccupyVo gateWayLine = new GateWayLineOccupyVo();
                        gateWayLine.setLineId(lineId + "");
                        gateWayLine.setStatus(GateWayLineStatusEnum.LEISURE.getState());
                        logger.info("设置redis加入路由网关路线:{}", JsonUtils.bean2Json(gateWayLine));
                        redisUtil.set(gatewayLineKey, gateWayLine);
                    }
                }catch(Exception e){
                    logger.error("设置redis加入路由网关路线"+lineId+"异常", e);
                }
            }
        }
    }
}
