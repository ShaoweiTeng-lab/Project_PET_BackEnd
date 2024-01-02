package project_pet_backEnd.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@EnableConfigurationProperties(RabbitProperties.class)
@Configuration
public class RabbitMqConfig {
    /**
     * 功能描述: 序列化
     *
     * @param connectionFactory
     * @return org.springframework.amqp.rabbit.core.RabbitTemplate
     * @author zhouwenjie
     * @date 2022/5/6 1:11
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitTemplateConfigurer rabbitTemplateConfigurer) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(converter);
        rabbitTemplateConfigurer.configure(rabbitTemplate,connectionFactory);
        return rabbitTemplate;
    }

    /**
     * 功能描述: 反序列化
     *
     * @param
     * @return org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
     * @author zhouwenjie
     * @date 2022/5/6 1:11
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer, ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factoryConfigurer.configure(factory,connectionFactory);
        return factory;
    }
}
