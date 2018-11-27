package com.xiao.user.mapper;

import com.xiao.user.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    UserInfoDTO getUserInfoLogin(@Param("userAccount") String userAccount, @Param("password") String password) throws Exception;

    UserInfoDTO getUserInfo(@Param("userAccount") String userAccount) throws Exception;

}
