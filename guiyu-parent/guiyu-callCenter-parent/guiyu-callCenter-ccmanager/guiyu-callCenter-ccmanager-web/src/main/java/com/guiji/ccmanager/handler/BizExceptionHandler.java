package com.guiji.ccmanager.handler;

import com.guiji.ccmanager.constant.Constant;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/4 0004 19:42
 * @Description:
 */
@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ReturnData bindException(MethodArgumentNotValidException e) {
         BindingResult bindingResult = e.getBindingResult();
         String errorMesssage = "校验失败:";
         for (FieldError fieldError : bindingResult.getFieldErrors()){
             errorMesssage += fieldError.getDefaultMessage() + "！ ";
         }
        ReturnData returnData = new ReturnData(Constant.ERROR_VALIDATE,errorMesssage,false);
        return returnData;
     }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ReturnData bindException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> bindingResult = e.getConstraintViolations();
        String errorMesssage = "校验失败:";
        for (ConstraintViolation constraintViolation : bindingResult){
            errorMesssage += constraintViolation.getMessage() + "！ ";
        }
        ReturnData returnData = new ReturnData(Constant.ERROR_VALIDATE,errorMesssage,false);
        return returnData;
    }

}
