/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:guojaing
 */
package ai.guiji.component.client.interceptor;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

import ai.guiji.component.client.util.StrUtils;
import ai.guiji.component.exception.CommonException;

/**
 *@Description:
 *@Author:weiyunbo
 *@date:2018年6月25日 下午5:53:32
 *@history:
 *@Version:v1.0
 */
@Component
public class BusiExceptionHandler implements HandlerExceptionResolver{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public static final String UNKNOW_ERROR_CODE = "999999"; //未知异常码
	public static final String UNKNOW_ERROR_MSG = "系统异常，请联系管理员"; //未知异常信息
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) {
		 ModelAndView mv = new ModelAndView();
         /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
         FastJsonJsonView view = new FastJsonJsonView();
         if(exception != null) {
        	 Map<String, Object> attributes = new HashMap<String, Object>();
        	 if(exception instanceof CommonException) {
        		 //系统异常
        		 CommonException commonException = (CommonException) exception;
        		 attributes.put("rspCode", StrUtils.isNotEmpty(commonException.getErrorCode()) ? commonException.getErrorCode() : UNKNOW_ERROR_CODE);
                 attributes.put("rspMsg", StrUtils.isNotEmpty(commonException.getErrorMessage()) ? commonException.getErrorMessage() : UNKNOW_ERROR_MSG);
        	 }else {
        		 //其他异常
        		 attributes.put("rspCode", UNKNOW_ERROR_CODE);  	//未知异常码
                 attributes.put("rspMsg", StrUtils.isNotEmpty(exception.getMessage()) ? exception.getMessage() : UNKNOW_ERROR_MSG); 	//未知异常原因
        	 }
        	 view.setAttributesMap(attributes);
             mv.setView(view);
             logger.error("异常:" + exception.getMessage(), exception);
             return mv;
         }
		return null;
	}

}

