package com.xiao.common.aop;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.StringUtil;
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
 * 接口请求校验
 */
@Order(1)
@Aspect
@Component
public class AccessAspect {

    @Pointcut(value = "@annotation(com.xiao.common.aop.AccessAop)")
    public void access() {

    }


    //环绕通知,环绕增强，相当于MethodInterceptor  AccessAop 接口没有添加AccessAop注解则不会进入这个方法
    @Around("@annotation(accessAop)")
    public Object arround(ProceedingJoinPoint pjp, AccessAop accessAop) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpSession session = request.getSession();
        String processFlag = StringUtil.transformNullStr(session.getAttribute(Constants.BEING_PROCESSED));
        ResponseData responseData = new ResponseData();
        try {
            if (StringUtil.isEqual(processFlag, true)) {
                responseData.setCode(Constants.FAILED);
                responseData.setMsg("处理中,请稍后");
                return responseData;
            } else {
                session.setAttribute(Constants.BEING_PROCESSED, true);
                Object responseData1 = pjp.proceed();
                session.setAttribute(Constants.BEING_PROCESSED, false);
                return responseData1;
            }
        } catch (Throwable e) {
            responseData.setCode(Constants.FAILED);
            responseData.setMsg("处理中,请稍后");
            return responseData;
        }
    }
}
