package com.huwuwu.learning.commons.exceprions;


import com.huwuwu.learning.commons.eums.ErrorCode;

/**
 * @Author: Ambation
 * @Description: 自定义异常类
 * @Date Created in 2023-02-04-21:27
 * @Modified By:
 */
public class BusinessException extends RuntimeException{
 
    private final int code;
 
    private final String description;
 
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
 
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
 
    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
 
    public int getCode() {
        return code;
    }
 
    public String getDescription() {
        return description;
    }
}