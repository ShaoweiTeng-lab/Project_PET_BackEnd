package project_pet_backEnd.user.service.imp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.smtp.EmailService;
import project_pet_backEnd.smtp.dto.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.dto.*;
import project_pet_backEnd.user.service.UserService;
import project_pet_backEnd.user.vo.IdentityProvider;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.UserJwtUtil;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserJwtUtil userJwtUtil;

    @Value("${renewPasswordUrl}")
    private  String renewPasswordUrl;

    public  void  localSignUp(UserSignUpRequest userSignUpRequest){
        if(userRepository.findByUserEmail(userSignUpRequest.getUserEmail())!=null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"已經有人註冊此帳號");//確認有無此帳號
        if(!validatedCaptcha(userSignUpRequest.getUserEmail(),userSignUpRequest.getCaptcha()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"驗證碼異常，請從新確認驗證碼");//確認驗證碼
        String encodePwd=bCryptPasswordEncoder.encode(userSignUpRequest.getUserPassword());
        userSignUpRequest.setUserPassword(encodePwd);
        userSignUpRequest.setIdentityProvider(IdentityProvider.Local);
        User user =new User();
        user.setUserName(userSignUpRequest.getUserName());
        user.setUserNickName(userSignUpRequest.getUserNickName());
        user.setUserGender(userSignUpRequest.getUserGender());
        user.setUserEmail(userSignUpRequest.getUserEmail());
        user.setUserPassword(userSignUpRequest.getUserPassword());
        user.setUserPhone(userSignUpRequest.getUserPhone());
        user.setUserPic(userSignUpRequest.getUserPic());
        user.setUserAddress(userSignUpRequest.getUserAddress());
        user.setUserBirthday(userSignUpRequest.getUserBirthday());
        user.setIdentityProvider(userSignUpRequest.getIdentityProvider());
        userRepository.save(user);
    }

    public ResultResponse localSignIn(UserLoginRequest userLoginRequest){
        User validUser=userRepository.findByUserEmail(userLoginRequest.getEmail());
        if(validUser==null || validUser.getIdentityProvider()!=IdentityProvider.Local)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"查無此帳號");//確認有無此帳號 或帳號屬於
        boolean isPasswordMatch  =bCryptPasswordEncoder.matches(userLoginRequest.getPassword(),validUser.getUserPassword());
        if(!isPasswordMatch)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"密碼錯誤");//驗證錯誤
        String jwt=userJwtUtil.createJwt(validUser.getUserId().toString());
        ResultResponse responseResult=new ResultResponse();

        responseResult.setMessage(jwt);
        return  responseResult;

    }

    public boolean  validatedCaptcha(String email,String captcha){
        String redisCapt= redisTemplate.opsForValue().get("MEMBER:"+ email);
        if(redisCapt==null || !redisCapt.equals(captcha))
            return  false;
        return  true;
    }


    public ResultResponse generateCaptcha(String email){
        //先判斷有無註冊過
        User user =userRepository.findByUserEmail(email);
        if(user!=null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"已有人註冊此信箱");
        String authCode=AllDogCatUtils.returnAuthCode();
        String  key ="MEMBER:"+ email;
        redisTemplate.opsForValue().set(key,authCode);
        redisTemplate.expire(key,10, TimeUnit.MINUTES);//十分鐘後過期
        ResultResponse rs=new ResultResponse();
        sendEmail(email,"請確認驗證碼","您的驗證碼為 : <br><p>"+authCode+"</p><br>請於十分鐘內輸入");
        rs.setMessage("generate_success");
        System.out.println(authCode);
        return  rs;
    }

    public  void  sendEmail(String to, String subject, String body){
        EmailResponse emailResponse =new EmailResponse(to,subject,body);
        emailService.sendEmail(emailResponse);
    }

    public UserProfileResponse getUserProfile(Integer userId){

        User user=userRepository.findById(userId).orElse(null);
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
        userProfileResponse.setUserPhone(user.getUserPhone());
        userProfileResponse.setUserAddress(user.getUserAddress());
        userProfileResponse.setUserBirthday(user.getUserBirthday());
        userProfileResponse.setUserPoint(user.getUserPoint());
        userProfileResponse.setUserPic(AllDogCatUtils.base64Encode(user.getUserPic()));
        userProfileResponse.setIdentityProvider(user.getIdentityProvider());
        userProfileResponse.setUserCreated(AllDogCatUtils.timestampToDateFormat(user.getUserCreated()));
        return  userProfileResponse;
    }
    @Override
    public   ResultResponse adjustUserProfile(Integer userId,AdjustUserProfileRequest adjustUserProfileRequest){
        User user =userRepository.findById(userId).orElse(null);//先檢查有無此使用者
        if(user==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此使用者");
        User adjustUser =new User();
        adjustUser.setUserId(userId);
        adjustUser.setUserName(adjustUserProfileRequest.getUserName()==null?user.getUserName():adjustUserProfileRequest.getUserName());
        adjustUser.setUserNickName(adjustUserProfileRequest.getUserNickName()==null?user.getUserNickName():adjustUserProfileRequest.getUserNickName());
        adjustUser.setUserGender(adjustUserProfileRequest.getUserGender()==null?user.getUserGender():adjustUserProfileRequest.getUserGender());
        adjustUser.setUserEmail(user.getUserEmail());
        adjustUser.setUserPhone(adjustUserProfileRequest.getUserPhone()==null?user.getUserPhone():adjustUserProfileRequest.getUserPhone());
        adjustUser.setUserAddress(adjustUserProfileRequest.getUserAddress()==null?user.getUserAddress():adjustUserProfileRequest.getUserAddress());
        adjustUser.setUserBirthday(adjustUserProfileRequest.getUserBirthday()==null?user.getUserBirthday():adjustUserProfileRequest.getUserBirthday());
        adjustUser.setUserPoint(user.getUserPoint());
        adjustUser.setUserPic(adjustUserProfileRequest.getUserPic()==null?user.getUserPic():adjustUserProfileRequest.getUserPic());
        adjustUser.setIdentityProvider(user.getIdentityProvider());
        adjustUser.setUserCreated(user.getUserCreated());
        userRepository.save(adjustUser);
        return  new ResultResponse();
    }

    @Override
    public void adjustPassword(Integer userId, String password) {
        User user =userRepository.findById(userId).orElse(null);//先檢查有無此使用者
        if(user==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此使用者");
        if(user.getIdentityProvider()!=IdentityProvider.Local)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"此帳戶不可修改密碼");
        String pwd =bCryptPasswordEncoder.encode(password);
        user.setUserPassword(pwd);
        userRepository.save(user);
    }

    @Override
    public ResultResponse forgetPassword(String userEmail) {
        User user= userRepository.findByUserEmail(userEmail);
        if(user==null||user.getIdentityProvider()!=IdentityProvider.Local)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此使用者");
        String uuid= AllDogCatUtils.generateUUID();
        redisTemplate.opsForValue().set(uuid,userEmail);
        redisTemplate.expire(uuid,10, TimeUnit.MINUTES);//十分鐘後過期
        sendEmail(userEmail,
                "修改密碼通知",
                "請點選以下連接更改您的密碼:<br>"+
                        renewPasswordUrl+"?code="+uuid);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("送出成功");
        return rs;
    }
    @Transactional
    @Override
    public ResultResponse forgetRenewPassword(String code, String newPassword) {
        String email= redisTemplate.opsForValue().get(code);
        if(email==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"認證網址已經過期，請重新寄送驗證信!");
        User user =userRepository.findByUserEmail(email);
        if(user==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此使用者");
        String pwd =bCryptPasswordEncoder.encode(newPassword);
        user.setUserPassword(pwd);
        userRepository.save(user);
        ResultResponse rs =new ResultResponse();
        redisTemplate.delete(code);
        rs.setMessage("修改成功");
        return rs;
    }

    @Override
    public String checkUserIsSingUp(String email) {
        User user =userRepository.findByUserEmail(email);
        if(user==null)
            return ("此帳號可以註冊");
        return ("此帳號已有人註冊");
    }
}
