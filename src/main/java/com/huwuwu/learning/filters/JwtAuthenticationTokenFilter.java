package com.huwuwu.learning.filters;

import com.huwuwu.learning.commons.eums.ErrorCode;
import com.huwuwu.learning.commons.exceprions.BusinessException;
import com.huwuwu.learning.commons.utils.JwtUtil;
import com.huwuwu.learning.commons.utils.RedisUtil;
import com.huwuwu.learning.commons.servers.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //token为空,放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SEC_TOKEN_ERROR);
        }
        //从redis中获取用户信息
        String redisKey = "login_" + userId;
        UserDetailsServiceImpl userDetailsServiceImpl = redisUtil.get(redisKey, UserDetailsServiceImpl.class);
        if (Objects.isNull(userDetailsServiceImpl)){
            throw new BusinessException(ErrorCode.SEC_LOGIN_ERROR);
        }

        // 获取权限信息封装到Authentication中
        Authentication authentication  =
                new UsernamePasswordAuthenticationToken(userDetailsServiceImpl, null, userDetailsServiceImpl.getAuthorities());
        //最后存入SecurityContext中
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //放行
        filterChain.doFilter(request, response);
    }
}
