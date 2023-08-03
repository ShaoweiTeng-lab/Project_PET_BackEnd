package project_pet_backEnd.user.service;


import project_pet_backEnd.user.dto.oAuth.OAuthRequest;
import project_pet_backEnd.user.dto.oAuth.OAuthResponse;
import project_pet_backEnd.user.dto.oAuth.UserInfoResponse;

public interface OAuthService {
    OAuthResponse getGoogleToken(OAuthRequest oAuthRequest);

    UserInfoResponse getUserInfo(String accessToken);

    UserInfoResponse oAuthLogin(OAuthRequest oauthRequest);
}
