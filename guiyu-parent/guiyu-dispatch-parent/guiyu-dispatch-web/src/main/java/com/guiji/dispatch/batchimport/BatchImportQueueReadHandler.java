package com.guiji.dispatch.batchimport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.entity.DispatchPlan;

/**
 * Created by zhujy on 2018/12/28.
 */
@Service
public class BatchImportQueueReadHandler {


    private static final Logger logger = LoggerFactory.getLogger(BatchImportQueueReadHandler.class);

    public void run(IBatchImportRecordHandler handler)
    {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        DispatchPlan vo = null;

        try
        {
            while(true)
            {
                try {
                	//获取队列的数据
                    vo = BatchImortQueue.getInstance().get();
                    if(vo == null)
                    {
                        continue;
                    }

                    executorService.execute(new BatchImportThread(vo, handler));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            executorService.shutdown();
        }

    }


    class BatchImportThread implements Runnable
    {
        private DispatchPlan vo;

        private IBatchImportRecordHandler handler;

        protected BatchImportThread(DispatchPlan vo, IBatchImportRecordHandler handler)
        {
            this.vo = vo;
            this.handler = handler;
        }
        @Override
        public void run() {
            try {
                this.handler.excute(vo);
            } catch (Exception e) {
            	logger.error("error",e);
            }
        }
    }
}
