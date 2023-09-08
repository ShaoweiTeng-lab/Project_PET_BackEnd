package project_pet_backEnd.socialMedia.activityChat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityChat.config.ActivityWebSocketHandler;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.NotifyMessage;
import project_pet_backEnd.socialMedia.activityChat.vo.PubSubMessage;
import project_pet_backEnd.socialMedia.util.DateUtils;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class SocialRedisMesSub implements MessageListener {


    @Autowired
    private ActivityWebSocketHandler webSocketHandler;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ObjectMapper objectMapper;

    //redis接收到訊息後的處理
    @Override
    public void onMessage(Message message, byte[] bytes) {
        //check message from which channel
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String channelStr = new String(channel, StandardCharsets.UTF_8);
        String bodyStr = new String(body, StandardCharsets.UTF_8);
        if (bodyStr.contains("roomId")) {
            try {
                PubSubMessage pubSubMessage = objectMapper.readValue(message.getBody(), PubSubMessage.class);
                //將message儲存到redis中
                roomDao.saveMessage(pubSubMessage);
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessage(pubSubMessage.getContent());
                chatMessage.setDate(DateUtils.intToString(pubSubMessage.getDate()));
                chatMessage.setUsername(pubSubMessage.getUsername());
                chatMessage.setRoomId(pubSubMessage.getRoomId());
                chatMessage.setUserId(pubSubMessage.getUserId());
                //透過websocket將message及時回傳給使用者
                webSocketHandler.sendMessageToRoomChannel(chatMessage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器出錯");
            }
        } else {
            try {
                NotifyMessage notifyMessage = objectMapper.readValue(message.getBody(), NotifyMessage.class);
                webSocketHandler.sendNotifyMessage(notifyMessage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器出錯");
            }

        }


    }


    //拿到聊天頻道
    public String getRoomId(String channel) {
        for (String s : channel.split("\\*", 2)) {
            if (!s.contains("social-media-channel.")) {
                return s;
            }
        }
        return null;
    }
}
