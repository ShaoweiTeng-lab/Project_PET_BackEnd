package project_pet_backEnd.userLogin.controller;

import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.userLogin.dto.UserLoginRequest;
import project_pet_backEnd.userLogin.dto.UserSignUpRequest;
import project_pet_backEnd.userLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/user/login")
    public ResponseEntity<?> login(){

        return  ResponseEntity.status(HttpStatus.OK).body("登入成功" );
    }
    @PostMapping("/user/customerSignUp")
    public ResponseEntity<?> localSignUp(@RequestBody  @Valid UserSignUpRequest userSignUpRequest){

        userService.localSignUp(userSignUpRequest);
        return  ResponseEntity.status(HttpStatus.OK).body("註冊成功" );
    }
    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile(@RequestAttribute String userId){

        return  ResponseEntity.status(HttpStatus.OK).body("使用者訊息");
    }
}
