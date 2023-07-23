package project_pet_backEnd.userLogin.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.smtp.EmailService;
import project_pet_backEnd.smtp.dto.EmailResponse;
import project_pet_backEnd.userLogin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.userLogin.dto.ResponseResult;
import project_pet_backEnd.userLogin.dto.UserSignUpRequest;
import project_pet_backEnd.userLogin.model.IdentityProvider;
import project_pet_backEnd.userLogin.model.User;
import project_pet_backEnd.utils.AllDogCatUtils;

import javax.validation.constraints.Email;
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
        sendEmail(email,"請確認驗證碼","您的驗證碼為 : <br>"+authCode+"<br>請於十分中內輸入");
        rs.setMessage("generate_success");
        System.out.println(authCode);
        return  rs;
    }

    public  void  sendEmail(String to, String subject, String body){
        EmailResponse emailResponse =new EmailResponse(to,subject,body);
        emailService.sendEmail(emailResponse);
    }

    private User getUserProfile(Integer userId){
        return  userDao.getUserById(userId);
    }


}
