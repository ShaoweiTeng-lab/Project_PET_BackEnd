package project_pet_backEnd.webSocketHandler.userNotify;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project_pet_backEnd.webSocketHandler.userNotify.dto.SocketMsg;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserNotifyWebSocketHandler extends TextWebSocketHandler {
    /**
     * 存放Session集合，方便推送消息 （javax.websocket.Session）
     * */
    private static final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 在建立WebSocket連結後觸發方法
     * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {


    }

    /**
     * 處理收到的WebSocket消息
     * */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload().toString());
        SocketMsg socketMsg;
        socketMsg=objectMapper.readValue(message.getPayload().toString(),SocketMsg.class);
        String channel=socketMsg.getChanel();
        String sessionKey= session.getId()+"_"+channel;//使用_隔開存入map
        System.out.println(sessionKey);
        if(!sessionMap.containsKey(sessionKey)||sessionMap.get(sessionKey).isOpen())
            sessionMap.put(sessionKey, session);
        System.out.println(sessionMap.size());
        for (String key : sessionMap.keySet()) {//將訊息傳遞給所有chanel相同的session
            if( key.contains(channel) &&sessionMap.get(key).isOpen())
                sessionMap.get(key).sendMessage(message);
        }
    }

    /**
     * 處理收到的錯誤消息
     * */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * 關閉WebSocket連結後觸發方法
     * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        if(sessionMap.containsValue(session))
            sessionMap.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
