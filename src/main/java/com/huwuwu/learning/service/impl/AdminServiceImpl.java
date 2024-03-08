package com.huwuwu.learning.service.impl;

import com.google.gson.Gson;
import com.huwuwu.learning.commons.utils.JwtUtil;
import com.huwuwu.learning.model.dto.AdminDetails;
import com.huwuwu.learning.model.dto.UserDTO;
import com.huwuwu.learning.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {



    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String login(UserDTO userDTO) {
        log.debug("开始处理验证登录的业务");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(), userDTO.getPassword()
        );
        Authentication authenticationResult = authenticationManager.authenticate(authentication);

        Map<String,Object> map = new HashMap<>(); // 准备用户信息

        AdminDetails adminDetails = (AdminDetails) authenticationResult.getPrincipal();
        map.put("id",adminDetails.getId());
        map.put("username",adminDetails.getUsername());
        log.debug("jwt中的数据包含,{}",map);

        String dataJson = new Gson().toJson(map);
        String jwt = JwtUtil.createJWT(dataJson);

        return jwt;
    }
}
