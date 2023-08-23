package project_pet_backEnd.manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ManagerLoginRequest {
    @NotBlank(message = "帳號不可為空")
    private  String managerAccount;
    @NotBlank(message = "密碼不可為空")
    private  String managerPassword;
}
