package com.huwuwu.learning.configuration;

import com.alibaba.fastjson.JSON;
import com.huwuwu.learning.commons.eums.ErrorCode;
import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.commons.utils.WebUtils;
import com.huwuwu.learning.filters.JwtAuthenticationTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity //开启SpringSecurity的默认行为
@Slf4j //日志
//@EnableGlobalMethodSecurity(prePostEnabled = true)// 新版不推荐使用这个,这个的主要功能是开启方法上的鉴权，使用下面这个就可以
@EnableMethodSecurity
public class SecurityConfig{

    /**
     * 目前最主流、安全性最高的加密算法 BCrypt
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //不对密码进行加密处理
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter filter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    // 这个主要是为了其他地方可以使用认证管理器
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置 URL 的安全配置
     * <p>
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                // 对于登录接口 允许匿名访问 未登录状态也可以访问
//                .antMatchers("/**").anonymous()
//                .antMatchers("/huwuwu/admin/login").anonymous()
                .antMatchers("/admin/login").anonymous()
                .antMatchers("/admin/register").anonymous()
                .antMatchers("/admin/sendCode").anonymous()
                .antMatchers("/pay/notify").anonymous()
                // 需要用户带有管理员权限
//                .antMatchers("/find").hasRole("管理员")
//                // 需要用户具备这个接口的权限
//                .antMatchers("/find").hasAuthority("menu:user")

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //将自定义的JWT过滤器添加在Spring Security的UsernamePasswordAuthenticationFilter之前
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        httpSecurity.exceptionHandling()
                //配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        httpSecurity.cors();

        return httpSecurity.build();
    }


    /**
     * 异常处理-认证
     */
    @Component
    public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            authException.printStackTrace();

            BaseResponse result = null;
            if(authException instanceof BadCredentialsException){
                //用户名或密码错误
                result = ResultUtils.error(ErrorCode.SEC_AUTHEN_USERNAME_PASSWORD_ERROR);
            }else if(authException instanceof InsufficientAuthenticationException){
                //认证请求未经授权
                result = ResultUtils.error(ErrorCode.SEC_AUTHEN_NOAUTHOR_ERROR);
            }else {
                result = ResultUtils.error(ErrorCode.SEC_AUTHEN_ERROR);
            }
            //响应给前端
            WebUtils.renderString(response, JSON.toJSONString(result));

        }
    }

    /**
     * 异常处理-权限
     */
    @Component
    public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            BaseResponse result = ResultUtils.error(HttpStatus.FORBIDDEN.value(), "", "权限不足");
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response, json);
        }
    }


}
