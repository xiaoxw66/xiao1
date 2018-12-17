package com.xiao.common.aop;

import com.xiao.common.baseDto.Constants;
import com.xiao.common.util.ResponseUtil;
import com.xiao.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录校验 未登录 拦截接口返回code2
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class LoginAspect {

    @Pointcut("execution(public * com.xiao.*.controller.*Controller.*(..))")
    public void login() {

    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("login()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpServletResponse response = requestAttributes.getResponse();

            HttpSession session = request.getSession();
            int maxInactiveInterval = session.getMaxInactiveInterval();// session 过期时间,单位秒 1800 半小时
            log.info("session.getMaxInactiveInterval():{}", maxInactiveInterval);
            if (StringUtil.isEmptyStr(session.getAttribute(Constants.USER_ID))) {
                return ResponseUtil.getInstance(Constants.NOT_LOGIN, "本次访问需要登录");
            } else {
                Object responseData = pjp.proceed();
                return responseData;
            }
        } catch (Throwable e) {
            log.error("登录校验LoginAspect异常", e);
            return ResponseUtil.getInstance(Constants.NOT_LOGIN, "本次访问需要登录");
        }
    }

}
