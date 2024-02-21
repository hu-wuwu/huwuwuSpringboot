package com.huwuwu.learning.commons.response;


import com.huwuwu.learning.commons.eums.ErrorCode;

/**
 *
 * @Description: 返回工具类
 * @Date Created in 2023-02-04-21:04
 * @Modified By:
 */
public class ResultUtils {
 
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(0,data,"ok");
    }
 
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }
 
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message,String descrition){
        return new BaseResponse<>(errorCode.getCode(),null,message,descrition);
    }
 
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message){
        return new BaseResponse<>(errorCode.getCode(),message);
    }
 
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(int errorCode,String message,String descrition){
        return new BaseResponse<>(errorCode,null,message,descrition);
    }
}