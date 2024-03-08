package com.huwuwu.learning.commons.servers;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huwuwu.learning.mapper.AdminDAO;
import com.huwuwu.learning.model.dto.AdminDetails;
import com.huwuwu.learning.model.entity.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetails, UserDetailsService {

    private User user;

    private List<String> permissions;

    @Resource
    private AdminDAO adminDAO;

    public UserDetailsServiceImpl(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }


    //返回权限信息
    @JSONField(serialize = false)  //不需要存到redis中，进行序列化忽略
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        //获取权限时判断如果为空，则。。
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //判断是否没过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 根据用户名返回匹配的用户详情
     * Spring Security会自动的判断账号的状态，并使用登录表单提交过来的密码与返回的UserDetails中的密码进行对比，以决定此账号是否能够登录。
     * UserDetails中的密码必须是密文，即使你执意不加密，也必须明确的表示出来！
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        Admin admin = adminDAO.selectOne(wrapper);
        //如果没有查询到用户就抛出异常
        if (Objects.isNull(admin)) {
            throw new RuntimeException("用户名或密码错误");
        }

        AdminDetails adminDetails = new AdminDetails(
                admin.getId(),
                admin.getUsername(),
                admin.getPassword(),
                admin.getEnabled() == 1,
                authorities//现在authorities为null，后面用户需要获取权限调用上面的getAuthorities方法即可
        );

        //获取该用户的所有权限
        List<String> list = admin.getPermissions();

        return new UserDetailsServiceImpl(adminDetails, list);
    }

}
