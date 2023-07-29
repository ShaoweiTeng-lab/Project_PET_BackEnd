package project_pet_backEnd.manager.dao.imp;

import org.springframework.stereotype.Repository;
import project_pet_backEnd.manager.dao.ManagerDao;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.util.List;

@Repository
public class ManagerDaoImp implements ManagerDao {
    @Override
    public Manager getUserByAccount(String username) {
        return null;
    }

    @Override
    public List<Function> getManagerRolesByUserId(Integer managerId) {
        return null;
    }
}
