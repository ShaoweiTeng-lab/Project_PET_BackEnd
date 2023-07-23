package project_pet_backEnd.userLogin.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.userLogin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.userLogin.dto.UserSignUpRequest;
import project_pet_backEnd.userLogin.model.IdentityProvider;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public  void  localSignUp(UserSignUpRequest userSignUpRequest){
        if(userDao.getUserByEmail(userSignUpRequest.getUserEmail())!=null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"已經有帳號註冊");//確認有無此帳號
        if(!validatedCaptcha(userSignUpRequest.getCaptcha()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"驗證碼異常，請從新確認驗證碼");//確認驗證碼
        String encodePwd=bCryptPasswordEncoder.encode(userSignUpRequest.getUserPassword());
        userSignUpRequest.setUserPassword(encodePwd);
        userSignUpRequest.setIdentityProvider(IdentityProvider.Local);
        userDao.localSignUp(userSignUpRequest);
    }

    private boolean  validatedCaptcha(String captcha){
        //從redis 確認
        return  true;
    }
}
