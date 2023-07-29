package project_pet_backEnd.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.manager.dao.imp.ManagerDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerLoginRequest;
import project_pet_backEnd.manager.security.ManagerDetailsImp;
import project_pet_backEnd.user.dto.loginResponse;
import project_pet_backEnd.utils.ManagerJwtUtil;

import java.util.Objects;

@Service
public class ManagerService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  ManagerJwtUtil managerJwtUtil;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ManagerDaoImp managerDao;
    @Autowired
    private  ObjectMapper objectMapper;
    public loginResponse createManager(CreateManagerRequest createManagerRequest){

        return  null;
    }

    public loginResponse managerLogin(ManagerLoginRequest managerLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(managerLoginRequest.getManagerAccount(),managerLoginRequest.getManagerPassword());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        ManagerDetailsImp managerDetail = (ManagerDetailsImp) authentication.getPrincipal();
        String managerId =String.valueOf( managerDetail.getManager().getManagerId());
        ObjectMapper objectMapper =new ObjectMapper();
        String managerDetailJson=null;
        try {
            managerDetailJson=objectMapper.writeValueAsString(managerDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(managerDetailJson);
        redisTemplate.opsForValue().set("Manager_Login_"+managerDetail.getManager().getManagerId(),managerDetailJson);
        String jwt= managerJwtUtil.createJwt(managerId);
        loginResponse responseResult=new loginResponse();
        responseResult.setMessage(jwt);
        return  responseResult;
    }
}
