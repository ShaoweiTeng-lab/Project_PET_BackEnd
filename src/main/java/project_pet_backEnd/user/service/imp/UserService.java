package project_pet_backEnd.user.service.imp;

import project_pet_backEnd.user.dto.*;

public interface UserService {
    void  localSignUp(UserSignUpRequest userSignUpRequest);
    ResultResponse localSignIn(UserLoginRequest userLoginRequest);
    boolean  validatedCaptcha(String email,String captcha);
    ResultResponse generateCaptcha(String email);
    void  sendEmail(String to, String subject, String body);
    UserProfileResponse getUserProfile(Integer userId);
    ResultResponse adjustUserProfile(Integer userId, AdjustUserProfileRequest adjustUserProfileRequest);


}
