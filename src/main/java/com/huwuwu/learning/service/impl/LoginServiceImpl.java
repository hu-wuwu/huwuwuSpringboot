package com.huwuwu.learning.service.impl;

import com.huwuwu.learning.commons.eums.ErrorCode;
import com.huwuwu.learning.commons.exceprions.BusinessException;
import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.commons.utils.JwtUtil;
import com.huwuwu.learning.commons.utils.RedisUtil;
import com.huwuwu.learning.model.vo.AdminVO;
import com.huwuwu.learning.model.vo.LoginUser;
import com.huwuwu.learning.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final Long LOGIN_REDIS_EXPIRE = 1 * 60 * 60L;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse login(AdminVO adminVO) {
        log.debug("开始处理验证登录的业务");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminVO.getUsername(), adminVO.getPassword()
        );
        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        // 如果认证没通过，给出对应提示
        if (Objects.isNull(authenticationResult)) {
            throw new BusinessException(ErrorCode.SEC_AUTHEN_ERROR);
        }
        // 如果认证通过了，使用用户生成JWT
        LoginUser loginUser = (LoginUser) authenticationResult.getPrincipal();
        //给每个存到reids中登录key设置过期时间，1小时，可以写一个拦截器判断过期时间小于30分钟进行续期
        redisUtil.set("login_" + loginUser.getId(), loginUser, LOGIN_REDIS_EXPIRE);

        HashMap<Object, Object> map = new HashMap<>();
        String jwt = JwtUtil.createJWT(loginUser.getId());
        map.put("token", jwt);
        return ResultUtils.success(map);
    }

    @Override
    public BaseResponse logout() {
        // 获取SecurityContextHolder中的用户ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getId();
        // 删除redis中的值
        redisUtil.delete("login_" + userId);
        return ResultUtils.success("退出登录成功！");
    }

    @Override
    public BaseResponse updatePassword(AdminVO adminVO) {

        return ResultUtils.success("修改密码成功！");
    }

    @Override
    public BaseResponse register(AdminVO adminVO) {

        return ResultUtils.success("注册成功！");
    }
}
