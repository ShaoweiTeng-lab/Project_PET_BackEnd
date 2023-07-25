package project_pet_backEnd.userLogin.dao;

import project_pet_backEnd.userLogin.dto.UserSignUpRequest;
import project_pet_backEnd.userLogin.model.User;

public interface UserDao  {
    void localSignUp(UserSignUpRequest userSignUpRequest);
    User getUserByEmail(String email);

    User getUserById(Integer id);

}
