package project_pet_backEnd.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.user.vo.User;

public interface UserRepository  extends JpaRepository<User,Integer> {

}
