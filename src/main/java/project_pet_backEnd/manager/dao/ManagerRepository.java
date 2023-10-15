package project_pet_backEnd.manager.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * 如果 :account為NULL，則所有的"Manager"資料都會被選擇。
     * 如果 :account 不為NULL，則"Manager"資料中的"managerAccount"必須包含":account"的值（不區分大小寫）。
     * */
    @Query("SELECT m FROM Manager m WHERE (:account IS NULL OR m.managerAccount LIKE CONCAT('%', :account, '%'))")
    Page<Manager> findByManagerAccount(@Param("account") String account, Pageable pageable);

    @Modifying
    @Query(value = "delete from permission p where p.MANAGER_ID = ?1", nativeQuery = true)
    void deleteAllAuthoritiesById(Integer manager_id);
}

