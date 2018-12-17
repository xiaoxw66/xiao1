package com.xiao.common.filter;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @Description 过滤器暂时未使用
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/17 15:16
 * @Param 
 * @Return 
 **/
//@Component
public class DemoFilter implements Filter {
    @Override
    public void destroy() {
        //  Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //System.out.println("方法执行前");
        HttpSession session = ((HttpServletRequest) request).getSession();
        try {
            if (StringUtil.isEmptyStr(session.getAttribute(Constants.USER_ID))) {
                ResponseData responseData = new ResponseData();
                responseData.setCode(Constants.NOT_LOGIN);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("方法执行后");

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        //  Auto-generated method stub
    }

}
