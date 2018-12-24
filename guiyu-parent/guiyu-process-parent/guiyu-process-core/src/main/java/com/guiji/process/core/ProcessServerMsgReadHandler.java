package com.guiji.process.core;

import com.guiji.process.core.message.CmdMessageVO;
import org.springframework.stereotype.Service;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class ProcessServerMsgReadHandler {

    public void run(IProcessCmdHandler handler)
    {
        CmdMessageVO cmdMessageVO = null;
        while(true)
        {
            try {

                cmdMessageVO = CmdMessageQueue.getInstance().get();
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
