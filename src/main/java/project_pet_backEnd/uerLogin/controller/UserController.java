package project_pet_backEnd.uerLogin.controller;

import project_pet_backEnd.uerLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/user/login")
    public ResponseEntity<?> login(){

        return  ResponseEntity.status(HttpStatus.OK).body("登入成功" );
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile(@RequestAttribute String userId){

        return  ResponseEntity.status(HttpStatus.OK).body("使用者訊息");
    }
}
