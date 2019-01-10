package com.guiji.dispatch.bean;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class DispatchPlanPushHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final  DispatchPlanPushHandler instance = new DispatchPlanPushHandler();

    private DispatchPlanPushHandler() {

    }

    public static DispatchPlanPushHandler getInstance()
    {
        return instance;
    }

    public void add(List<DispatchPlan> dispatchPlans){
        try {
            DispatchPlanQueue.getInstance().produce(dispatchPlans);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
