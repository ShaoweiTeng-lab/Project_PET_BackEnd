package project_pet_backEnd.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import project_pet_backEnd.manager.security.ManagerDetailsImp;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class ProductMallWebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Autowired
    private WebsocketIdentityValid websocketIdentityValid;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        String token=req.getParameter("access_token");
        System.out.println("進入驗證中!");
        Map<String ,Object> attribute=websocketIdentityValid.validSession(token);
        if(attribute==null)
            return  false;
        //todo 判斷Manager 的token 是否具備 商城管理員權限
        if(!validManagerPermission(attribute))
            return  false;
        attributes.putAll(attribute);
        return  super.beforeHandshake(request, response, wsHandler, attributes);
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }

    boolean validManagerPermission(Map<String ,Object>attribute) throws JsonProcessingException {

        String connect =(String) attribute.get("connect");
        if(!connect.contains("managerId"))//如果是user 放行
            return  true;
        //格式 managerId_1
        Integer managerId=Integer.parseInt( connect.split("_")[1]);
        String managerLoginJson=redisTemplate.opsForValue().get("Manager:Login:"+managerId);
        ManagerDetailsImp managerDetail=null;
        //判斷權限有無 商城管理
        managerDetail=objectMapper.readValue(managerLoginJson,ManagerDetailsImp.class);
        List<String> permissionList= managerDetail.getPermissionsList();
        if(permissionList.contains("商城管理"))
            return  true;
        return  false;
    }

}
