package project_pet_backEnd.webSocketHandler.userNotify.dto;

import lombok.Data;

@Data
public class SocketMsg {
    /**
     * 頻道
     **/
    private String chanel;

    /**
     * 發送者
     **/
    private String sender;
    /**
     * 消息
     **/
    private String msg;
}
