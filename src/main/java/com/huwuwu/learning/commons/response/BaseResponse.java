package com.huwuwu.learning.commons.response;

import com.huwuwu.learning.commons.eums.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Description: 通用返回类
 * @Date Created in 2023-02-04-20:57
 * @Modified By:
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -2243004256529006189L;
 
    private int code;
 
    private T data;
 
    private String message;
 
    private String description;
 
    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
 
    public BaseResponse(int code, T data,String message) {
        this(code,data,message,"");
    }
 
    public BaseResponse(int code, T data) {
        this(code,data,"","");
    }
 
    public BaseResponse(ErrorCode code) {
        this(code.getCode(),null,code.getMessage(),code.getDescription());
    }
}