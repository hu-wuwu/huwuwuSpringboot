package com.huwuwu.learning.web;

import com.huwuwu.learning.commons.eums.ErrorCode;
import com.huwuwu.learning.commons.exceprions.BusinessException;
import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.model.vo.AdminVO;
import com.huwuwu.learning.model.vo.LoginUser;
import com.huwuwu.learning.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     * @param adminVO
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(@RequestBody AdminVO adminVO){
        if (!StringUtils.hasText(adminVO.getUsername())){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        System.out.println("调用service进行处理");
        return loginService.login(adminVO);
    }

    /**
     * 注入的类型改为loginUser
     * @param loginUser
     * @return
     */
    @GetMapping("/getPrincipal")
    public BaseResponse getPrincipal(@AuthenticationPrincipal LoginUser loginUser) {
        log.debug("当事人用户id:{}", loginUser.getId());
        log.debug("当事人用户名是:{}", loginUser.getUsername());
        return ResultUtils.success(loginUser);
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public BaseResponse logout() {
        // 登出
        return loginService.logout();
    }

    /**
     * 修改密码
     *
     */
    @RequestMapping("/updatePassword")
    public BaseResponse updatePassword(@RequestBody AdminVO adminVO) {
        // 登出
        return loginService.updatePassword(adminVO);
    }

    /**
     * 用户注册
     */
    @RequestMapping("/register")
    public BaseResponse register(@RequestBody AdminVO adminVO){

        return loginService.register(adminVO);
    }



}