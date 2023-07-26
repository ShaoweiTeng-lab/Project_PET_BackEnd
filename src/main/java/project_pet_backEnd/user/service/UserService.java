package project_pet_backEnd.user.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.smtp.EmailService;
import project_pet_backEnd.smtp.dto.EmailResponse;
import project_pet_backEnd.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.user.dto.ResponseResult;
import project_pet_backEnd.user.dto.UserLoginRequest;
import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.user.dto.UserSignUpRequest;
import project_pet_backEnd.user.model.IdentityProvider;
import project_pet_backEnd.user.model.User;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.UserJwtUtil;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    EmailService emailService;

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserJwtUtil userJwtUtil;

    public  void  localSignUp(UserSignUpRequest userSignUpRequest){
        if(userDao.getUserByEmail(userSignUpRequest.getUserEmail())!=null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"已經有帳號註冊");//確認有無此帳號
        if(!validatedCaptcha(userSignUpRequest.getUserEmail(),userSignUpRequest.getCaptcha()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"驗證碼異常，請從新確認驗證碼");//確認驗證碼
        String encodePwd=bCryptPasswordEncoder.encode(userSignUpRequest.getUserPassword());
        userSignUpRequest.setUserPassword(encodePwd);
        userSignUpRequest.setIdentityProvider(IdentityProvider.Local);
        userDao.localSignUp(userSignUpRequest);
    }

    public  ResponseResult localSignIn(UserLoginRequest userLoginRequest){
        User validUser=userDao.getUserByEmail(userLoginRequest.getEmail());
        if(validUser==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"查無此帳號");//確認有無此帳號
        boolean isPasswordMatch  =bCryptPasswordEncoder.matches(userLoginRequest.getPassword(),validUser.getUserPassword());
        if(!isPasswordMatch)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"密碼錯誤");//驗證錯誤



        String jwt=userJwtUtil.createJwt(validUser.getUserId().toString());
        ResponseResult responseResult=new ResponseResult();
        responseResult.setMessage(jwt);
        return  responseResult;

    }

    private boolean  validatedCaptcha(String email,String captcha){
        String redisCapt= redisTemplate.opsForValue().get("MEMBER:"+ email);
        if(redisCapt==null || !redisCapt.equals(captcha))
            return  false;
        return  true;
    }


    public ResponseResult generateCaptcha(String email){
        String authCode=AllDogCatUtils.returnAuthCode();
        String  key ="MEMBER:"+ email;
        redisTemplate.opsForValue().set(key,authCode);
        redisTemplate.expire(key,10, TimeUnit.MINUTES);//十分鐘後過期
        ResponseResult rs=new ResponseResult();
        sendEmail(email,"請確認驗證碼","您的驗證碼為 : <br><p>"+authCode+"</p><br>請於十分鐘內輸入");
        rs.setMessage("generate_success");
        System.out.println(authCode);
        return  rs;
    }

    public  void  sendEmail(String to, String subject, String body){
        EmailResponse emailResponse =new EmailResponse(to,subject,body);
        emailService.sendEmail(emailResponse);
    }

    public UserProfileResponse getUserProfile(String userId){

        User user=userDao.getUserById(Integer.parseInt(userId));
        if(user==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"找不到使用者");
        UserProfileResponse userProfileResponse =new UserProfileResponse();
        userProfileResponse.setUserName(user.getUserName());
        userProfileResponse.setUserNickName(user.getUserNickName());
        int gender=user.getUserGender();
        switch (gender){
            case 0:
                userProfileResponse.setUserGender("女性");
                break;
            case 1:
                userProfileResponse.setUserGender("男性");
                break;
            case 2:
                userProfileResponse.setUserGender("尚未設定");
                break;
        }
        userProfileResponse.setUserAddress(user.getUserAddress());
        userProfileResponse.setUserBirthday(user.getUserBirthday());
        userProfileResponse.setUserPoint(user.getUserPoint());
        userProfileResponse.setUserPic(AllDogCatUtils.base64Encode(user.getUserPic()));
        userProfileResponse.setIdentityProvider(userProfileResponse.getIdentityProvider());
        userProfileResponse.setUserCreated(user.getUserCreated());
        return  userProfileResponse;
    }


}
