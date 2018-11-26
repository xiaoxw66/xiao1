package com.xiao.user.service.validate;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.ResponseUtil;
import com.xiao.common.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class UserValidate {
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
    public ResponseData registerValidate(String userAccount, String password, String passwordConfirm) {
        if (StringUtil.checkEmail(userAccount)) {

        }

        return ResponseUtil.getInstance();
    }


}
