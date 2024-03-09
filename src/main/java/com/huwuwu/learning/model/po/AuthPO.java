package com.huwuwu.learning.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("auth")
public class AuthPO {

    @TableId("id")
    private String id;

    @TableField("auth_name")
    private String authName;

    @TableField("admin_id")
    private String adminId;
}
