package com.huwuwu.learning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huwuwu.learning.model.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.User;

import java.security.Policy;


@Mapper
public interface AdminDAO extends BaseMapper<Admin> {


    Admin selectByUsername(String username);
}
