package project_pet_backEnd.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.vo.Manager;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerDetailsServiceImp implements UserDetailsService { 
    @Autowired
    private  ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        //查詢管理員
        Manager manager =managerRepository.findByManagerAccount(username);

        if (manager == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        }
        List<String> functions=managerRepository.findManagerFunctionsById(manager.getManagerId());
        //查權限
        List<String> permissionsList=new ArrayList();
        if(functions.size()>0){
            for (String function: functions) {
                permissionsList.add(function);
            }
        }
        //回傳一個 userDetails
        return new ManagerDetailsImp(manager,permissionsList);
    }
}
