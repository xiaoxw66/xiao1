package com.xiao.common.websocket;

import com.google.common.collect.Lists;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(@Payload ChatMessageDTO chatMessage,
                                  SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("连接成功?");
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    // 这时另一套的配置
    @Resource
    WebSocketService webSocketService;

    @MessageMapping(WebSocketConfig.FORETOSERVERPATH)
//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendTo(WebSocketConfig.PRODUCERPATH)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public ChatMessageDTO say(ChatMessageDTO message) throws Exception {
        List<String> users = Lists.newArrayList();
        users.add("d892bf12bf7d11e793b69c5c8e6f60fb");//此处写死只是为了方便测试,此值需要对应页面中订阅个人消息的userId
        webSocketService.send2Users(users, new ChatMessageDTO());

        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setType(ChatMessageDTO.MessageType.CHAT);
        chatMessageDTO.setContent("222");
        chatMessageDTO.setSender("333");
        return chatMessageDTO;
    }

}
