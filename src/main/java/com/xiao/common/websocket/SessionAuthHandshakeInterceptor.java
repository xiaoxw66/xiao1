package com.xiao.common.websocket;

import com.alibaba.fastjson.JSON;
import com.xiao.common.baseDto.Constants;
import com.xiao.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description 拦截连接webSocket时获取不到session情况
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/3 21:18
 * @Param
 * @Return
 **/
@Slf4j
@Component
public class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpSession session = getSession(request);
        if (StringUtil.isEmptyStr(session) || StringUtil.isEmptyStr(session.getAttribute(Constants.USER_ID))) {
            log.info("未登录,连接webSocket拒绝");
            //return false;
        }
        Object userDto = session.getAttribute(Constants.USER_DTO);
        log.info("webSocket连接成功,用户:{}", JSON.toJSONString(userDto));
        attributes.put(Constants.USER_DTO, userDto);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    // 参考 HttpSessionHandshakeInterceptor
    // getSession(false) 当没有session时返回null
    // 不加false 当没有session时会create 一个session
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}