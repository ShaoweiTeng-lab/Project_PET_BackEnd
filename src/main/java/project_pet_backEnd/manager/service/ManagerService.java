package project_pet_backEnd.manager.service;

import project_pet_backEnd.manager.dto.AdjustManagerRequest;
import project_pet_backEnd.manager.dto.AdjustPermissionRequest;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerLoginRequest;
import project_pet_backEnd.user.dto.ResultResponse;

public interface ManagerService {
    ResultResponse createManager(CreateManagerRequest createManagerRequest);
    ResultResponse managerLogin(ManagerLoginRequest managerLoginRequest);
    ResultResponse adjustPermission(AdjustPermissionRequest adjustPermissionRequest);
    ResultResponse getManagerAuthoritiesById(Integer managerId);
    ResultResponse getManagerAuthoritiesByAccount(String account);
    ResultResponse adjustManager(AdjustManagerRequest adjustManagerRequest);
}
