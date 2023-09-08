package project_pet_backEnd.socialMedia.activityChat.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import project_pet_backEnd.socialMedia.activityChat.service.SocialRedisMesSub;

@Component
public class RedisAcConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer container() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(activityMessageListenerAdapter(), activityTopic());
        return redisMessageListenerContainer;
    }

    @Bean("activityMessageListenerAdapter")
    MessageListenerAdapter activityMessageListenerAdapter() {
        return new MessageListenerAdapter(new SocialRedisMesSub(), "onMessage");
    }

    @Bean("activityTopic")
    public ChannelTopic activityTopic() {
        return new ChannelTopic("social-media-channel");
    }




}
