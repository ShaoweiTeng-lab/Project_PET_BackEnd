package project_pet_backEnd.manager.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import project_pet_backEnd.manager.dao.ManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerLoginRequest;
import project_pet_backEnd.user.dto.loginResponse;

@Service
public class ManagerService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ManagerDao managerDao;
    public loginResponse createManager(CreateManagerRequest createManagerRequest){

        return  null;
    }

    public loginResponse managerLogin(ManagerLoginRequest managerLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(managerLoginRequest.getManagerAccount(),managerLoginRequest.getManagerPassword());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);

        return null;
    }
}
