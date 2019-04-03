//package com.guiji.calloutserver.service.impl;
//
//import com.guiji.calloutserver.service.DispatchLogService;
//import com.guiji.component.result.Result;
//import com.guiji.dispatch.api.IModularLogsOut;
//import com.guiji.dispatch.model.Constant;
//import com.guiji.dispatch.model.ModularLogs;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/12/16 0016 09:19
// * @Description:
// */
//@Slf4j
//@Service
//public class DispatchLogServiceImpl implements DispatchLogService {
//
//    @Autowired
//    IModularLogsOut iModularLogsOut;
//
//    @Override
//    @Async
//    public void startServiceRequestLog(String callId, String phone, Integer status, String msg) {
//        try {
//            ModularLogs modularLogs = new ModularLogs();
//            modularLogs.setPlanUuid(callId);
//            modularLogs.setPhone(phone);
//            modularLogs.setStatus(status);
//            modularLogs.setMsg(msg);
//            modularLogs.setCreateTime(new Date());
//            modularLogs.setModularName(Constant.MODULAR_CALLCENTER);
//
//            iModularLogsOut.notifyLogs(modularLogs);
//
//        } catch (Exception e) {
////            log.error("startServiceRequestLog has error:", e);
//        }
//    }
//
//
//    @Override
//    @Async
//    public void endServiceRequestLog(String seqId, String phone, Result.ReturnData result, String msg) {
//        try {
//            ModularLogs modularLogs = new ModularLogs();
//            modularLogs.setPlanUuid(seqId);
//            modularLogs.setPhone(phone);
//            modularLogs.setCreateTime(new Date());
//            modularLogs.setModularName(Constant.MODULAR_CALLCENTER);
//
//            if (result == null || result.getCode() == null) {
//                modularLogs.setStatus(Constant.MODULAR_STATUS_ERROR);
//                if (msg != null) {
//                    msg = "Result ReturnData is null " + msg;
//                } else {
//                    msg = "Result ReturnData is null ";
//                }
//            } else if (result.getCode().equals(com.guiji.calloutserver.constant.Constant.SUCCESS_COMMON)) {
//                modularLogs.setStatus(Constant.MODULAR_STATUS_END);
//            } else {
//                modularLogs.setStatus(Constant.MODULAR_STATUS_ERROR);
//            }
//            if (msg != null) {
//                modularLogs.setMsg(msg);
//            }
//
//            iModularLogsOut.notifyLogs(modularLogs);
//        } catch (Exception e) {
////            log.error("endServiceRequestLog has error:", e);
//        }
//
//    }
//}
