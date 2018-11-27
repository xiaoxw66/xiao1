package com.xiao.user.controller;


import com.xiao.common.aop.AccessAop;
import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.ItemValidate;
import com.xiao.common.util.JsonUtil;
import com.xiao.common.util.ResponseUtil;
import com.xiao.common.util.StringUtil;
import com.xiao.user.dto.UserInfoDTO;
import com.xiao.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @AccessAop
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseData<UserInfoDTO> getUserInfo(HttpSession httpSession) {
        ResponseData<UserInfoDTO> responseData = ResponseUtil.getInstance();
        String userAccount = StringUtil.transformNullStr(httpSession.getAttribute(Constants.USER_ACCOUNT));
        try {
            UserInfoDTO userInfo = userInfoService.getUserInfo(userAccount);
            log.info("userInfo:{}", JsonUtil.toJSONString(userInfo));
            if (ItemValidate.isEmpty(userInfo)) {
                responseData.setCodeMsg(Constants.FAILED, "查询用户信息为空");
            } else {
                responseData.setCodeMsgData(Constants.SUCCESS, "查询完成", userInfo);
            }
        } catch (Exception e) {
            log.error("查询用户信息异常,账号: {}", userAccount, e);
            responseData.setCodeMsg(Constants.FAILED, "网络异常");
        }
        return responseData;
    }


}
