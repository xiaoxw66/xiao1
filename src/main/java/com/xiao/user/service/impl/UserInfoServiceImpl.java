package com.xiao.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.ItemValidate;
import com.xiao.common.util.JsonUtil;
import com.xiao.common.util.MD5Util;
import com.xiao.common.util.ResponseUtil;
import com.xiao.user.dto.UserInfoDTO;
import com.xiao.user.dto.UserInfoPasswordDTO;
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

    /**
     * @Description 通过密码和账号查询用户信息 登录时使用
     * @Author yexiaomu
     * @Date 2018/11/27 20:16
     * @Param [userAccount, password]
     * @Return com.xiao.user.dto.UserInfoDTO
     **/
    @Override
    public UserInfoDTO getUserInfoLogin(String userAccount, String password) throws Exception {
        UserInfoDTO userInfoDTO = userInfoMapper.getUserInfoLogin(userAccount, password);
        return userInfoDTO;
    }

    /**
     * @Description 通过账号查询用户信息
     * @Author yexiaomu
     * @Date 2018/11/27 20:16
     * @Param [userAccount]
     * @Return com.xiao.user.dto.UserInfoDTO
     **/
    @Override
    public UserInfoDTO getUserInfo(String userAccount) throws Exception {
        UserInfoDTO userInfo = userInfoMapper.getUserInfo(userAccount);
        return userInfo;
    }

    /**
     * @Description 登录校验
     * @Author yexiaomu
     * @Date 2018/11/27 20:17
     * @Param [userAccount, password]
     * @Return com.xiao.common.dto.ResponseData
     **/
    @Override
    public ResponseData loginValidate(String userAccount, String password) {
        try {
            String passwordMd5 = MD5Util.MD5EncodePassword(userAccount, password);
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

    /**
     * @Description 账号注册
     * @Author yexiaomu
     * @Date 2018/11/27 20:19
     * @Param [userAccount, password, passwordConfirm]
     * @Return void
     **/
    @Override
    public ResponseData register(String userAccount, String password, String passwordConfirm) {
        UserInfoPasswordDTO userInfoPasswordDTO = new UserInfoPasswordDTO();
        try {
            userInfoPasswordDTO.setPassword(MD5Util.MD5EncodePassword(userAccount, password));
            userInfoPasswordDTO.setUserAccount(userAccount);
            userInfoPasswordDTO.setCreatedBy(userAccount);
            userInfoPasswordDTO.setUpdatedBy(userAccount);
            userInfoMapper.addUser(userInfoPasswordDTO);
            log.info("账号注册完成:{}", JSON.toJSONString(userInfoPasswordDTO));
            return ResponseUtil.getInstance(Constants.SUCCESS, "注册成功");
        } catch (Exception e) {
            log.error("注册账号异常:{}", JSON.toJSONString(userInfoPasswordDTO), e);
            return ResponseUtil.getInstance(Constants.FAILED, "注册账号异常");
        }
    }

    /**
     * @Description 注册时校验账号是否存在 success 为不存在
     * @Author xiaoxuewang_vendor
     * @Date 2018/11/28 16:38
     * @Param [userAccount]
     * @Return com.xiao.common.dto.ResponseData
     **/
    @Override
    public ResponseData isExistAccount(String userAccount) {
        try {
            UserInfoDTO userInfo = this.getUserInfo(userAccount);
            log.info("isExistAccount userInfo:{}", JsonUtil.toJSONString(userInfo));
            if (ItemValidate.isEmpty(userInfo)) {
                return ResponseUtil.getInstance(Constants.SUCCESS);
            } else {
                return ResponseUtil.getInstance(Constants.FAILED);
            }
        } catch (Exception e) {
            log.error("校验用户是否存在异常,userAccount:{}", userAccount, e);
            return ResponseUtil.getInstance(Constants.FAILED);
        }
    }
}
