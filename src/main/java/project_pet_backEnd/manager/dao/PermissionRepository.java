package project_pet_backEnd.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.manager.vo.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    @Modifying
    @Query(value = "INSERT INTO permission (MANAGER_ID, FUNCTION_ID) " +
            "SELECT :managerId, f.FUNCTION_ID " +
            "FROM `function` f " +
            "WHERE f.FUNCTION_NAME IN :functionNames", nativeQuery = true)
    void batchUpdatePermission(@Param("managerId") Integer managerId, @Param("functionNames") List<String> functionNames);
}
