package com.guiji.web.config;

import com.guiji.web.response.ApiResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 处理全局的异常事件，封装后抛给客户端
 * Created by wchi on 2017/4/27.
 */
@ControllerAdvice
public class MyExceptionHandler {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 参数验证失败异常处理
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        ApiResponse response = new ApiResponse();
        response.setResult(false);
        response.setMsg(error.getDefaultMessage());

        return response;
    }

    /**
     * 非法请求参数格式异常处理
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse processValidationError(HttpMessageNotReadableException ex) {
        log.info("捕获到非法参数格式异常", ex);
        ApiResponse response = new ApiResponse();
        response.setResult(false);
        response.setMsg(ex.getMessage());

        return response;
    }

    /**
     * 非法请求url错误
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse processNoHandlerError(NoHandlerFoundException ex) {
        log.info("捕获到非法的url异常", ex);
        ApiResponse response = new ApiResponse();
        response.setResult(false);
        response.setMsg(ex.getMessage());

        return response;
    }


    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleGlobalException(Exception ex) {
        log.info("捕获到全局异常", ex);
        ApiResponse response = new ApiResponse();
        response.setResult(false);
        response.setMsg(ex.getMessage());

        return response;
    }
}
