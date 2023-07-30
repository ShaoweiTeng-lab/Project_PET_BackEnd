package project_pet_backEnd.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.manager.dao.ManagerDao;
import project_pet_backEnd.manager.dao.imp.ManagerDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.manager.dto.AdjustPermissionRequest;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerAuthorities;
import project_pet_backEnd.manager.dto.ManagerLoginRequest;
import project_pet_backEnd.manager.security.ManagerDetailsImp;
import project_pet_backEnd.manager.vo.Manager;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.ManagerJwtUtil;

import java.util.List;
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
    private ManagerDao managerDao;
    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    public ResultResponse createManager(CreateManagerRequest createManagerRequest){
        Manager manager=managerDao.getManagerByAccount(createManagerRequest.getManagerAccount());
        if(manager!=null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);

        manager =new Manager();
        manager.setManagerAccount(createManagerRequest.getManagerAccount());
        String encodePwd=bcryptEncoder.encode(createManagerRequest.getManagerPassword());
        manager.setManagerPassword(encodePwd);
        managerDao.createManager(manager);
        ResultResponse loginResponse=new ResultResponse();
        loginResponse.setMessage("新增成功");
        return loginResponse;
    }

    public ResultResponse managerLogin(ManagerLoginRequest managerLoginRequest) {
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
        redisTemplate.opsForValue().set("Manager_Login_"+managerDetail.getManager().getManagerId(),managerDetailJson);
        String jwt= managerJwtUtil.createJwt(managerId);
        ResultResponse responseResult=new ResultResponse();
        responseResult.setMessage(jwt);
        return  responseResult;
    }

    public  ResultResponse adjustPermission(AdjustPermissionRequest adjustPermissionRequest){

        return  null;
    }

    public  ResultResponse getManagerAuthorities(Integer managerId){
        ResultResponse rs =new ResultResponse();
        List<ManagerAuthorities> managerAuthorities=managerDao.getManagerAuthorities(managerId);
        rs.setMessage(managerAuthorities);
        return  rs;
    }
}
