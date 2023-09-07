package project_pet_backEnd.socialMedia.activityUser.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.user.vo.User;

public interface UserNickNameDao extends JpaRepository<User, Integer> {
}
