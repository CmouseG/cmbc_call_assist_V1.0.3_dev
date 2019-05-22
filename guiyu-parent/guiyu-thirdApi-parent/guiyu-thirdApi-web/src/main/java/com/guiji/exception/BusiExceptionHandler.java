package com.guiji.exception;

/**
 * @Copyright:Copyright (c) 2008 - 2100
 * @Company:guojaing
 */

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.guiji.cache.ThirdApiUser;
import com.guiji.cache.UserCacheUtil;
import com.guiji.support.MyEncryptHttpMessageConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * thirdApi模块异常统一处理类
 */
@Component
public class BusiExceptionHandler implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String PROCESS_ERR = "1004";

    @Autowired
    UserCacheUtil userCacheUtil;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
                                         Exception exception) {

        if (!request.getRequestURI().matches("/thirdApi/.*")) {
            return null;
        }

        ModelAndView mv = new ModelAndView();
        /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        if (exception != null) {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (exception instanceof ThirdApiException) {
                //系统异常
                ThirdApiException commonException = (ThirdApiException) exception;
                attributes.put("code", 0);
                attributes.put("msg", commonException.getErrorCode());
                attributes.put("success", commonException.getSuccess());
            } else {
                attributes.put("code", 1);
                attributes.put("msg", PROCESS_ERR);
                attributes.put("success", 1);
            }

            Long userId = MyEncryptHttpMessageConverter.userIdMap.get();

            ThirdApiUser thirdApiUser = userCacheUtil.getUserInfoByUserId(userId);

            //加签
            attributes.put("sign", DigestUtils.md5Hex(String.format("code=%s&msg=%s&success=%s%s", attributes.get("code"), attributes.get("msg"), attributes.get("success"), thirdApiUser.getClientSecret())).toUpperCase());

            view.setAttributesMap(attributes);
            mv.setView(view);
            logger.error("异常:" + exception.getMessage(), exception);
            return mv;
        }
        return null;
    }

}

