package project_pet_backEnd.user.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project_pet_backEnd.user.dto.oAuth.OAuthRequest;
import project_pet_backEnd.user.service.OAuthService;
import project_pet_backEnd.user.service.UserService;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Date;
@Slf4j
@Api(tags = "前台會員")
@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OAuthService oAuthService;
    @ApiOperation("使用者登入")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse<String>> login(@RequestBody @Valid UserLoginRequest userLoginRequest){

        ResultResponse responseResult= userService.localSignIn(userLoginRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(responseResult );
    }

    @ApiOperation("使用者註冊")
    @PostMapping("/signUp")
    public ResponseEntity<ResultResponse<String>> localSignUp(@RequestBody  @Valid UserSignUpRequest userSignUpRequest){
        ResultResponse rs =new ResultResponse();
        userService.localSignUp(userSignUpRequest);
        rs.setMessage("註冊成功" );
        return  ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    @ApiOperation("確認使用者帳號是否註冊")
    @PostMapping("/checkAccountIsSignUp")
    public  ResponseEntity<ResultResponse<String>> checkUserIsSingUp(@RequestParam @Email String email){
        ResultResponse rs =new ResultResponse();
        rs.setMessage(userService.checkUserIsSingUp(email));
        return  ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @ApiOperation("生成認證碼")
    @PostMapping("/generateCaptcha")
    public  ResponseEntity<ResultResponse<String>> generateCaptcha(@RequestParam @Email String email){
        ResultResponse rs =userService.generateCaptcha(email);
        return  ResponseEntity.status(HttpStatus.OK).body(rs);
    }


    /**取得個人資訊*/
    @ApiOperation("取得個人資訊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/profile")
    public ResponseEntity<ResultResponse<UserProfileResponse>> getUserProfile(@ApiParam(hidden = true)@RequestAttribute(name = "userId") Integer userId){
        ResultResponse rs =new ResultResponse();
        UserProfileResponse userProfileResponse= userService.getUserProfile(userId);
        rs.setMessage(userProfileResponse);
        return  ResponseEntity.status(HttpStatus.OK).body(rs);
    }


    /**
     * 修改個人資訊
     * */
    @ApiOperation("修改個人身分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/profile")
    public ResponseEntity<ResultResponse<String>> adjustUserProfile(
            @ApiParam(hidden = true)
            @RequestAttribute(name = "userId") Integer userId,
            @RequestParam(required = false) @NotBlank(message = "姓名不可為空") String userName,
            @RequestParam(required = false) @NotBlank(message = "匿稱不可為空")  String userNickName,
            @RequestParam(required = false) @Max(1) @Min(0) Integer userGender,
            @RequestParam(required = false) @NotBlank(message = "地址不可為空")  String userAddress,
            @RequestParam(required = false)
            @Size(max = 10, message = "電話長度不可大於10")
            @NotBlank(message = "電話不可為空")  String userPhone,
            @RequestParam(required = false) Date userBirthday,
            @RequestParam(required = false) MultipartFile userPic
            ){
        AdjustUserProfileRequest adjustUserProfileRequest=new AdjustUserProfileRequest();
        adjustUserProfileRequest.setUserName(userName);
        adjustUserProfileRequest.setUserNickName(userNickName);
        adjustUserProfileRequest.setUserGender(userGender);
        adjustUserProfileRequest.setUserPhone(userPhone);
        adjustUserProfileRequest.setUserAddress(userAddress);
        adjustUserProfileRequest.setUserBirthday(userBirthday);
        adjustUserProfileRequest.setUserPic(AllDogCatUtils.convertMultipartFileToByteArray(userPic));
        userService.adjustUserProfile(userId,adjustUserProfileRequest);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改成功");
        return  ResponseEntity.status(200).body(rs);
    }
    /**
     * 修改密碼
     * */
    @ApiOperation("修改密碼")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/password")
    public  ResultResponse<String> adjustPassword(@ApiParam(hidden = true) @RequestAttribute(name = "userId") Integer userId,
                                                  @RequestParam @NotBlank(message = "密碼不可為空")  String userPassword){

        userService.adjustPassword(userId,userPassword);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改成功");
        return  rs;
    }

    /**
     * 第三方登入
     * */
    @ApiOperation("google 登入 ")
    @PostMapping("/googleLogin")
    public ResponseEntity<ResultResponse<String>> googleLogin(@RequestBody OAuthRequest oAuthRequest){
        ResultResponse rs =oAuthService.oAuthLogin(oAuthRequest);
        return ResponseEntity.ok().body(rs);
    }


    /**
     * 使用者忘記密碼
     * */
    @ApiOperation("使用者忘記密碼 ")
    @PostMapping("/forgetPassword")
    public ResponseEntity<ResultResponse<String>> forgetPassword(@RequestParam @Email String userEmail){
        ResultResponse rs= userService.forgetPassword(userEmail);
        return ResponseEntity.ok().body(rs);
    }
    /**
     * 使用者忘記密碼
     * */
    @ApiOperation("忘記密碼更改密碼 ")
    @PostMapping("/forgetPassword/renewPassword")
    public ResponseEntity<ResultResponse<String>> forgetRenewPassword(@RequestParam  String code,@RequestParam String newPassword){
        ResultResponse rs= userService.forgetRenewPassword(code,newPassword);
        return ResponseEntity.ok().body(rs);
    }
}
