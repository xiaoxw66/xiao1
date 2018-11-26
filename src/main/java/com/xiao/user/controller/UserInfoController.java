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
import com.xiao.user.service.validate.UserValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserValidate userValidate;

    @AccessAop
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseData<UserInfoDTO> getUserInfo(@RequestParam("userAccount") String userAccount) {
        ResponseData<UserInfoDTO> responseData = ResponseUtil.getInstance();
        try {
            ResponseData checkRes = userValidate.getUserInfoValidte(userAccount);
            if (StringUtil.invalid(checkRes)) {
                log.info("查询用户信息入参校验失败,用户账号不能为空");
                return checkRes;
            }

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
