package com.huwuwu.learning.service;

import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.model.vo.AdminVO;

public interface LoginService {

    BaseResponse login(AdminVO adminVO);

    BaseResponse logout();

    BaseResponse updatePassword(AdminVO adminVO);

    BaseResponse register(AdminVO adminDTO);
}
