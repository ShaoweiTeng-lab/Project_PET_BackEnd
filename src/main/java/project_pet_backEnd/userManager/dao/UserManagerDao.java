package project_pet_backEnd.userManager.dao;

import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dto.UserQueryParameter;

import java.util.List;

public interface UserManagerDao {
    List<User> getUsers(UserQueryParameter userQueryParameter);
}
