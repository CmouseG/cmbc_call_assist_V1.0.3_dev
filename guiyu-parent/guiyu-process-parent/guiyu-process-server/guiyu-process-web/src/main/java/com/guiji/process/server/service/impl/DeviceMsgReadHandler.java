package com.guiji.process.server.service.impl;

import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.server.model.CmdMessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class DeviceMsgReadHandler {

    public void run(DeviceCmdHandler handler)
    {
        CmdMessageVO cmdMessageVO = null;
        while(true)
        {
            System.out.println("33333333333333333333333333333333333333333333333333333333");
            try {
                cmdMessageVO = CmdMessageQueue.getInstance().get();
                System.out.println("99999999999999999999999999");
                if(cmdMessageVO == null)
                {
                    continue;
                }

                handler.excute(cmdMessageVO);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
