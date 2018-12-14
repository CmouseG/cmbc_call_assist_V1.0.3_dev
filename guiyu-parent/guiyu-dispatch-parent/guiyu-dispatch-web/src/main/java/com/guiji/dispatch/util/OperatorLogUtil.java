package com.guiji.dispatch.util;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.dev.ReSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.dispatch.dao.entity.DispatchLog;

public class OperatorLogUtil {

    /**
     * @Fields logger : 日志
     */
    private static Logger logger = LoggerFactory.getLogger(OperatorLogUtil.class);
    /**
     * 操作结果 0：失败
     */
    public static final String OPERATOR_RESULT_FAIL = "0";

    /**
     * 操作结果 1：成功
     */
    public static final String OPERATOR_RESULT_SUCCESS = "1";

    /**
     * ip :unknown
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取操作日志
     * @param request 请求
     * @param method  方法对象
     * @return 返回操作日志对象
     * @throws Exception 
     */
    public static DispatchLog getLog(HttpServletRequest request, Method method) throws Exception{
    	DispatchLog log = new DispatchLog();

        log.setGmtCreate(ToolDateTime.getCurrentTime());
        log.setGmtModified(ToolDateTime.getCurrentTime());
        //动作
        log.setAction(String.valueOf(request.getRequestURL()));
        //通过注解获得的 操作内容 和 操作
        Log sysLog = method.getAnnotation(Log.class);
        log.setModule(sysLog.info());
        log.setParams(request.getQueryString());
        return log;
    }


}
