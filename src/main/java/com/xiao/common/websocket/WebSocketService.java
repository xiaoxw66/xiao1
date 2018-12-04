package com.xiao.common.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/3 23:51
 */
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param msg
     */
    public void sendMsg(ChatMessageDTO msg) {
        template.convertAndSend(WebSocketConfig.PRODUCERPATH, msg);
    }

    /**
     * 发送给指定用户
     *
     * @param users
     * @param msg
     */
    public void send2Users(List<String> users, ChatMessageDTO msg) {
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, WebSocketConfig.P2PPUSHPATH, msg);
        });
    }
}
