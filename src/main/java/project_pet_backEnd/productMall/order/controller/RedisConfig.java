package project_pet_backEnd.productMall.order.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import project_pet_backEnd.productMall.order.dto.CartItemDTO;
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, CartItemDTO> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, CartItemDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用 JSON 序列化
        Jackson2JsonRedisSerializer<CartItemDTO> serializer = new Jackson2JsonRedisSerializer<>(CartItemDTO.class);
        template.setDefaultSerializer(serializer);

        return template;
    }
}