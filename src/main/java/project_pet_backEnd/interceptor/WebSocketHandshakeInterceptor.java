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
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.vo.Manager;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.utils.ManagerJwtUtil;
import project_pet_backEnd.utils.UserJwtUtil;

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
        if(attribute==null)
            return  false;
        attributes.putAll(attribute);
        return  super.beforeHandshake(request, response, wsHandler, attributes);
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }


}
