package project_pet_backEnd.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Component
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Autowired
    private WebsocketIdentityValid websocketIdentityValid;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        String token=req.getParameter("access_token");
        Map<String ,Object> attribute=websocketIdentityValid.validSession(token);
        //若不是帶token的 回傳false 擋掉 websocket 連線
        if(attribute==null)
            return  false;
        attributes.putAll(attribute);
        //傳入attribute
        return  super.beforeHandshake(request, response, wsHandler, attributes);
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }


}
