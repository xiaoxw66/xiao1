package com.xiao.common.websocket;

import lombok.Data;

/**
 * @Description webSocket消息多谢
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/3 21:16
 * @Param
 * @Return
 **/
@Data
public class ChatMessageDTO {

    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}
