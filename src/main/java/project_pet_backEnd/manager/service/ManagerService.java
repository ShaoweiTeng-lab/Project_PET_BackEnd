package project_pet_backEnd.manager.service;

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
    private ManagerDaoImp managerDao;
    public loginResponse createManager(CreateManagerRequest createManagerRequest){

        return  null;
    }

    public loginResponse managerLogin(ManagerLoginRequest managerLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(managerLoginRequest.getManagerAccount(),managerLoginRequest.getManagerPassword());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);
        System.out.println(Objects.isNull(authentication));
        if(Objects.isNull(authentication))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        ManagerDetailsImp managerDetail = (ManagerDetailsImp) authentication.getPrincipal();
        String managerId =String.valueOf( managerDetail.getManager().getManagerId());
        String jwt= managerJwtUtil.createJwt(managerId);
        loginResponse responseResult=new loginResponse();
        responseResult.setMessage(jwt);
        return  responseResult;
    }
}
