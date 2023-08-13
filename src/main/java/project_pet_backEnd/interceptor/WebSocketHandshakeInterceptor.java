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
    private UserJwtUtil userJwtUtil;
    @Autowired
    private ManagerJwtUtil managerJwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        String token=req.getParameter("access_token");
        SocketIdentity socketIdentity=null;
        if(token==null)
            return false;
        Claims uClaims= userJwtUtil.validateToken(token);
        if(uClaims!=null)
            socketIdentity=SocketIdentity.User;
        Claims mClaims= managerJwtUtil.validateToken(token);
        if(mClaims!=null)
            socketIdentity=SocketIdentity.Manager;
        if(socketIdentity==null)
            return false;
        switch (socketIdentity){
            case User :
                String userId=uClaims.getSubject();
                attributes.put("connect","userId_"+uClaims.getSubject());
                User user=userRepository.findById(Integer.parseInt(userId)).orElse(null);
                if(user==null)
                    return false;
                attributes.put("sender",user.getUserName());
                break;
            case Manager:
                String managerId=uClaims.getSubject();
                attributes.put("connect","managerId_"+mClaims.getSubject());
                Manager manager=managerRepository.findById(Integer.parseInt(managerId)).orElse(null);
                if(manager==null)
                    return false;
                attributes.put("sender",manager.getManagerAccount());
                break;
        }
        return  super.beforeHandshake(request, response, wsHandler, attributes);
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }


}
