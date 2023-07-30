package project_pet_backEnd.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project_pet_backEnd.manager.dao.ManagerDao;
import project_pet_backEnd.manager.dao.imp.ManagerDaoImp;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerDetailsServiceImp implements UserDetailsService {
    @Autowired
    private ManagerDao managerDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Manager manager =managerDao.getManagerByAccount(username);

        if (manager == null) {
            throw new UsernameNotFoundException("無此Manager");
        }
        List<Function> functions=managerDao.getManagerRolesByManagerId(manager.getManagerId());
        List<String> permissionsList=new ArrayList();
        if(functions.size()>0){
            for (Function function: functions) {
                permissionsList.add(function.getFunctionName());
            }
        }
        return new ManagerDetailsImp(manager,permissionsList);
    }
}
