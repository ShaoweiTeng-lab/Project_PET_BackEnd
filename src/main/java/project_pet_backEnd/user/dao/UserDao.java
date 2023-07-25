package project_pet_backEnd.user.dao;

import project_pet_backEnd.user.dto.UserSignUpRequest;
import project_pet_backEnd.user.model.User;

public interface UserDao  {
    void localSignUp(UserSignUpRequest userSignUpRequest);
    User getUserByEmail(String email);

    User getUserById(Integer id);

}
