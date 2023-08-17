package project_pet_backEnd.config.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import project_pet_backEnd.userPushNotify.UserNotifyWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    @Qualifier("webSocketHandshakeInterceptor")
    private HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor;

    @Autowired
    private UserNotifyWebSocketHandler userNotifyWebSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userNotifyWebSocketHandler, "/websocket")// 添加處理器
                .setAllowedOrigins("*")
                .addInterceptors(httpSessionHandshakeInterceptor);
    }

}
