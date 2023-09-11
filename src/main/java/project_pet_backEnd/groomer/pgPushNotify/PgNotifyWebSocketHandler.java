package project_pet_backEnd.groomer.pgPushNotify;

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
public class PgNotifyWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String,WebSocketSession> sessionMap =new ConcurrentHashMap<>();

    //Jackson 庫提供的 Java 對象和 JSON 之間的映射器
    @Autowired
    private ObjectMapper objectMapper;

    //Redis
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String connector = (String) session.getAttributes().get("connect");
        if (!redisTemplate.hasKey("pgUserNotify:" + connector))
            redisTemplate.opsForList().leftPush("pgUserNotify:" + connector, "");
        sessionMap.put(connector + "-" + session.getId(), session);
    }

    //個人推播消息
    public  void  publishPersonalNotifyMsg(Integer userId, PgNotifyMsg pgNotifyMsg){

        String jsNotifyMsg = null;
        try {
            jsNotifyMsg = objectMapper.writeValueAsString(pgNotifyMsg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        TextMessage textMessage = new TextMessage(jsNotifyMsg);
        Set<String> notifyKeys = getKeys("pgUserNotify:userId_*");
        if (!notifyKeys.contains("pgUserNotify:userId_"+userId))
            redisTemplate.opsForList().leftPush("pgUserNotify:userId_" + userId, jsNotifyMsg);

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
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 當連線後先去 redis 拿對應的 history
        String userId = AllDogCatUtils.getKeyByValue(sessionMap, session).split("-")[0];
        Long lsSize= redisTemplate.opsForList().size("pgUserNotify:" + userId);
        for(long i =0; i<lsSize;i++){
            if(redisTemplate.opsForList().index("pgUserNotify:" + userId,0).equals(""))//最後一個不移除
                continue;
            String msg =redisTemplate.opsForList().leftPop("pgUserNotify:" + userId);
            session.sendMessage(new TextMessage(msg));
        }
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String key =AllDogCatUtils.getKeyByValue(sessionMap,session);
        sessionMap.remove(key);
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    public Set<String> getKeys(String keys) {
        return redisTemplate.keys(keys); // "*"表示匹配所有的 key
    }
}
