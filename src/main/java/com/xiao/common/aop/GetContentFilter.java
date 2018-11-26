package com.xiao.common.aop;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 株洲健坤北大青鸟 周钢
 */
//@Component
public class GetContentFilter implements Filter {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
    }

    private void login(ServletRequest request, ServletResponse response) {


    }
}
