package project_pet_backEnd.productMall.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MallRedisHandleMessageService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private String root="ProductMallChat";
    public  List<String> getHistoryMsg(String sender, String receiver) {
        String key = new StringBuilder(root).append(":").append(sender).append(":").append(receiver).toString();
        List<String> historyData = redisTemplate.opsForList().range(key, 0, -1);
        return historyData;
    }

    public  void saveChatMessage(String sender, String receiver, String message) {
        // 對雙方來說，都要各存著歷史聊天記錄
        String senderKey = new StringBuilder(root).append(":").append(sender).append(":").append(receiver).toString();
        String receiverKey = new StringBuilder(root).append(":").append(receiver).append(":").append(sender).toString();
        redisTemplate.opsForList().rightPush(senderKey, message);
        redisTemplate.opsForList().rightPush(receiverKey, message);
    }
    //如果有設定nickName
    public  void saveNickName(String  userId,String nickName){
        String nickNameKey = new StringBuilder(root).append(":").append("userNickName").append(":").append(userId).toString();
        redisTemplate.opsForValue().set(nickNameKey,nickName);
    }
    public  String getNickName(String  userId){
        String nickNameKey = new StringBuilder(root).append(":").append("userNickName").append(":").append(userId).toString();
        return redisTemplate.opsForValue().get(nickNameKey);
    }
    public Set<String> getAllKeys(String key) {
        // 使用 RedisTemplate 的 keys 方法來獲取所有匹配的鍵
        Set<String> keys = redisTemplate.keys(key+"*"); // 此處的 * 表示匹配所有鍵

        // 返回所有鍵的集合
        return keys;
    }

    //設定尚未讀取訊息
    public  void setNotRead(String key,String userId){
        String notReadKey = new StringBuilder(root).append(":").append(key).append(":").append("NotRead").toString();
        redisTemplate.opsForSet().add(notReadKey,userId);
    }

    //更改為已經讀取
    public void removeFromSet(String key, String valueToRemove) {
        String notReadKey = new StringBuilder(root).append(":").append(key).append(":").append("NotRead").toString();
        redisTemplate.opsForSet().remove(notReadKey,valueToRemove);
    }
    //得到所有未讀的訊息
    public Set<String> getAllNotRead(String key) {
        String notReadKey = new StringBuilder(root).append(":").append(key).append(":").append("NotRead").toString();
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        return setOps.members(notReadKey);
    }

}
