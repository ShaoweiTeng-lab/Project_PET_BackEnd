package project_pet_backEnd.manager.dao;

import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.util.List;

public interface ManagerDao {
    Manager getUserByAccount(String username);
    List<Function>  getManagerRolesByUserId(Integer managerId);
}
