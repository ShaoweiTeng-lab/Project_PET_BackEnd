package project_pet_backEnd.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.vo.Manager;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.utils.ManagerJwtUtil;
import project_pet_backEnd.utils.UserJwtUtil;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebsocketIdentityValid {
    @Autowired
    private UserJwtUtil userJwtUtil;
    @Autowired
    private ManagerJwtUtil managerJwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public Map<String, Object> validSession(String token) {
        if (token == null)
            return null;
        Claims uClaims = userJwtUtil.validateToken(token);
        SocketIdentity socketIdentity = null;
        if (uClaims != null)
            socketIdentity = SocketIdentity.User;
        Claims mClaims = managerJwtUtil.validateToken(token);
        if (mClaims != null)
            socketIdentity = SocketIdentity.Manager;
        if (socketIdentity == null)
            return null;
        Map<String, Object> attributes = new HashMap<>();
        switch (socketIdentity) {
            case User:
                String userId = uClaims.getSubject();
                attributes.put("connect", "userId_" + uClaims.getSubject());
                User user = userRepository.findById(Integer.parseInt(userId)).orElse(null);
                if (user == null)
                    return null;
                attributes.put("sender", user.getUserNickName());
                break;
            case Manager:
                String managerId = mClaims.getSubject();
                attributes.put("connect", "managerId_" + mClaims.getSubject());
                Manager manager = managerRepository.findById(Integer.parseInt(managerId)).orElse(null);
                if (manager == null)
                    return null;
                attributes.put("sender", manager.getManagerAccount());
                break;
        }
        return  attributes;
    }
}
