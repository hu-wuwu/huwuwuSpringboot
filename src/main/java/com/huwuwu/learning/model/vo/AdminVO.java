package com.huwuwu.learning.model.vo;

import lombok.Data;

@Data
public class AdminVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPassword;



}
