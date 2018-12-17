package com.xiao.user.controller;

import com.xiao.common.baseDto.Constants;
import com.xiao.common.baseDto.ResponseData;
import com.xiao.common.session.SessionHelper;
import com.xiao.common.util.*;
import com.xiao.user.dto.UserInfoDTO;
import com.xiao.user.service.UserInfoService;
import com.xiao.user.service.validate.UserValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/login")
@EnableAutoConfiguration
public class LoginAction {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserValidate userValidate;

    /**
     * @Description 账号登录
     * @Author xiaoxuewang_vendor
     * @Date 2018/11/29 11:26
     * @Param [userAccount, password, validateCode, request]
     * @Return com.xiao.common.baseDto.ResponseData
     **/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestParam("userAccount") String userAccount, @RequestParam("password") String password,
                              @RequestParam("validateCode") String validateCode, HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        ResponseData responseData = ResponseUtil.getInstance();
        ResponseData responseValidate = userValidate.loginValidate(userAccount, password);
        if (StringUtil.invalid(responseValidate)) {
            log.info("校验失败,登录账号或密码不能为空");
            return responseValidate;
        }
        // 登录校验验证码 // TODO 登录暂时取消验证码
        //ResponseData checkValidateCode = this.checkValidateCode(validateCode, currentSession);
        //if (StringUtil.invalid(checkValidateCode)) {
        //    return checkValidateCode;
        //}

        // 如果同一个session登录两个账号,或者说是多次登录
        // 则把已经登录的sessionId获取到
        Object oldUserId = currentSession.getAttribute(Constants.USER_ID);

        ResponseData<UserInfoDTO> userInfoDtoRes = userInfoService.loginValidate(userAccount, password);
        if (StringUtil.validate(userInfoDtoRes)) {
            UserInfoDTO userInfoDto = userInfoDtoRes.getData();
            String currentUserId = userInfoDto.getUserId();
            currentSession.setAttribute(Constants.USER_ID, currentUserId);
            currentSession.setAttribute(Constants.USER_NAME, userInfoDto.getUserName());
            currentSession.setAttribute(Constants.USER_DTO, userInfoDto);
            currentSession.setAttribute(Constants.USER_ACCOUNT, userInfoDto.getUserAccount());
            currentSession.removeAttribute(RandomValidateCodeUtil.RANDOM_CODE_KEY);
            log.info("登录成功,账号:{}", userAccount);

            // 登录成功 从map中获取session
            // 用当前登录userId从静态map中获取
            // 获取到session对象不为空则表示之前已经登录过
            log.info("当前登录sessionId:{}", currentSession.getId());
            HttpSession oldSession = SessionHelper.getSession(currentUserId);
            // 两种场景:
            // 1.同一个浏览器登录不同个的账号
            // 如果同一个session登录两个账号则 把已经登录的和当前登录的userId拿出来比较
            // 不相同则把旧的session从map中删除  并且旧的不为空,如果为空表示第一次登录
            if (StringUtil.isNotEqual(oldUserId, currentUserId) && StringUtil.isNotEmptyStr(oldUserId)) {
                log.info("如果同一个session登录两个账号则 把已经登录的和当前登录的userId拿出来比较,不相同则把旧的session从map中删除,oldUserId:{},currentUserId:", oldUserId, currentUserId);
                SessionHelper.removeSession(StringUtil.transformNullStr(oldUserId));
            }

            if (ItemValidate.isNotEmpty(oldSession)) {
                // 2.不同的浏览器登录同一个账号
                String oldSessionId = oldSession.getId();
                String newSessionId = currentSession.getId();
                // 两个sessionId不相等 则将旧session销毁,
                // 并将当前session放入map
                // 相等的表示当前session多次登录 保持不变
                if (StringUtil.isNotEqual(oldSessionId, newSessionId)) {
                    oldSession.invalidate();
                    log.info("loginAction销毁session之后");
                    SessionHelper.addSession(currentUserId, currentSession);
                } else {
                    log.info("同一个账号多次登录");
                }
            } else {
                // 为空 则表示之前没有登录 将当前session放入map
                SessionHelper.addSession(currentUserId, currentSession);
            }
            responseData.setCode(Constants.SUCCESS);
        } else {
            responseData.setCodeMsg(Constants.FAILED, userInfoDtoRes.getMsg());
        }
        log.info("输出下sessionMap:{}", SessionHelper.getInstance());
        return responseData;
    }

    /**
     * @Description 校验账号是否存在
     * @Author yexiaomu
     * @Date 2018/11/27 21:39
     * @Param [userAccount]
     * @Return com.xiao.common.baseDto.ResponseData
     **/
    @RequestMapping(value = "/isExistAccount", method = RequestMethod.GET)
    public ResponseData isExistAccount(String userAccount) {
        ResponseData checkRes = userValidate.getUserInfoValidte(userAccount);
        if (StringUtil.invalid(checkRes)) {
            log.info("查询用户信息入参校验失败,用户账号不能为空");
            return checkRes;
        }
        return userInfoService.isExistAccount(userAccount);
    }

    /**
     * 账号注册
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData register(String userAccount, String password, String confirmPassword, String validateCode, HttpServletRequest request) {
        ResponseData responseData = userValidate.registerValidate(userAccount, password, confirmPassword, validateCode);
        if (StringUtil.invalid(responseData)) {
            return responseData;
        }
        ResponseData checkValidateCode = this.checkValidateCode(validateCode, request.getSession());
        if (StringUtil.invalid(checkValidateCode)) {
            return checkValidateCode;
        }
        return userInfoService.register(userAccount, password, confirmPassword);
    }

    /**
     * @Description 生成验证码
     * @Author xiaoxuewang_vendor
     * @Date 2018/11/29 11:14
     * @Param [request, response]
     * @Return void
     **/
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandomCode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            log.error("获取验证码失败>>>>   ", e);
        }
    }

    /**
     * @Description 校验验证码 不区分大小写
     * @Author xiaoxuewang_vendor
     * @Date 2018/11/29 11:13
     * @Param [validateCode, session]
     * @Return boolean
     **/
    @RequestMapping(value = "/checkValidateCode", method = RequestMethod.POST)
    public ResponseData checkValidateCode(@RequestParam("validateCode") String validateCode, HttpSession session) {
        try {
            //从session中获取生成的验证码
            String sessionValidateCode = StringUtil.transformNullStr(session.getAttribute(RandomValidateCodeUtil.RANDOM_CODE_KEY));
            log.info("验证码校验入参:{},session中验证码:{}", validateCode, sessionValidateCode);
            if (StringUtil.isEqual(StringUtil.toUpperCase(validateCode), sessionValidateCode)) {
                log.info("验证码校验成功");
                return ResponseUtil.getInstance(Constants.SUCCESS);
            }
            log.info("验证码校验失败");
            return ResponseUtil.getInstance(Constants.FAILED, "验证码校验失败");
        } catch (Exception e) {
            log.error("验证码校验异常", e);
            return ResponseUtil.getInstance(Constants.FAILED, "验证码校验异常");
        }
    }
}
