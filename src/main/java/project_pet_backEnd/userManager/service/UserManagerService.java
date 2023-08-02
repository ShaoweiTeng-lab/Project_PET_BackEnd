package project_pet_backEnd.userManager.service;

import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.userManager.dto.UserQueryParameter;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.List;

public interface UserManagerService {
    Page<List<UserProfileResponse>> getUsers(UserQueryParameter userQueryParameter);
}
