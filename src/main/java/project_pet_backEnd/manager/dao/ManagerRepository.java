package project_pet_backEnd.manager.dao;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.util.List;

@Repository
public interface  ManagerRepository   extends JpaRepository<Manager, Integer> {
    Manager findByManagerAccount(String manager_account);
    @Query(value = "SELECT f.FUNCTION_NAME " +
            "FROM manager m " +
            "JOIN permission p ON m.MANAGER_ID = p.MANAGER_ID " +
            "JOIN `function` f ON p.FUNCTION_ID = f.FUNCTION_ID " +
            "WHERE m.MANAGER_ID = ?1", nativeQuery = true)
    List<String> findManagerFunctionsById(Integer manager_id);
    @Query(value = "SELECT f.FUNCTION_NAME " +
            "FROM manager m " +
            "JOIN permission p ON m.MANAGER_ID = p.MANAGER_ID " +
            "JOIN `function` f ON p.FUNCTION_ID = f.FUNCTION_ID " +
            "WHERE m.MANAGER_ACCOUNT = ?1", nativeQuery = true)
    List<String> findManagerFunctionsByAccount(String account);


}

