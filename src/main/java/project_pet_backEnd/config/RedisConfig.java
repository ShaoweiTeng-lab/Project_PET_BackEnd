package project_pet_backEnd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import project_pet_backEnd.productMall.order.dto.CartItemDTO;
import project_pet_backEnd.socialMedia.activityChat.service.SocialRedisMesSub;

@Configuration
public class RedisConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @Bean
    public RedisTemplate<String, CartItemDTO> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, CartItemDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用 JSON 序列化
        Jackson2JsonRedisSerializer<CartItemDTO> serializer = new Jackson2JsonRedisSerializer<>(CartItemDTO.class);
        template.setDefaultSerializer(serializer);

        return template;
    }



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