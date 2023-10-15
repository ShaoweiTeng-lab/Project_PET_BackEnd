package project_pet_backEnd.config.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import project_pet_backEnd.groomer.pgPushNotify.PgNotifyWebSocketHandler;
import project_pet_backEnd.interceptor.ProductMallWebSocketHandshakeInterceptor;
import project_pet_backEnd.productMall.chat.ProductMallWebSocketHandler;
import project_pet_backEnd.socialMedia.activityChat.config.ActivityWebSocketHandler;
import project_pet_backEnd.userPushNotify.UserNotifyWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    @Qualifier("webSocketHandshakeInterceptor")
    private HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor;
    @Autowired
    private PgNotifyWebSocketHandler pgNotifyWebSocketHandler;
    @Autowired
    private UserNotifyWebSocketHandler userNotifyWebSocketHandler;
    @Autowired
    private ProductMallWebSocketHandler productMallWebSocketHandler;
    @Autowired
    private ProductMallWebSocketHandshakeInterceptor productMallWebSocketHandshakeInterceptor;
    @Autowired
    private ActivityWebSocketHandler activityWebSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userNotifyWebSocketHandler, "/websocket/userNotify")// 添加處理器
                .setAllowedOrigins("*")
                .addInterceptors(httpSessionHandshakeInterceptor);//設置攔截器
        registry.addHandler(productMallWebSocketHandler, "/websocket/productMallChat")
                .setAllowedOrigins("*")
                .addInterceptors(productMallWebSocketHandshakeInterceptor);
        registry.addHandler(activityWebSocketHandler, "/websocket/activity")
                .setAllowedOrigins("*")
                .addInterceptors(httpSessionHandshakeInterceptor);
        registry.addHandler(pgNotifyWebSocketHandler, "/websocket/pgNotify")
                .setAllowedOrigins("*")
                .addInterceptors(httpSessionHandshakeInterceptor);
    }

}
