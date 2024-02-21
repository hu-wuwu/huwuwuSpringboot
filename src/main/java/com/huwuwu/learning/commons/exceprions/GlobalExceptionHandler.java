package com.huwuwu.learning.commons.exceprions;

import com.huwuwu.learning.commons.eums.ErrorCode;
import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Ambation
 * @Description:
 * @Date Created in 2023-02-04-21:53
 * @Modified By:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
       
    //捕获自定义异常
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessException(BusinessException e) {
        log.error("BusinessException:"+ e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(),e.getDescription());
    }
       
    //捕获所有运行时异常
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionException(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }

    //捕获所有异常
//    @ExceptionHandler(Exception.class)
//    public BaseResponse ExceptionException(Exception e) {
//        log.error("Exception", e);
//        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
//    }
}