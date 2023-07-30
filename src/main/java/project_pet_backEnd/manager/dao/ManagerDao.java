package project_pet_backEnd.manager.dao;

import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.util.List;

public interface ManagerDao {
    Manager getManagerByAccount(String username);
    List<Function>  getManagerRolesByManagerId(Integer managerId);

    void createManager(Manager createManagerData );
}
