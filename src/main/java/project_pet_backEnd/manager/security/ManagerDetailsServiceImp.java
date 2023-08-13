package project_pet_backEnd.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.manager.dao.ManagerDao;
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.dao.imp.ManagerDaoImp;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerDetailsServiceImp implements UserDetailsService {
    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private  ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {

        Manager manager =managerRepository.findByManagerAccount(username);

        if (manager == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        }
        List<String> functions=managerRepository.findManagerFunctionsById(manager.getManagerId());
        List<String> permissionsList=new ArrayList();
        if(functions.size()>0){
            for (String function: functions) {
                permissionsList.add(function);
            }
        }
        return new ManagerDetailsImp(manager,permissionsList);
    }
}
