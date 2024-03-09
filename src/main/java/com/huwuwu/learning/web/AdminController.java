package com.huwuwu.learning.web;

import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.model.dto.AdminDTO;
import com.huwuwu.learning.model.dto.LoginUser;
import com.huwuwu.learning.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     * @param adminDTO
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(AdminDTO adminDTO){
        System.out.println("调用service进行处理");
        return loginService.login(adminDTO);
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
        return ResultUtils.success("");
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
    public BaseResponse updatePassword(@RequestBody AdminDTO adminDTO) {
        // 登出
        return loginService.updatePassword(adminDTO);
    }

    /**
     * 用户注册
     */
    @RequestMapping("/register")
    public BaseResponse register(@RequestBody AdminDTO adminDTO){

        return loginService.register(adminDTO);
    }



}