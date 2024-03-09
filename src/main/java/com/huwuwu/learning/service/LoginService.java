package com.huwuwu.learning.service;

import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.model.dto.AdminDTO;

public interface LoginService {

    BaseResponse login(AdminDTO adminDTO);

    BaseResponse logout();

    BaseResponse updatePassword(AdminDTO adminDTO);

    BaseResponse register(AdminDTO adminDTO);
}
