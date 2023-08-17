package project_pet_backEnd.userPushNotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project_pet_backEnd.webSocketHandler.userNotify.dto.SocketMsg;

import java.util.Set;
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
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String connector=(String) session.getAttributes().get("connect"); 
        if(!redisTemplate.hasKey("userNotify:"+connector))
            redisTemplate.opsForList().leftPush("userNotify:"+connector, "");
        sessionMap.put(connector+"-"+session.getId(),session);
        System.out.println(connector+"-"+session.getId());
    }
    //推播消息
    public  void  publicNotifyMsg(NotifyMsg notifyMsg) throws Exception {
        String message= objectMapper.writeValueAsString(notifyMsg);
        //todo 先從redis 拿出有訂閱的userId 再檢查當前上線的session ，若無 則放進history(儲存格式 userNotify:UserId)
        Set<String> notifyKeys=getKeys("userNotify:*");
        String jsNotifyMsg=objectMapper.writeValueAsString(notifyMsg);
        if(sessionMap.keySet().size()==0){
            //代表沒人上線 將所有推撥存入history
            notifyKeys.forEach(notifyKey->{
                redisTemplate.opsForList().leftPush(notifyKey,jsNotifyMsg);
            });
        }

        for (String key : sessionMap.keySet()) {
            String userId=key.split("_")[0];
            if(!notifyKeys.contains(userId))
                redisTemplate.opsForList().leftPush("userNotify:"+userId,jsNotifyMsg);
            TextMessage textMessage = new TextMessage(message);
            sessionMap.get(key).sendMessage(textMessage);
        }

    }
    /**
     * 處理收到的WebSocket消息
     * */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //todo 當連線後先去 redis 拿取對應的 history
//        System.out.println(message.getPayload().toString());
//        SocketMsg socketMsg;
//        socketMsg=objectMapper.readValue(message.getPayload().toString(),SocketMsg.class);
//        String channel=socketMsg.getChanel();
//        String sessionKey= session.getId()+"_"+channel;//使用_隔開存入map
//        System.out.println(sessionKey);
//        if(!sessionMap.containsKey(sessionKey)||sessionMap.get(sessionKey).isOpen())
//            sessionMap.put(sessionKey, session);
//        System.out.println(sessionMap.size());
//        for (String key : sessionMap.keySet()) {//將訊息傳遞給所有chanel相同的session
//            if( key.contains(channel) &&sessionMap.get(key).isOpen())
//                sessionMap.get(key).sendMessage(message);
//        }

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
    /**
     * 取得特定底下的key
     * */
    public Set<String> getKeys(String keys) {
        return redisTemplate.keys(keys); // "*"表示匹配所有的 key
    }
}
