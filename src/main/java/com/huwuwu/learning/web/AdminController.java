package com.huwuwu.learning.web;

import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.model.dto.AdminDetails;
import com.huwuwu.learning.model.dto.UserDTO;
import com.huwuwu.learning.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admins")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录接口
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(UserDTO userDTO){
        System.out.println("调用service进行处理");
        String jwt = adminService.login(userDTO);
        return ResultUtils.success(jwt);
    }

    /**
     * 注入的类型改为AdminDetails
     * @param adminDetails
     * @return
     */
    @GetMapping("/getPrincipal")
    public BaseResponse getPrincipal(@AuthenticationPrincipal AdminDetails adminDetails) {
        log.debug("当事人用户id:{}",adminDetails.getId());
        log.debug("当事人用户名是:{}",adminDetails.getUsername());
        //获取权限
        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();
        return ResultUtils.success("");
    }


}