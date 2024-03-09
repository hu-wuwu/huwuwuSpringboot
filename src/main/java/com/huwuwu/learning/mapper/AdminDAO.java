package com.huwuwu.learning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huwuwu.learning.model.po.AdminPO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AdminDAO extends BaseMapper<AdminPO> {


    AdminPO selectByUsername(String username);
}
