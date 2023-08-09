package project_pet_backEnd.socialMedia.chat.dao.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.chat.dao.ChatDao;
import project_pet_backEnd.socialMedia.chat.dto.PubSubMessage;

import java.util.Set;

@Repository
public class ChatImpl implements ChatDao {
    private static final String ROOM_KEY = "room:%s";
    private static final String ROOM_NAME_KEY = "room:%s:name";
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PatternTopic topic() {
        return new PatternTopic("user.*");
    }

    @Autowired
    private ObjectMapper objectMapper;

    // get all messages by roomId and config size
    @Override
    public Set<String> getMessages(String roomId, int offset, int size) {
        String roomNameKey = String.format(ROOM_KEY, roomId);
        return redisTemplate.opsForZSet().reverseRange(roomNameKey, offset, offset + size);
    }

    // save your message to chat room by roomId
    @Override
    public void saveMessage(PubSubMessage message) {
        String roomKey = String.format(ROOM_KEY, message.getRoomId());

        try {
            String jacksonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.opsForZSet().add(roomKey, objectMapper.writeValueAsString(message), message.getDate());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToRedis(String channel, String message) {
        redisTemplate.convertAndSend(topic().getTopic() + channel, message);

    }


}
