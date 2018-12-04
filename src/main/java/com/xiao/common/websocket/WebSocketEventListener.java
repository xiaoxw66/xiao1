package com.xiao.common.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Created by rajeevkumarsingh on 25/07/17.
 */
@Component
@Slf4j
public class WebSocketEventListener {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * @Description socket连接成功后执行这里
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/3 20:43
     * @Param [event]
     * @Return void
     **/
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        log.info("Received a new web socket connection  登录人数计数之类的可以在这里添加");


    }

    /**
     * @Description socket断开连接前执行这里
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/3 20:44
     * @Param [event]
     * @Return void
     **/
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected : " + username);

            ChatMessageDTO chatMessage = new ChatMessageDTO();
            chatMessage.setType(ChatMessageDTO.MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
