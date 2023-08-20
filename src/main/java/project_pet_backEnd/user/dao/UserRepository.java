package project_pet_backEnd.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project_pet_backEnd.manager.vo.Manager;
import project_pet_backEnd.user.vo.User;

public interface UserRepository  extends JpaRepository<User,Integer> {
    User findByUserEmail(String email);
    @Query(value = "SELECT * FROM `user` u WHERE (?1 IS NULL OR u.user_Name LIKE CONCAT('%', ?1, '%'))",nativeQuery = true)
    Page<User> findByUserAccount(String name, Pageable pageable);
}
