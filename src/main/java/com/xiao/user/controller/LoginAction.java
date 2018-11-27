package com.xiao.user.controller;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.session.SessionHelper;
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

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/account")
@EnableAutoConfiguration
public class LoginAction {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserValidate userValidate;

    /**
     * 账号登录
     *
     * @param userAccount
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestParam("userAccount") String userAccount, @RequestParam("password") String password, HttpSession session) {
        ResponseData responseData = new ResponseData();
        ResponseData responseValidate = userValidate.loginValidate(userAccount, password);
        if (StringUtil.invalid(responseValidate)) {
            log.info("校验失败,登录账号或密码不能为空");
            return responseValidate;
        }
        // 如果同一个session登录两个账号,或者说是多次登录
        // 则把已经登录的sessionId获取到
        Object oldUserId = session.getAttribute(Constants.USER_ID);

        ResponseData<UserInfoDTO> userInfoDtoRes = userInfoService.loginValidate(userAccount, password);
        if (StringUtil.validate(userInfoDtoRes)) {
            UserInfoDTO userInfoDto = userInfoDtoRes.getData();
            String userId = userInfoDto.getUserId();
            session.setAttribute(Constants.USER_ID, userId);
            session.setAttribute(Constants.USER_NAME, userInfoDto.getUserName());
            session.setAttribute(Constants.USER_DTO, userInfoDto);
            session.setAttribute(Constants.USER_ACCOUNT, userInfoDto.getUserAccount());
            log.info("登录成功,账号:{}", userAccount);

            // 登录成功 从map中获取session
            // 获取到session对象不为空则表示之前已经登录过
            log.info("当前登录sessionId:{}", session.getId());
            HttpSession oldSession = SessionHelper.getSession(userId);
            // 两种场景:
            // 1.同一个浏览器登录不同个的账号
            Object newUserId = session.getAttribute(Constants.USER_ID);
            // 如果同一个session登录两个账号则 把已经登录的和当前登录的userId拿出来比较
            // 不相同则把旧的session从map中删除  并且旧的不为空,如果为空表示第一次登录
            if (StringUtil.isNotEqual(oldUserId, newUserId) && StringUtil.isNotEmptyStr(oldUserId)) {
                log.info("如果同一个session登录两个账号则 把已经登录的和当前登录的userId拿出来比较,不相同则把旧的session从map中删除,oldUserId:{}", oldUserId);
                SessionHelper.removeSession(StringUtil.transformNullStr(oldUserId));
            }

            if (ItemValidate.isNotEmpty(oldSession)) {
                // 2.不同的浏览器登录同一个账号
                String oldSessionId = oldSession.getId();
                String newSessionId = session.getId();
                // 两个sessionId不相等 则将旧session销毁,
                // 并将当前session放入map
                // 相等的表示当前session多次登录 保持不变
                if (StringUtil.isNotEqual(oldSessionId, newSessionId)) {
                    oldSession.invalidate();
                    log.info("loginAction销毁session之后");
                    SessionHelper.addSession(userId, session);
                } else {
                    log.info("同一个账号多次登录");
                }
            } else {
                // 为空 则表示之前没有登录 将当前session放入map
                SessionHelper.addSession(userId, session);
            }
            responseData.setCode(Constants.SUCCESS);
        } else {
            responseData.setCodeMsg(Constants.FAILED, userInfoDtoRes.getMsg());
        }
        log.info("输出下sessionMap:{}", SessionHelper.getInstance());
        return responseData;
    }

    /**
     * 账号登出
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseData logout(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseUtil.getInstance(Constants.SUCCESS, "登出成功");
    }

    /**
     * @Description 校验账号是否存在
     * @Author yexiaomu
     * @Date 2018/11/27 21:39
     * @Param [userAccount]
     * @Return com.xiao.common.dto.ResponseData
     **/
    @RequestMapping(value = "/isExistAccount", method = RequestMethod.GET)
    public ResponseData isExistAccount(String userAccount) {
        try {
            ResponseData checkRes = userValidate.getUserInfoValidte(userAccount);
            if (StringUtil.invalid(checkRes)) {
                log.info("查询用户信息入参校验失败,用户账号不能为空");
                return checkRes;
            }
            UserInfoDTO userInfo = userInfoService.getUserInfo(userAccount);
            log.info("userInfo:{}", JsonUtil.toJSONString(userInfo));
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

    /**
     * 账号注册
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData register(String userAccount, String password, String confirmPassword) {
        ResponseData responseData = userValidate.registerValidate(userAccount, password, confirmPassword);
        if (StringUtil.invalid(responseData)) {
            return responseData;
        }
        return userInfoService.register(userAccount, password, confirmPassword);
    }
}
