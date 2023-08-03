package project_pet_backEnd.user.dao;

import project_pet_backEnd.user.dto.UserSignUpRequest;
import project_pet_backEnd.user.vo.User;

public interface UserDao  {
    Integer localSignUp(UserSignUpRequest userSignUpRequest);
    User getUserByEmail(String email);

    User getUserById(Integer id);

}
