package com.huwuwu.learning.commons.servers;

import com.huwuwu.learning.commons.eums.ErrorCode;
import com.huwuwu.learning.commons.exceprions.BusinessException;
import com.huwuwu.learning.mapper.AdminDAO;
import com.huwuwu.learning.model.po.AdminPO;
import com.huwuwu.learning.model.vo.LoginUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Data
@NoArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AdminDAO adminDAO;

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

        //查询出用户后还要获取对应的权限信息，封装到UserDetails中返回
        //查询用户信息
//        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Admin::getUsername, username);
//        Admin admin = adminDAO.selectOne(wrapper)

        //查询用户信息（查询数据库，这里使用假数据）
        AdminPO adminPO = new AdminPO("1", "huwuwu", "$2a$10$wKsKDR/PLSWsgLhiB2H1jOpOHqXpplaS89ATfuOs1q36aaYzjDiVe", 1, new ArrayList<>(Arrays.asList("test", "admin")));
        //如果没有查询到用户就抛出异常
        if (Objects.isNull(adminPO)) {
            throw new BusinessException(ErrorCode.SEC_LOGIN_USER_PASSWORD_ERROR);
        }

        // 把数据封装成LoginUser对象返回
        return new LoginUser(adminPO.getId(), adminPO.getUsername(), adminPO.getPassword(), adminPO.getPermissions());
    }


}
