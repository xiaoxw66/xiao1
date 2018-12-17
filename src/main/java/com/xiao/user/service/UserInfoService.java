package com.xiao.user.service;

import com.xiao.common.baseDto.ResponseData;
import com.xiao.user.dto.UserInfoDTO;

public interface UserInfoService {


    UserInfoDTO getUserInfoLogin(String userAccount, String password) throws Exception;

    UserInfoDTO getUserInfo(String userAccount) throws Exception;

    ResponseData<UserInfoDTO> loginValidate(String userAccount, String password);

    ResponseData register(String userAccount, String password, String passwordConfirm);

    ResponseData isExistAccount(String userAccount);
}
