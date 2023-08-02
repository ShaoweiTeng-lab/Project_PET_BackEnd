package project_pet_backEnd.manager.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.manager.vo.Manager;
@Repository
public interface  ManagerRepository   extends JpaRepository<Manager, Integer> {
    Manager findByManagerAccount(String manager_account);
}
