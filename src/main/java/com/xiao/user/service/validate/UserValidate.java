package com.xiao.user.service.validate;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.ResponseUtil;
import com.xiao.common.util.StringUtil;
import com.xiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidate {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录校验账号密码是否为空
     *
     * @param userAccount
     * @param password
     * @return
     */
    public ResponseData loginValidate(String userAccount, String password) {

        if (StringUtil.isEmptyStr(userAccount) || StringUtil.isEmptyStr(password)) {
            return ResponseUtil.getInstance(Constants.FAILED, "用户账号和密码不能为空");
        }
        return ResponseUtil.getInstance(Constants.SUCCESS);
    }

    /**
     * 查询用户信息入参用户账号不能为空
     *
     * @param userAccount
     * @return
     */
    public ResponseData getUserInfoValidte(String userAccount) {
        if (StringUtil.isEmptyStr(userAccount)) {
            return ResponseUtil.getInstance(Constants.FAILED, "用户账号不能为空");
        } else if (StringUtil.checkEmail(userAccount)) {
            return ResponseUtil.getInstance(Constants.FAILED, "请输入正确邮箱");
        }
        return ResponseUtil.getInstance(Constants.SUCCESS);
    }

    /**
     * 账号注册校验
     *
     * @param userAccount
     * @param password
     * @param passwordConfirm
     * @return
     */
    public ResponseData registerValidate(String userAccount, String password, String passwordConfirm, String validateCode) {
        if (StringUtil.isEmptyArr(userAccount, password, passwordConfirm, validateCode)) {
            return ResponseUtil.getInstance(Constants.FAILED, "用户账号密码不能为空");
        } else if (!StringUtil.checkEmail(userAccount)) {
            return ResponseUtil.getInstance(Constants.FAILED, "请输入正确邮箱");
        } else if (StringUtil.isNotEqual(password, passwordConfirm)) {
            return ResponseUtil.getInstance(Constants.FAILED, "两次填写的密码不一致");
        } else if (password.length() < Constants.STRING_LENGTH_6 || password.length() > Constants.STRING_LENGTH_16) {
            return ResponseUtil.getInstance(Constants.FAILED, "密码长度为6~16个字符，区分大小写");
        } else if (StringUtil.invalid(userInfoService.isExistAccount(userAccount))) {
            return ResponseUtil.getInstance(Constants.FAILED, "账号已存在,请登录");
        }
        return ResponseUtil.getInstance(Constants.SUCCESS);
    }


}
