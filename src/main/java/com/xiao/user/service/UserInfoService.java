package com.xiao.user.service;

import com.xiao.common.dto.ResponseData;
import com.xiao.user.dto.UserInfoDTO;

public interface UserInfoService {


    UserInfoDTO getUserInfoLogin(String userAccount, String password) throws Exception;

    UserInfoDTO getUserInfo(String userAccount) throws Exception;

    ResponseData<UserInfoDTO> loginValidate(String userAccount, String password);
}
