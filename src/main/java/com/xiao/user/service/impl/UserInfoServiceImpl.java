package com.xiao.user.service.impl;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.ItemValidate;
import com.xiao.common.util.MD5Util;
import com.xiao.common.util.ResponseUtil;
import com.xiao.user.dto.UserInfoDTO;
import com.xiao.user.mapper.UserInfoMapper;
import com.xiao.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfoDTO getUserInfoLogin(String userAccount, String password) throws Exception {
        UserInfoDTO userInfoDTO = userInfoMapper.getUserInfoLogin(userAccount, password);
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO getUserInfo(String userAccount) throws Exception {
        UserInfoDTO userInfo = userInfoMapper.getUserInfo(userAccount);
        return userInfo;
    }

    public ResponseData loginValidate(String userAccount, String password) {
        try {
            String passwordMd5 = MD5Util.MD5EncodePassword(password);
            UserInfoDTO userInfoDTO = this.getUserInfoLogin(userAccount, passwordMd5);
            // 查询失败 为 null
            if (ItemValidate.isEmpty(userInfoDTO)) {
                return ResponseUtil.getInstance(Constants.FAILED, "账号或密码错误");
            } else {
                ResponseData<UserInfoDTO> userInfoDTORes = ResponseUtil.getInstance(Constants.SUCCESS, "登录成功");
                userInfoDTORes.setData(userInfoDTO);
                return userInfoDTORes;
            }
        } catch (Exception e) {
            log.error("UserInfoServiceImpl.loginValidate异常,账号:{}", userAccount, e);
            return ResponseUtil.getInstance(Constants.FAILED, "网络异常");
        }
    }


}
