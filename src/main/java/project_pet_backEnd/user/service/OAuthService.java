package project_pet_backEnd.user.service;


import project_pet_backEnd.user.dto.UserSignUpRequest;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.user.dto.oAuth.OAuthRequest;
import project_pet_backEnd.user.dto.oAuth.OAuthResponse;
import project_pet_backEnd.user.dto.oAuth.UserInfoResponse;

public interface OAuthService {

    OAuthResponse getGoogleToken(OAuthRequest oAuthRequest);


    UserInfoResponse getUserInfo(String accessToken);

    ResultResponse oAuthLogin(OAuthRequest oauthRequest);

    Integer oAuthSignUp(UserSignUpRequest userSignUpRequest);

    String generateLoginToken(String sub);
}
