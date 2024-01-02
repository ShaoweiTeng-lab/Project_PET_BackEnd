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
import project_pet_backEnd.utils.AllDogCatUtils;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserNotifyWebSocketHandler extends TextWebSocketHandler {
    /**
     * 存放Session集合，方便推送消息 （javax.websocket.Session）
     */
    private static final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 在建立WebSocket連結後觸發方法
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //格式 userId_1-xxxxxxxxx
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String connector = (String) session.getAttributes().get("connect");
        //首次創建帳號者 先推入一個空字串
        if (!redisTemplate.hasKey("userNotify:" + connector))
            redisTemplate.opsForList().leftPush("userNotify:" + connector, "");
        sessionMap.put(connector + "-" + session.getId(), session);
    }

    //全部人推播消息
    public void publicNotifyMsg(NotifyMsg notifyMsg) throws Exception {
        String message = objectMapper.writeValueAsString(notifyMsg);
        Set<String> notifyKeys = getKeys("userNotify:*");
        String jsNotifyMsg = objectMapper.writeValueAsString(notifyMsg);
        //儲存訊息(沒上線也儲存)
        notifyKeys.forEach(notifyKey -> {
            redisTemplate.opsForList().leftPush(notifyKey, jsNotifyMsg);
        });
        //對所有人推播
        for (String key : sessionMap.keySet()) {
            TextMessage textMessage = new TextMessage(message);
            sessionMap.get(key).sendMessage(textMessage);
        }

    }
    //個人推播消息
    public  void  publishPersonalNotifyMsg(Integer userId,NotifyMsg notifyMsg){

        String jsNotifyMsg = null;
        try {
            jsNotifyMsg = objectMapper.writeValueAsString(notifyMsg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        TextMessage textMessage = new TextMessage(jsNotifyMsg);
        Set<String> notifyKeys = getKeys("userNotify:userId_*");
        //判斷使用者當前有無上線
        if (!notifyKeys.contains("userNotify:userId_"+userId))
            redisTemplate.opsForList().leftPush("userNotify:userId_" + userId, jsNotifyMsg);

        for (String key : sessionMap.keySet()) {
            if( key.split("-")[0].contains("userId_"+userId) &&sessionMap.get(key).isOpen()) {
                try {
                    sessionMap.get(key).sendMessage(textMessage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 處理收到的WebSocket消息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 當連線後先去 redis 拿對應的 history
        String userId = AllDogCatUtils.getKeyByValue(sessionMap, session).split("-")[0];
        Long lsSize= redisTemplate.opsForList().size("userNotify:" + userId);
        for(long i =0; i<lsSize;i++){
            if(redisTemplate.opsForList().index("userNotify:" + userId,0).equals(""))//最後一個(空字串部分)不移除 ，以便未來存入訊息
                continue;
            //傳輸消息
            String msg =redisTemplate.opsForList().leftPop("userNotify:" + userId);
            session.sendMessage(new TextMessage(msg));
        }
    }

    /**
     * 處理收到的錯誤消息
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * 關閉WebSocket連結後觸發方法
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //拿回key
        String key =AllDogCatUtils.getKeyByValue(sessionMap,session);
        sessionMap.remove(key);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 取得特定底下的key
     */
    public Set<String> getKeys(String keys) {
        return redisTemplate.keys(keys); // "*"表示匹配所有的 key
    }
}
