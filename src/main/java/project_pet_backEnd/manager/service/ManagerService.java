package project_pet_backEnd.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import project_pet_backEnd.manager.dto.*;
import project_pet_backEnd.utils.commonDto.ResponsePage;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.List;

public interface ManagerService {
    ResultResponse createManager(CreateManagerRequest createManagerRequest);
    ResultResponse managerLogin(ManagerLoginRequest managerLoginRequest) throws JsonProcessingException;
    ResultResponse adjustPermission(AdjustPermissionRequest adjustPermissionRequest);
    ResultResponse getManagerAuthoritiesById(Integer managerId);
    ResultResponse getManagerAuthoritiesByAccount(String account);
    ResultResponse adjustManager(AdjustManagerRequest adjustManagerRequest);

    ResponsePage<List<ManagerQueryResponse>> getManagers(QueryManagerParameter queryManagerParameter);

    ManagerProfileResponse getProfile(Integer managerId);

    void adjustProfile(Integer managerId,ManagerAdjustProfileRequest managerAdjustProfileRequest);

}
