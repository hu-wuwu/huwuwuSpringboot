package com.huwuwu.learning.model.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@ToString(callSuper = true)
public class AdminDetails extends User {

    @Getter
    private String id;

    public AdminDetails(String id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        // 这里由于不需要关心boolean credentialsNonExpired, boolean accountNonLocked的值
        // 所以直接传入一个固定的true
        // 													↓此数据为权限列表
        super(username, password, enabled, true, true, true, authorities);
        this.id = id;
    }


}
