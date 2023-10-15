package project_pet_backEnd.user.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.user.dto.UserSignUpRequest;
import project_pet_backEnd.user.dto.oAuth.OAuthRequest;
import project_pet_backEnd.user.dto.oAuth.OAuthResponse;
import project_pet_backEnd.user.dto.oAuth.UserInfoResponse;
import project_pet_backEnd.user.service.OAuthService;
import project_pet_backEnd.user.dto.IdentityProvider;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.UserJwtUtil;


@Service
public class GoogleAuthServiceImp implements OAuthService {


    @Autowired
    private UserRepository userRepository;
    @Value("${google.client-id}")
    private String clientId;
    @Value("${google.client-secret}")
    private String clientSecret;

    @Autowired
    private UserJwtUtil userJwtUtil;
    /**
     * 第三方登入
     * */
    public ResultResponse oAuthLogin(OAuthRequest oauthRequest){
        //拿到 access Token
        OAuthResponse OAuthResponse =getGoogleToken(oauthRequest);
        //拿到 user Profile
        UserInfoResponse userInfoResponse=getUserInfo(OAuthResponse.getAccess_token());
        //驗證有無此使用者
        User user= userRepository.findByUserEmail(userInfoResponse.getEmail());
        ResultResponse rs =new ResultResponse();
        if(user!=null){
            //若有此使用者 生成token
            String token=generateLoginToken(user.getUserId().toString());
            rs.setMessage(token);
            return  rs;
        }
        //註冊一個 並登入
        UserSignUpRequest userSignUpRequest =new UserSignUpRequest();
        userSignUpRequest.setUserEmail(userInfoResponse.getEmail());
        userSignUpRequest.setUserName(userInfoResponse.getName());
        userSignUpRequest.setUserNickName(userInfoResponse.getName());
        byte[] userPic = AllDogCatUtils.downloadImageAsByteArray(userInfoResponse.getPicture());
        userSignUpRequest.setUserPic(userPic);
        userSignUpRequest.setIdentityProvider(IdentityProvider.Google);
        userSignUpRequest.setUserGender(2);
        Integer userId=oAuthSignUp(userSignUpRequest);
        String token=generateLoginToken(userId.toString());
        rs.setMessage(token);
        return  rs;
    }
    /**
     * 使用授權碼向Auth Server 拿 Auth Token
     * */
    public OAuthResponse getGoogleToken(OAuthRequest oauthRequest){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        //帶入授權碼 secret clientId
        parameters.add("code", oauthRequest.getCode());
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("redirect_uri", "http://localhost:5500");
//        parameters.add("redirect_uri", "https://yang-hung-fei.github.io");
        parameters.add("grant_type", "authorization_code");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        //送出Request
        ResponseEntity<OAuthResponse> responseEntity = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                requestEntity,
                OAuthResponse.class
        );

        OAuthResponse oauthResponse = responseEntity.getBody();
        return oauthResponse;
    }
    /**
     * 使用 Auth Token 向 Resource Server 拿 user Profile
     * */

    public UserInfoResponse getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);


        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);


        ResponseEntity<UserInfoResponse> responseEntity = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                requestEntity,
                UserInfoResponse.class
        );

        UserInfoResponse userInfoResponse = responseEntity.getBody();
        return userInfoResponse;
    }

    /**
     *生成登入token
     * */

    public String generateLoginToken(String sub){
        return userJwtUtil.createJwt(sub);
    }

    /**
     * 使用google 資訊註冊
     * */
    public  Integer oAuthSignUp(UserSignUpRequest userSignUpRequest){
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
        return  user.getUserId();
    }
}
