package project_pet_backEnd.socialMedia.activityChat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import project_pet_backEnd.socialMedia.activityChat.dto.NotifyMessage;
import project_pet_backEnd.socialMedia.activityChat.vo.PubSubMessage;

@Component
public class RedisMessagePublisher {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    @Qualifier("activityTopic")
    private ChannelTopic topic;


    public RedisMessagePublisher() {
    }

    public void publish(PubSubMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    public void notify(NotifyMessage notifyMessage) {
        redisTemplate.convertAndSend(topic.getTopic(), notifyMessage);
    }
}
