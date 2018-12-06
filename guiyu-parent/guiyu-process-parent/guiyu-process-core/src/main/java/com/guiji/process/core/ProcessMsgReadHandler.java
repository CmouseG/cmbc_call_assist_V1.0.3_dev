package com.guiji.process.core;

import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.CmdProtoMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class ProcessMsgReadHandler {

    public void run(IProcessCmdHandler handler)
    {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CmdMessageVO cmdMessageVO = null;

        try
        {
            while(true)
            {
                try {

                    cmdMessageVO = CmdMessageQueue.getInstance().get();
                    if(cmdMessageVO == null)
                    {
                        continue;
                    }

                    executorService.execute(new ProcessMsgThread(cmdMessageVO, handler));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            executorService.shutdown();
        }

    }


    class ProcessMsgThread implements Runnable
    {
        private CmdMessageVO cmdMessageVO;

        private IProcessCmdHandler handler;

        protected ProcessMsgThread(CmdMessageVO cmdMessageVO, IProcessCmdHandler handler)
        {
            this.cmdMessageVO = cmdMessageVO;
            this.handler = handler;
        }
        @Override
        public void run() {
            try {
                this.handler.excute(cmdMessageVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
