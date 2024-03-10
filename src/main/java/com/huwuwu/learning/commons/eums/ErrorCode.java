package com.huwuwu.learning.commons.eums;

/**
 *
 * @Description: 错误码
 * @Date Created in 2023-02-04-21:18
 * @Modified By:
 */
public enum ErrorCode{
 
    SUCCESS(0,"ok",""),
    PARAM_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"数据为空",""),
    FILE_ERROR(40002,"请求文件错误",""),
    NULL_AUTH(40101,"无权限",""),
    NOT_LOGIN(40100,"未登录",""),
    REDISS_ERROR(50001,"redis异常",""),
    SYSTEM_ERROR(50000,"系统内部异常",""),
    SEC_LOGIN_ERROR(60101,"登录异常,请重新登录",""),
    SEC_LOGIN_USER_PASSWORD_ERROR(60102,"用户名或密码错误",""),
    SEC_AUTHEN_ERROR(60201,"认证异常",""),
    SEC_AUTHEN_USERNAME_PASSWORD_ERROR(60202,"用户名或密码错误",""),
    SEC_AUTHEN_NOAUTHOR_ERROR(60203,"认证请求未经授权",""),
    SEC_AUTHOR_ERROR(60301,"权限异常",""),
    SEC_TOKEN_ERROR(60001,"token非法","");


 
    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码详情
     */
    private final String description;
 
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
 
    public int getCode() {
        return code;
    }
 
    public String getMessage() {
        return message;
    }
 
    public String getDescription() {
        return description;
    }
}