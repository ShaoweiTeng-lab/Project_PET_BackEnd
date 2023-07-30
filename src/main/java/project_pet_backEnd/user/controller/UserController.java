package project_pet_backEnd.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.user.dto.LoginResponse;
import project_pet_backEnd.user.dto.UserLoginRequest;
import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.user.dto.UserSignUpRequest;
import project_pet_backEnd.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){

        LoginResponse responseResult= userService.localSignIn(userLoginRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(responseResult );
    }
    @PostMapping("/user/customerSignUp")
    public ResponseEntity<?> localSignUp(@RequestBody  @Valid UserSignUpRequest userSignUpRequest){

        userService.localSignUp(userSignUpRequest);
        return  ResponseEntity.status(HttpStatus.OK).body("註冊成功" );
    }
    @GetMapping("/user/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestAttribute(name = "userId") String userId){
        UserProfileResponse userProfileResponse= userService.getUserProfile(userId);
        return  ResponseEntity.status(HttpStatus.OK).body(userProfileResponse);
    }
    @PostMapping("/user/generateCaptcha")
    public  ResponseEntity<LoginResponse> generateCaptcha(@RequestParam @Email String email){
        LoginResponse rs =userService.generateCaptcha(email);
        return  ResponseEntity.status(HttpStatus.OK).body(rs);
    }


}
