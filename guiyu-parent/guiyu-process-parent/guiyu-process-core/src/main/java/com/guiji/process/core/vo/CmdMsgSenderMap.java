package com.guiji.process.core.vo;

import com.guiji.process.core.message.CmdMessageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ty on 2018/11/21.
 */
public class CmdMsgSenderMap {

    private static final CmdMsgSenderMap instance = new CmdMsgSenderMap();

    private Map<String, Long> timeMap = null;
    private Map<String, CmdMessageVO> msgMap = null;
    private final static long TRY_TIMEOUT = 5 * 1000L;// 默认5s必须有回应

    private CmdMsgSenderMap()
    {
        timeMap = new ConcurrentHashMap<>();
        msgMap = new ConcurrentHashMap<>();
    }

    public static CmdMsgSenderMap getInstance()
    {
        return CmdMsgSenderMap.instance;
    }

    public void produce(CmdMessageVO deviceMsgVO) {

        if(deviceMsgVO == null)
        {
            return;
        }
        timeMap.put(deviceMsgVO.getReqKey(), System.currentTimeMillis());
        msgMap.put(deviceMsgVO.getReqKey(), deviceMsgVO);
    }

    public void hasReceived(CmdMessageVO deviceMsgVO) {

        if(deviceMsgVO == null)
        {
            return;
        }

        timeMap.remove(deviceMsgVO.getReqKey());
        msgMap.remove(deviceMsgVO.getReqKey());
    }



    public List<CmdMessageVO> queryNeedResendMsgs() throws InterruptedException {

        List<CmdMessageVO> result = new ArrayList<>();

        long currentTime = System.currentTimeMillis();
        long sendTime;
        for (Map.Entry<String, Long> ent: timeMap.entrySet()) {

            sendTime = ent.getValue();
            if(currentTime - sendTime >= TRY_TIMEOUT)
            {
                result.add(msgMap.get(ent.getKey()));
            }
        }
        return result;
    }


}
