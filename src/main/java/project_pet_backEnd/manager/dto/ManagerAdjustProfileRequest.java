package project_pet_backEnd.manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ManagerAdjustProfileRequest {
    @NotBlank(message = "密碼不可為空")
    private  String managerPassword;
}
