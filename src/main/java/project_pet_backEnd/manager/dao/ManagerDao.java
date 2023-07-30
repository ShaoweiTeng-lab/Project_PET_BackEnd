package project_pet_backEnd.manager.dao;

import org.springframework.data.relational.core.sql.In;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerAuthorities;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.security.Permission;
import java.util.List;

public interface ManagerDao {
    Manager getManagerByAccount(String username);
    List<Function>  getManagerRolesByManagerId(Integer managerId);

    void createManager(Manager createManagerData );

    List<ManagerAuthorities> getManagerAuthorities(Integer managerId);
}
