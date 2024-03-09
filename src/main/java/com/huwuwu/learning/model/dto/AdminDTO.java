package com.huwuwu.learning.model.dto;

import lombok.Data;

@Data
public class AdminDTO {

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
