package com.xiao.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志切面 暂时不写日志
 * 切面的优先级可以通过实现 Ordered 接口或利用 @Order 注解指定.
 * 实现 Ordered 接口, getOrder() 方法的返回值越小, 优先级越高.
 * 若使用 @Order 注解, 序号出现在注解中，序号可以为任意整数（负数也可以），数值越小优先级越高。
 */
@Order(66)
@Aspect
@Component
public class LogAspect {


    @Pointcut("execution(public * com.xiao.*.controller.*.*(..))")
    public void webLog() {
    }

    // @Before("webLog()")
    //  public void doBefore(JoinPoint joinPoint) throws Throwable {
    //// 接收到请求，记录请求内容
    //ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //HttpServletRequest request = attributes.getRequest();
    //
    //ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    ////HttpServletRequest request = requestAttributes.getRequest();
    //HttpServletResponse response = requestAttributes.getResponse();
    ////从session里面获取对应的值 方式1
    //HttpSession httpSession = request.getSession();
    //System.out.println("session value: " + httpSession.getAttribute(Constants.BEING_PROCESSED));
    //// 方式2
    //String myValue = (String) requestAttributes.getAttribute(Constants.BEING_PROCESSED, RequestAttributes.SCOPE_SESSION);
    //System.out.println("myValue:" + myValue);
    //
    //
    //// 记录下请求内容
    //System.out.println("URL : " + request.getRequestURL().toString());
    //System.out.println("HTTP_METHOD : " + request.getMethod());
    //System.out.println("IP : " + request.getRemoteAddr());
    //System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    //System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    //}

    //@AfterReturning(returning = "ret", pointcut = "webLog()")
    //public void doAfterReturning(Object ret) throws Throwable {
    //    // 处理完请求，返回内容
    //    System.out.println("方法的返回值 : " + ret);
    //}

    ////后置异常通知
    //@AfterThrowing("webLog()")
    //public void throwss(JoinPoint jp) {
    //    System.out.println("方法异常时执行.....");
    //}
    //
    ////后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    //@After("webLog()")
    //public void after(JoinPoint jp) {
    //    System.out.println("方法最后执行.....");
    //}
    //
    ////环绕通知,环绕增强，相当于MethodInterceptor
    //@Around("webLog()")
    //public Object arround(ProceedingJoinPoint pjp) {
    //    System.out.println("方法环绕start.....");
    //    try {
    //        Object o = pjp.proceed();
    //        System.out.println("方法环绕proceed，结果是 :" + o);
    //        return o;
    //    } catch (Throwable e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}


}