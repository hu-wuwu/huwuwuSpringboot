package com.huwuwu.learning.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("admin")
public class Admin implements Serializable {

    @TableId
    private String id;

    @TableField
    private String username;

    @TableField
    private String password;

    /**
     * enabled,1 用户启动，0 用户禁用
     */
    @TableField
    private int enabled;

    private List<String> permissions;

}
