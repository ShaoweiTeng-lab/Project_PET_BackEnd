package project_pet_backEnd.productMall.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallRedisHandleMessageService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public  List<String> getHistoryMsg(String sender, String receiver) {
        String key = new StringBuilder(sender).append(":").append(receiver).toString();
        List<String> historyData = redisTemplate.opsForList().range(key, 0, -1);
        return historyData;
    }

    public  void saveChatMessage(String sender, String receiver, String message) {
        // 對雙方來說，都要各存著歷史聊天記錄
        String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
        String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
        redisTemplate.opsForList().rightPush(senderKey, message);
        redisTemplate.opsForList().rightPush(receiverKey, message);
    }
}
